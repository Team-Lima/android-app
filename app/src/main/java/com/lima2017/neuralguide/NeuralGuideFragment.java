package com.lima2017.neuralguide;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.service.voice.AlwaysOnHotwordDetector;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.cameraview.CameraView;
import com.google.android.cameraview.CameraView.Callback;
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
    /** The View through which what the Camera sees is displayed to the user. */
    private CameraView mCameraView;

    /** TextView holding the result of captioning. */
    private TextView mCaptionTextView;

    /** TextView holding the 'Tap or say "What is this?"' prompt. */
    private TextView mPromptTextView;

    /** The Activity holding this fragment must be the NeuralGuideActivity. */
    private NeuralGuideActivity mNeuralGuideActivity;

    /** An instance of the Android TextToSpeech service. */
    private TextToSpeech mTextToSpeech;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!(getActivity() instanceof NeuralGuideActivity)) {
            // If we reach here we are about to hit a cast exception.
            Log.e(LOG_TAG, "The parent activity must be a NeuralGuideActivity but is not!");
        }

        mNeuralGuideActivity = (NeuralGuideActivity) getActivity();
        initialiseTextToSpeech();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_neural_guide, container, false);

        setUpCameraView(root);
        setUpPromptTextView(root);
        setUpCaptionTextView(root);

        if (!hasCameraPermission()) {
            requestCameraPermission();
        }

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCameraView.start();
    }

    @Override
    public void onPause() {
        mCameraView.stop();
        super.onPause();
    }

    private void setUpPromptTextView(final View root) {
        mPromptTextView = (TextView) root.findViewById(R.id.fragment_neural_guide_prompt);
        mPromptTextView.setOnClickListener(view -> mCameraView.takePicture());
    }

    private void setUpCaptionTextView(final View root) {
        mCaptionTextView = (TextView) root.findViewById(R.id.fragment_neural_guide_feedback_text);

        mCaptionTextView.setOnClickListener(view -> {
            final CharSequence text = mCaptionTextView.getText();
            speak(text);
        });
    }

    private void setUpCameraView(final View root) {
        mCameraView = (CameraView) root.findViewById(R.id.fragment_neural_guide_camera_view);
        mCameraView.addCallback(onPictureTaken);
        mCameraView.setOnClickListener(view -> mCameraView.takePicture());
    }

    /**
     * Uses the Android Text-to-Speech service to read out the provided text.
     * @param text The text to be read out.
     */
    private void speak(final CharSequence text) {
        if (mTextToSpeech == null) {
            // TODO: Deal with this.
            Log.e(LOG_TAG, "Called 'speak' but no text to speech instance exists.");
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else {
            mTextToSpeech.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    /**
     * Callback for when the captioned state of the current image changes. This will cause the
     * user interface to reflect the captioning attempt represented by the ImageCaptionResult
     * object.
     * @param result The captioning result which should be reflected in the user interface.
     */
    public void onImageCaptioned(final ImageCaptionResult result) {
        if (result.success()) {
            final String text = result.getCaption();
            mCaptionTextView.setText(text);
            speak(text);
        }
        else {
            // TODO: Handle error cases
        }
    }

    /**
     * Set up the Android Text-to-Speech service, including dealing with errors from it not being
     * available.
     */
    private void initialiseTextToSpeech() {
        mTextToSpeech = new TextToSpeech(mNeuralGuideActivity, status -> {
            if (status != TextToSpeech.SUCCESS) {
                // TODO: Deal with TTS being unavailable
                Log.e(LOG_TAG, "Failed to initialise TextToSpeech service");
            }
        });
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
                    mCameraView.start();
                }
                else {
                    // TODO: Handle permissions denied
                }

                break;
            }
        }
    }

    /**
     * Callback called by the CameraView whenever a picture is taken.
     */
    private Callback onPictureTaken = new Callback() {
        @Override
        public void onPictureTaken(final CameraView cameraView, final byte[] data) {
            mNeuralGuideActivity.captionImage(data);
        }
    };

    /** The error log tag used for this class */
    private static final String LOG_TAG = "NeuralGuideFragment";

    /** The code representing this fragment's request to use the Camera */
    private static final int PERMISSION_CODE_REQUEST_CAMERA = 1;
}