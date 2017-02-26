package com.lima2017.neuralguide.api.web;

import android.support.annotation.NonNull;

import com.lima2017.neuralguide.api.INeuralGuideApi;
import com.lima2017.neuralguide.api.OnImageCaptionedListener;

/**
 * Represents a concrete implementation of the Neural Guide API, actually calling the Web API
 * endpoint.
 */
public class NeuralGuideApi implements INeuralGuideApi {
    /** Represents the configuration of the Web API to be used in the requests */
    private final WebApiConfig _config;

    /**
     * Initialises a new instance of the Web API.
     * @param config The configuration used to query the Web API with.
     */
    public NeuralGuideApi(@NonNull final WebApiConfig config) {
        _config = config;
    }

    @Override
    public void tryCaptionImage(@NonNull final byte[] imageData,
                                @NonNull final OnImageCaptionedListener callback) {
        new QueryCaptionEndpointTask(_config, callback).execute(imageData);
    }
}