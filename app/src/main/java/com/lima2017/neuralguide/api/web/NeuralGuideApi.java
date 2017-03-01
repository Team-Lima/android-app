package com.lima2017.neuralguide.api.web;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lima2017.neuralguide.api.INeuralGuideApi;
import com.lima2017.neuralguide.api.OnImageCaptionedListener;

import javax.inject.Inject;

/**
 * Represents a concrete implementation of the Neural Guide API, actually calling the Web API
 * endpoint.
 *
 * @author Henry Thompson
 */
public class NeuralGuideApi implements INeuralGuideApi {
    /** The HTTP Request Manager configured to contact the Web API. */
    private final HttpRequestManager _requestManager;

    /** The ObjectManager used to construct the JSON objects. */
    private final ObjectMapper _objectManager;

    /**
     * Initialises a new instance of the Web API.
     * @param requestManager The HTTP Request Manager used to perform the requests to the API.
     */
    @Inject
    public NeuralGuideApi(@NonNull final HttpRequestManager requestManager,
                          @NonNull final ObjectMapper objectMapper) {
        _requestManager = requestManager;
        _objectManager = objectMapper;
    }

    @Override
    public void tryCaptionImage(@NonNull final byte[] imageData,
                                @NonNull final OnImageCaptionedListener callback) {
        new QueryCaptionEndpointTask(callback, _objectManager, _requestManager).execute(imageData);
    }
}