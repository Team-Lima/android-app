package com.lima2017.neuralguide;

import android.Manifest;
import android.app.ActionBar;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lima2017.neuralguide.api.ImageCaptionResult;

import java.net.HttpURLConnection;

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

    private TextView mNeuralGuideResultView;

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_neural_guide, container, false);

        if (hasCameraPermission()) {
            inflateCameraView(root);
        }
        else {
            requestCameraPermission();
        }

        return root;
    }

    public void onImageCaptioned(final ImageCaptionResult result) {
        if (result.success()) {
            mNeuralGuideResultView.setText(result.getCaption());
        }
        else {
            // TODO: Handle error cases
        }
    }

    /**
     * Safely obtains a <code>Camera</code> instance. Note that the camera resource could be
     * locked by another resource, in which case we will fail to obtain it.
     * @return A Camera object if it successfully could get hold of one, or null if not.
     */
    private static Camera getCameraInstance() {
        Camera camera = null;

        try {
            camera = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception exception){
            Log.e(LOG_TAG, "Camera not found: " + exception.getMessage());
        }

        return camera;
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

    /**
     * @return <code>true</code> if and only if the user has granted permission for this app to
     * use the camera.
     */
    private boolean hasCameraPermission() {
        return PackageManager.PERMISSION_GRANTED ==
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
    }

    /**
     * Requests permission to use the Camera from the user, displaying a rationale if the user
     * has declined permission in the past.
     */
    private void requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            // TODO Explain to the user why we need the camera
        }
        else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_CODE_REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           final String permissions[], final int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    inflateCameraView(getActivity().findViewById(R.id.activity_neural_guide_root));
                }
                else {
                    // TODO: Handle permissions denied
                }

                break;
            }
        }
    }

    /**
     * Instantiates the <code>CameraView</code> used to display what the back camera sees to the user.
     * @param root The root view of this fragment.
     */
    private void inflateCameraView(final View root) {
        if (mCamera == null) {
            mCamera = getCameraInstance();
        }

        if (mCamera == null) {
            // TODO: Deal with camera being unavailable
            Log.e(LOG_TAG, "Camera was unavailable");
        }
        else {
            mCameraView = new CameraView(getActivity(), mCamera);
            FrameLayout cameraFrame = (FrameLayout) root.findViewById(R.id.fragment_neural_guide_camera_frame);
            cameraFrame.addView(mCameraView);
        }
    }

    /** The error log tag used for this class */
    private static final String LOG_TAG = "NeuralGuideFragment";

    /** The code representing this fragment's request to use the Camera */
    private static final int PERMISSION_CODE_REQUEST_CAMERA = 1;
}