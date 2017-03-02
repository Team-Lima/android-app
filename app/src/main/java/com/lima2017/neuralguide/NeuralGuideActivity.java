package com.lima2017.neuralguide;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lima2017.neuralguide.api.DaggerNeuralGuideApiComponent;
import com.lima2017.neuralguide.api.NeuralGuideApiComponent;
import com.lima2017.neuralguide.api.web.WebApiModule;

/**
 * Represents the main activity that the user interacts with. This should act as a Controller of
 * sorts between the API Layer of the application and the View layer, which is represented by
 * <code>Fragment</code>s. User actions and UI manipulations should <strong>not</strong> go here
 * unless absolutely necessary. These go in <code>Fragment</code>s.
 *
 * @author Henry Thompson
 * @version 1.0
 */
public class NeuralGuideActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    /** The Dagger component holding the API dependencies. */
    private final NeuralGuideApiComponent _apiComponent;

    /** The <code>Fragment</code> representing the UI for the Neural Guide activity. */
    private NeuralGuideFragment mFragment;
    private GoogleApiClient mGoogleApiClient;

    public NeuralGuideActivity() {
        _apiComponent = DaggerNeuralGuideApiComponent.builder()
                .webApiModule(new WebApiModule())
                .build();
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        hideStatusBar();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signIn();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neural_guide);

        mFragment =  (NeuralGuideFragment) getSupportFragmentManager().findFragmentById(R.id.activity_neural_guide_fragment);
    }

    /**
     * Uses the Neural Guide API to try to caption the image provided.
     * @param image The Image to be captioned.
     */
    public void captionImage(@NonNull final byte[] image) {
        _apiComponent.api().tryCaptionImage(image, result -> mFragment.onImageCaptioned(result));
    }

    /** Hides the Status Bars from the UI. */
    private void hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (!result.isSuccess()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.sign_in_failed)
                    .setMessage(R.string.sign_in_failed_explanation)
                    .setPositiveButton(android.R.string.ok, null)
                    .setOnDismissListener(v -> signIn())
                    .show();
        }
    }

    /** Request code for signing in the user to their Google Account. */
    private static final int REQUEST_CODE_SIGN_IN = 0;
}