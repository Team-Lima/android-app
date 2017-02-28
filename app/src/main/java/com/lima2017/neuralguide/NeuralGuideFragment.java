package com.lima2017.neuralguide;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.cameraview.CameraView;
import com.google.android.cameraview.CameraView.Callback;
import com.lima2017.neuralguide.api.ImageCaptionResult;
import com.lima2017.neuralguide.api.ImprovementTip;

import javax.inject.Inject;

import java8.util.Optional;

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

    /** TextView holding tips and secondary messages to the user. */
    private TextView mTipsTextView;

    /** ImageView showing an icon reflecting the success or error state of the app to the user. */
    private ImageView mIconImageView;

    /** RelativeLayout holding the result, icon and improvements of captioning. */
    private RelativeLayout mCaptionPanel;

    /** The Activity holding this fragment must be the NeuralGuideActivity. */
    private NeuralGuideActivity mNeuralGuideActivity;

    /** An instance of the Android TextToSpeech service. */
    private TextToSpeech mTextToSpeech;

    /** ProgressBar shown to the user whilst captioning is in progress. */
    private ProgressBar mProgressSpinner;

    /** Android's Vibrator service for providing haptic feedback to users. */
    private Vibrator mVibrator;

    /** A mapping between improvement tips and their text representation to the user */
    @Inject private ImprovementToTextMapping mTextMapping;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!(getActivity() instanceof NeuralGuideActivity)) {
            // If we reach here we are about to hit a cast exception.
            Log.e(LOG_TAG, "The parent activity must be a NeuralGuideActivity but is not!");
        }

        mNeuralGuideActivity = (NeuralGuideActivity) getActivity();
        initialiseTextToSpeech();
    }

    @Override
    @NonNull
    public View onCreateView(@NonNull  final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_neural_guide, container, false);

        setUpCameraView(root);
        setUpCaptionPane(root);
        mProgressSpinner = (ProgressBar) root.findViewById(R.id.fragment_neural_guide_progress);
        mVibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);

        requestPermissions();

        return root;
    }

    private void requestPermissions() {
        if (!hasInternetPermission()) {
            requestInternetPermission();
        }

        if (!hasCameraPermission()) {
            requestCameraPermission();
        }

        if (!hasVibratePermission()) {
            requestVibratePermission();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (hasCameraPermission()) {
            mCameraView.start();
        }
        else {
            requestCameraPermission();
        }
    }

    @Override
    public void onPause() {
        mCameraView.stop();
        super.onPause();
    }

    private void setUpCaptionPane(@NonNull final View root) {
        mCaptionPanel = (RelativeLayout) root.findViewById(R.id.fragment_neural_guide_feedback_panel);
        mCaptionTextView = (TextView) root.findViewById(R.id.fragment_neural_guide_feedback_text);
        mTipsTextView = (TextView) root.findViewById(R.id.fragment_neural_guide_feedback_tips);
        mIconImageView = (ImageView) root.findViewById(R.id.fragment_neural_guide_feedback_icon);

        mCaptionPanel.setOnClickListener(view -> speakCaptionPanelContents());

        mIconImageView.setImageResource(R.drawable.ic_info);
        showCaptionPane();
    }

    private void speakCaptionPanelContents() {
        final CharSequence text = mCaptionTextView.getText();
        final CharSequence tips = mTipsTextView.getText();
        speak(text + ". " + tips);
    }

    private void setUpCameraView(@NonNull final View root) {
        mCameraView = (CameraView) root.findViewById(R.id.fragment_neural_guide_camera_view);
        mCameraView.addCallback(onPictureTaken);
        mCameraView.setOnClickListener(view -> mCameraView.takePicture());
    }

    /**
     * Uses the Android Text-to-Speech service to read out the provided text.
     * @param text The text to be read out.
     */
    @SuppressWarnings("deprecation")
    private void speak(@NonNull final CharSequence text) {
        if (mTextToSpeech == null) {
            createTextToSpeechUnavailableDialog(null).show();
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
    public void onImageCaptioned(@NonNull final Optional<ImageCaptionResult> result) {
        mCameraView.setEnabled(true);
        hideProgressSpinner();

        if (!result.isPresent()) {
            Log.d(LOG_TAG, "Image captioning failed due to lack of internet connectivity.");
            showCaptionPaneWithText(R.string.internet_connection_failed,
                    R.string.internet_connection_failed_subtitle, false);
            return;
        }

        final ImageCaptionResult captionResult = result.get();

        if (captionResult.success() && captionResult.getCaption().isPresent()) {
            final String text = captionResult.getCaption().get();
            showCaptionPaneWithText(text, null, true);
        }
        else {
            final StringBuilder tips = new StringBuilder();

            for (final ImprovementTip tip: captionResult.getImprovementTips()) {
                final String tipAsText = mTextMapping.getText(tip, getResources());
                tips.append(tipAsText);
                tips.append(' ');
            }

            showCaptionPaneWithText(getString(R.string.captioning_failed), tips.toString(), false);
        }
    }

    private void showCaptionPaneWithText(@StringRes final int stringId,
                                         @StringRes final int subtitleId,
                                         final boolean success) {
        showCaptionPaneWithText(getString(stringId), getString(subtitleId), success);
    }

    private void showCaptionPaneWithText(@NonNull final String text,
                                         @Nullable final String subtitle,
                                         final boolean success) {
        mCaptionTextView.setText(text);
        mTipsTextView.setText((subtitle != null) ? subtitle : "");
        mTipsTextView.setVisibility((subtitle != null) ? View.VISIBLE : View.GONE);
        speak((subtitle != null) ? text + ". " + subtitle : text);
        mIconImageView.setImageResource(success ? R.drawable.ic_speaking : R.drawable.ic_error_alert);

        showCaptionPane();
    }

    /**
     * Set up the Android Text-to-Speech service, including dealing with errors from it not being
     * available.
     */
    private void initialiseTextToSpeech() {
        mTextToSpeech = new TextToSpeech(mNeuralGuideActivity, status -> {
            if (status != TextToSpeech.SUCCESS) {
                createTextToSpeechUnavailableDialog(null).show();
                Log.e(LOG_TAG, "Failed to initialise TextToSpeech service");
            }
            else {
                speakCaptionPanelContents();
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
            createCameraPermissionsRationaleDialog(this::requestCameraPermissionNoRationale).show();
        }
        else {
            requestCameraPermissionNoRationale();
        }
    }

    /**
     * Requests permission to use the Camera from the user, never displaying the rationale.
     */
    private void requestCameraPermissionNoRationale() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_CODE_REQUEST_CAMERA);

    }

    /**
     * Creates a dialog which displays the rationale for requiring the camera to the user.
     * @param onDismiss A callback to be invoked when the dialog is dismissed.
     * @return A dialog that, when shown, displays to the user a message indicating that
     * why the camera permission is required. Note that the dialog is not shown, and so
     * AlertDialog.show() must be called explicitly.
     */
    public AlertDialog createCameraPermissionsRationaleDialog(@Nullable Runnable onDismiss) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.camera_permission_rationale)
                .setPositiveButton(android.R.string.ok, (dialog, id) -> {
                    if (onDismiss != null) {
                        onDismiss.run();
                    }
                })
                .setOnDismissListener(dialog -> {
                    if (onDismiss != null) {
                        onDismiss.run();
                    }
                })
                .create();
    }

    /**
     * @return <code>true</code> if and only if the user has granted permission for this app to
     * use the camera.
     */
    private boolean hasVibratePermission() {
        return PackageManager.PERMISSION_GRANTED ==
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.VIBRATE);
    }

    /**
     * Requests permission to use haptic feedback from the user, displaying a rationale if the user
     * has declined permission in the past.
     */
    private void requestVibratePermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            createVibratePermissionsRationaleDialog(this::requestVibratePermissionNoRationale).show();
        }
        else {
            requestVibratePermissionNoRationale();
        }
    }

    /** Requests permission to use haptic feedback from the user, never displaying the rationale. */
    private void requestVibratePermissionNoRationale() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.VIBRATE},
                PERMISSION_CODE_REQUEST_VIBRATE);

    }

    /**
     * Creates a dialog which displays the rationale for requiring haptic feedback to the user.
     * @param onDismiss A callback to be invoked when the dialog is dismissed.
     * @return A dialog that, when shown, displays to the user a message indicating that
     * why the camera permission is required. Note that the dialog is not shown, and so
     * AlertDialog.show() must be called explicitly.
     */
    public AlertDialog createVibratePermissionsRationaleDialog(@Nullable Runnable onDismiss) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.vibrate_permission_rationale)
                .setPositiveButton(android.R.string.ok, (dialog, id) -> {
                    if (onDismiss != null) {
                        onDismiss.run();
                    }
                })
                .setOnDismissListener(dialog -> {
                    if (onDismiss != null) {
                        onDismiss.run();
                    }
                })
                .create();
    }

    /**
     * @return <code>true</code> if and only if the user has granted permission for this app to
     * use the Internet.
     */
    private boolean hasInternetPermission() {
        return PackageManager.PERMISSION_GRANTED ==
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.INTERNET);
    }

    /**
     * Requests permission to use the Internet from the user, displaying a rationale if the user
     * has declined permission in the past.
     */
    private void requestInternetPermission() {
        Log.d(LOG_TAG, "Requesting internet permission");

        if (shouldShowRequestPermissionRationale(Manifest.permission.INTERNET)) {
            createInternetPermissionsRationaleDialog(this::requestInternetPermissionNoRationale).show();
        }
        else {
            requestInternetPermissionNoRationale();
        }
    }

    /**
     * Requests permission to use the Internet from the user, never displaying the rationale.
     */
    private void requestInternetPermissionNoRationale() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.INTERNET},
                PERMISSION_CODE_REQUEST_INTERNET);

    }

    /**
     * Creates a dialog which displays the rationale for requiring the internet to the user.
     * @param onDismiss A callback to be invoked when the dialog is dismissed.
     * @return A dialog that, when shown, displays to the user a message indicating that
     * why the internet permission is required. Note that the dialog is not shown, and so
     * AlertDialog.show() must be called explicitly.
     */
    public AlertDialog createInternetPermissionsRationaleDialog(@Nullable Runnable onDismiss) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.internet_permission_rationale)
                .setPositiveButton(android.R.string.ok, (dialog, id) -> {
                    if (onDismiss != null) {
                        onDismiss.run();
                    }
                })
                .setOnDismissListener(dialog -> {
                    if (onDismiss != null) {
                        onDismiss.run();
                    }
                })
                .create();
    }

    /**
     * Creates a dialog which explains to the user that the Android Text-to-Speech service is
     * unavailable.
     * @param onDismiss A callback to be invoked when the dialog is dismissed.
     * @return A dialog that, when shown, displays to the user a message indicating that
     * Android Text-to-Speech is unavailable. Note that the dialog is not shown, and so
     * AlertDialog.show() must be called explicitly.
     */
    @NonNull
    public AlertDialog createTextToSpeechUnavailableDialog(@Nullable Runnable onDismiss) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.text_to_speech_unavailable)
                .setPositiveButton(android.R.string.ok, (dialog, id) -> {
                    if (onDismiss != null) {
                        onDismiss.run();
                    }
                })
                .setOnDismissListener(dialog -> {
                    if (onDismiss != null) {
                        onDismiss.run();
                    }
                })
                .create();
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           @NonNull final String permissions[],
                                           @NonNull final int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mCameraView.start();
                }
                else {
                    // We absolutely need the camera. Try again.
                    requestCameraPermission();
                }

                break;
            }

            case PERMISSION_CODE_REQUEST_INTERNET: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // We absolutely need the internet. Try again.
                    requestInternetPermission();
                }

                break;
            }

            case PERMISSION_CODE_REQUEST_VIBRATE: {
                // This isn't too essential. Let's not worry about it.
            }
        }
    }

    private void hideCaptionPane() {
        mCaptionPanel.animate().translationY(mCaptionPanel.getHeight());
    }

    private void showCaptionPane() {
        mCaptionPanel.animate().translationY(0);
    }

    private void showProgressSpinner() {
        mProgressSpinner.setVisibility(View.VISIBLE);
        mCameraView.stop();
    }

    private void hideProgressSpinner() {
        mCameraView.start();
        mProgressSpinner.setVisibility(View.GONE);
    }

    /**
     * Callback called by the CameraView whenever a picture is taken.
     */
    private Callback onPictureTaken = new Callback() {
        @Override
        public void onPictureTaken(@NonNull final CameraView cameraView,
                                   @NonNull final byte[] data) {
            requestPermissions();

            hideCaptionPane();
            showProgressSpinner();

            mNeuralGuideActivity.captionImage(data);
            mVibrator.vibrate(VIBRATE_TIME_MILLISECONDS);
            mCameraView.setEnabled(false);
        }
    };

    /** The error log tag used for this class */
    private static final String LOG_TAG = "NeuralGuideFragment";

    /** The code representing this fragment's request to use the Camera */
    private static final int PERMISSION_CODE_REQUEST_CAMERA = 1;

    /** The code representing this fragment's request to use the Internet */
    private static final int PERMISSION_CODE_REQUEST_INTERNET = 2;

    /** The code representing this fragment's request to use haptic feedback. */
    private static final int PERMISSION_CODE_REQUEST_VIBRATE = 3;

    /** Time to vibrate the haptic feedback for. */
    private static final long VIBRATE_TIME_MILLISECONDS = 1000;
}