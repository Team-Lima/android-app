package com.lima2017.neuralguide;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lima2017.neuralguide.api.ImageCaptionResult;

/**
 * This fragment represents the main user interface components for the Neural Guide application.
 * It should only be used within the <code>NeuralGuideActivity</code> which acts as its
 * Controller of sorts.
 *
 * @author Henry Thompson
 * @version 1.0
 *
 * @see NeuralGuideActivity
 */
public class NeuralGuideFragment extends Fragment {
    /**
     * An instance of the <code>Camera</code> object which this application has a lock on to.
     */
    private Camera mCamera;

    /**
     * The <code>View</code> through which what the Camera sees is displayed to the user.
     */
    private CameraView mCameraView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mCamera = getCameraInstance();

        if (mCamera == null) {
            // TODO: Deal with camera being unavailable
            Log.e(TAG, "Camera was unavailable");

            return inflater.inflate(R.layout.fragment_neural_guide, container, false);
        }

        // Create our CameraView and set it as the content of our activity.
        mCameraView = new CameraView(getActivity(), mCamera);
        FrameLayout cameraFrame = (FrameLayout) container.findViewById(R.id.fragment_neural_guide_camera_frame);
        cameraFrame.addView(mCameraView);

        return inflater.inflate(R.layout.fragment_neural_guide, container, false);
    }

    public void onImageCaptioned(ImageCaptionResult result) {

    }

    /**
     * Safely obtains a <code>Camera</code> instance. Note that the camera resource could be
     * locked by another resource, in which case we will fail to obtain it.
     * @return A Camera object if it successfully could get hold of one, or null if not.
     */
    private static Camera getCameraInstance() {
        Camera c = null;

        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            Log.e(TAG, "Camera not found: " + e.getMessage());
        }

        return c;
    }

    /**
     * Releases the <code>Camera</code> resource for use by other applications.
     */
    private void releaseCamera() {
        if (mCamera != null){
            mCamera.release();
            mCamera = null;
        }
    }

    private static final String TAG = "NeuralGuideFragment";
}
