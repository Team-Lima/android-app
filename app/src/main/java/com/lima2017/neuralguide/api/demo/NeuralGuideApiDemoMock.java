package com.lima2017.neuralguide.api.demo;

import android.support.annotation.NonNull;

import java.net.HttpURLConnection;

import com.lima2017.neuralguide.api.INeuralGuideApi;
import com.lima2017.neuralguide.api.OnImageCaptionedListener;
import com.lima2017.neuralguide.api.ImageCaptionResult;

/**
 * Mocks the API by returning a hardcoded string value.
 *
 * @author Henry Thompson
 * @version 1.0
 */
public class NeuralGuideApiDemoMock implements INeuralGuideApi {
    @Override
    public void tryCaptionImage(@NonNull byte[] imageData,
                                @NonNull OnImageCaptionedListener callback) {
        callback.onImageCaptioned(new ImageCaptionResult(HttpURLConnection.HTTP_OK, "I think this is a picture of a flower"));
    }
}