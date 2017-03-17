package com.example.panorama.imagestitcher;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.List;
import java.util.ListIterator;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity implements CvCameraViewListener2 {
    private static final String TAG = "Stitcher::MainActivity";


    /* interface to camera */
    private CameraView 	mOpenCvCameraView;

    private MenuItem	mResChecked;
    private List<Size> 	mResolutionList;

    /* timing menu */
    private SubMenu     mTimingMenu;
    private MenuItem	mTimingChecked;

    /* current frame storage */
    private Mat 		mCurrentFrame;
    private Mat 		mRgbFrame;

    /* capture timing information */
    private boolean 	mIsCapturing = false;
    private int 		mTimeBetweenCaptures = 1000;
    private long 		mTimeSinceLastCapture;
    private long 		mTick;
    private long 		mTock;

    /* OpenCV Boilerplate */
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    System.loadLibrary("Stitcher");
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    public void onPause()
    {
        Log.i(TAG, "called onPause");
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        Log.i(TAG, "called onResume");
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);

		/* Clear pano image list every time camera view is restarted */
        SharedData.panoImgs.clear();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "called onDestroy");
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

        mOpenCvCameraView = (CameraView) findViewById(R.id.javaCameraView1);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
    }


    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgbFrame = new Mat();
        mTick = SystemClock.elapsedRealtime();
        mTimeSinceLastCapture = mTimeBetweenCaptures;
    }

    @Override
    public void onCameraViewStopped() {
        mIsCapturing = false;
    }


    @Override
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        mCurrentFrame = inputFrame.rgba();

		/* keep track of time between frames */
        mTock = SystemClock.elapsedRealtime();
        long diff = mTock - mTick;
        mTick = mTock;

        if(mIsCapturing) {
			/* accumulate time during capturing */
            mTimeSinceLastCapture += diff;

			/* save frame every N milliseconds */
            if(mTimeSinceLastCapture >= mTimeBetweenCaptures) {
                Log.i(TAG, "grabbing frame...");
                Imgproc.cvtColor(mCurrentFrame, mRgbFrame, Imgproc.COLOR_BGRA2BGR);
                SharedData.panoImgs.add(mRgbFrame.clone());
                mTimeSinceLastCapture = 0;
            }
        }

        return mCurrentFrame;
    }

    /**
     * This method is called when the "Capture" button is clicked.
     * @param view : The button, cast to View
     */
    public void onCaptureClicked(View view) {
        boolean on = ((ToggleButton) view).isChecked();

		/* turning it on just sets the flag */
        if(on && !mIsCapturing) {
            Log.i(TAG, "capturing started");
            mIsCapturing = true;
        }

		/* turning it off captures one more frame and starts processing */
        if(!on && mIsCapturing) {
            Log.i(TAG, "capturing ended");
            mIsCapturing = false;

            Imgproc.cvtColor(mCurrentFrame, mRgbFrame, Imgproc.COLOR_BGRA2BGR);
            SharedData.panoImgs.add(mRgbFrame.clone());
            mTimeSinceLastCapture = 0;

            startActivity(new Intent(this, ProcessingActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		/* resolution menu adapated from opencv tutorial 3 */
        Log.i(TAG, "onCreateOptionsMenu called");
        SubMenu mResolutionMenu = menu.addSubMenu("Resolution");
        mResolutionList = mOpenCvCameraView.getResolutionList();
        ListIterator<Size> resolutionItr = mResolutionList.listIterator();
        int idx = 0;
        while(resolutionItr.hasNext()) {
            Size element = resolutionItr.next();

            MenuItem itm = mResolutionMenu.add(
                    1, idx, Menu.NONE,
                    Integer.valueOf(element.width).toString() + "x" + Integer.valueOf(element.height).toString()
            );

            idx++;

            if (element.equals(mOpenCvCameraView.getResolution())) {
                mResChecked = itm;
            }
        }
        mResolutionMenu.setGroupCheckable(1, true, true);

		/* add menu to adjust capture rate */
        mTimingMenu = menu.addSubMenu("Capture Rate");

        mTimingMenu.add(2, 500,  1, "2 frames per second");
        mTimingChecked = mTimingMenu.add(2, 1000, 2, "1 frame per second"); // default
        mTimingMenu.add(2, 2000, 3, "1 frame per 2 seconds");

        mTimingMenu.setGroupCheckable(2, true, true);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mResChecked.setChecked(true);
        mTimingChecked.setChecked(true);

        return super.onPrepareOptionsMenu(menu);
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getGroupId() == 1) {
            int id = item.getItemId();
            Size res = mResolutionList.get(id);
            mOpenCvCameraView.setResolution(res);
            mResChecked = item;
            toast("Resolution set to " + item.getTitle());
        } else if (item.getGroupId() == 2) {
            mTimeBetweenCaptures = item.getItemId();
            mTimingChecked = item;
            toast("Capture rate set to " + item.getTitle());
        }
        return super.onOptionsItemSelected(item);
    }


}