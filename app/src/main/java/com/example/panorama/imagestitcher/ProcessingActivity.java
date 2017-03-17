package com.example.panorama.imagestitcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


public class ProcessingActivity extends Activity {
    private static final String TAG = "Stitcher::ProcessingActivity";

    private MenuItem mItemWriteFile;

    private MenuItem mItemSetWaveCorrect;
    private boolean useWaveCorrect;

    private MenuItem mItemSetBlender;
    private boolean useMultibandBlender;

    /**
     * Cache for bitmaps created with different processing options.
     */
    private Bitmap[][] images = {{null, null}, {null, null}};

    private void setCurrentImage(Bitmap bmp) {
        int row = useWaveCorrect ? 1 : 0;
        int col = useMultibandBlender ? 1 : 0;
        images[row][col] = bmp;
    }

    private Bitmap getCurrentImage() {
        int row = useWaveCorrect ? 1 : 0;
        int col = useMultibandBlender ? 1 : 0;
        return images[row][col];
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Grab the current images from {@link SharedData#panoImgs SharedData.panoImgs},
     * stitch them together, and redisplay
     */
    private void stitchAndDisplay() {
        ImageView imview = (ImageView) findViewById(R.id.imageView1);

		/* if result exists in cache, don't bother restitching, just redisplay */
        Bitmap bmpImage = getCurrentImage();
        if(bmpImage != null) {
            imview.setImageBitmap(bmpImage);
            return;
        }

        System.loadLibrary("Stitcher");

        Mat result = NativeStitcherWrapper.stitch(
                SharedData.panoImgs,
                useWaveCorrect,
                useMultibandBlender
        );

        if(result.empty()) {
            toast("Failed to stitch!");
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        Mat display = new Mat();
        Imgproc.cvtColor(result, display, Imgproc.COLOR_BGR2BGRA);

        bmpImage = Bitmap.createBitmap(display.cols(), display.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(display, bmpImage);

        setCurrentImage(bmpImage);
        imview.setImageBitmap(bmpImage);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);

		/* set defaults */
        useWaveCorrect = true;
        useMultibandBlender = true;
        stitchAndDisplay();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        mItemWriteFile = menu.add("Save File");
        mItemSetWaveCorrect = menu.add("Wave Correction is On");
        mItemSetBlender = menu.add("Using Multiband Blending");

        return super.onCreateOptionsMenu(menu);
    }

    private boolean externalStorageReady() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    private void saveImage() {
		/* name image with current date/time */
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss", Locale.US);
        String filename = formatter.format(Calendar.getInstance().getTime());

        try {
			/* sanity check */
            if(!externalStorageReady()) {
                throw new IOException("external storage not mounted");
            }

			/* assemble path name */
            File picDir = new File(
                    Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES
                    ),
                    "Stitcher"
            );

			/* make gallery directory if needed */
            if(!picDir.mkdirs() && !picDir.isDirectory()) {
                throw new IOException("unable to make dir");
            }

			/* write out file */
            File outFile = new File(picDir, filename + ".png");
            FileOutputStream fos = new FileOutputStream(outFile);
            getCurrentImage().compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            Log.i(TAG, "saved image: " + outFile);

			/* Tell media scanner about this image to add it to the gallery */
            Intent addToDB = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            addToDB.setData(Uri.fromFile(outFile));
            sendBroadcast(addToDB);
            Log.i(TAG, "added to image database:" + outFile);

        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item == mItemWriteFile) {

            saveImage();

        } else if (item == mItemSetWaveCorrect) {

            useWaveCorrect = !useWaveCorrect;

            stitchAndDisplay();

            mItemSetWaveCorrect.setTitle("Wave Correction is " + (useWaveCorrect ? "On" : "Off"));
            toast("Turning " + (useWaveCorrect ? "On" : "Off") + " Wave Correction");

        } else if (item == mItemSetBlender) {

            useMultibandBlender = !useMultibandBlender;

            stitchAndDisplay();

            mItemSetBlender.setTitle("Using " + (useMultibandBlender ? "Multiband" : "Feather") + " Blending");
            toast("Using " + (useMultibandBlender ? "Multiband" : "Feather") + " Blending");

        }
        return super.onOptionsItemSelected(item);
    }


}