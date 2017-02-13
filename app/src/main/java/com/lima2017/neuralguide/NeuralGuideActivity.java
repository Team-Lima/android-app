package com.lima2017.neuralguide;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.lima2017.neuralguide.api.INeuralGuideApi;
import com.lima2017.neuralguide.api.demo.NeuralGuideApiDemoMock;

/**
 * Represents the main activity that the user interacts with. This should act as a Controller of
 * sorts between the API Layer of the application and the View layer, which is represented by
 * <code>Fragment</code>s. User actions and UI manipulations should <strong>not</strong> go here
 * unless absolutely necessary. These go in <code>Fragment</code>s.
 *
 * @author Henry Thompson
 * @version 1.0
 */
public class NeuralGuideActivity extends AppCompatActivity {

    /**
     * The instance of the Neural Guide API used by this Activity when relaying user commands to
     * the endpoint, and from which results will eminate.
     */
    private final INeuralGuideApi _api;

    /** The <code>Fragment</code> representing the UI for the Neural Guide activity. */
    private NeuralGuideFragment mFragment;

    /** Temporary constructor - we want to use a DI framework but this will do for now. */
    public NeuralGuideActivity() {
        this(new NeuralGuideApiDemoMock());
    }

    /**
     * @param api The instance of the Neural Guided API through which requests to caption the image
     *            will be routed.
     */
    public NeuralGuideActivity(final INeuralGuideApi api) {
        _api = api;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        hideStatusBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neural_guide);

        mFragment =  (NeuralGuideFragment) getSupportFragmentManager().findFragmentById(R.id.activity_neural_guide_fragment);
    }

    /**
     * Uses the Neural Guide API to try to caption the image provided.
     * @param image The Image to be captioned.
     */
    public void captionImage(final Image image) {
        _api.tryCaptionImage(image, result -> mFragment.onImageCaptioned(result));
    }

    /** Hides the Status Bars from the UI. */
    private void hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}