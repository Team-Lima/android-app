package com.lima2017.neuralguide.api.web;

import android.media.Image;

import com.lima2017.neuralguide.api.INeuralGuideApi;
import com.lima2017.neuralguide.api.OnImageCaptionedListener;

/**
 * Represents a concrete implenetation of the Neural Guide API, actually calling the Web API
 * endpoint.
 */
public class NeuralGuideApi implements INeuralGuideApi {
    /** The asynchronous task to query the API endpoint and retrieve a caption for the image */
    private final QueryCaptionEndpointTask _task;

    /**
     * Initliases a new instance of the Web API.
     * @param task The asynchronous task that should be used to query the endpoint.
     */
    public NeuralGuideApi(final QueryCaptionEndpointTask task) {
        _task = task;
    }

    @Override
    public void tryCaptionImage(final Image image, final OnImageCaptionedListener callback) {
        _task.execute(image);
    }
}