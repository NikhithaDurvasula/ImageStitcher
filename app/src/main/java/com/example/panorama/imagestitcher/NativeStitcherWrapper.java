package com.example.panorama.imagestitcher;

import java.util.List;

import org.opencv.core.Mat;

import android.util.Log;

public class NativeStitcherWrapper {
    private static final String TAG = "Stitcher::NativeStitcherWrapper";
    public static Mat stitch(List<Mat> inputs, boolean waveCorrect, boolean multiBand) {
        System.loadLibrary("Stitcher");

        Mat imgArr[] = new Mat[inputs.size()];
        imgArr = inputs.toArray(imgArr);

        Log.i(TAG, "about to begin stitching");

        Mat result = new Mat();
        NativeStitch(imgArr, result.getNativeObjAddr(), waveCorrect, multiBand);

        Log.i(TAG, "done with stitching");

        return result;
    }

    private static native void NativeStitch(Mat imgs[], long resultMatAddr, boolean waveCorrect, boolean multiBand);
}
