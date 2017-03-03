package com.lima2017.neuralguide.api.web;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lima2017.neuralguide.api.INeuralGuideApi;
import com.lima2017.neuralguide.api.OnImageCaptionedListener;

/**
 * Represents a concrete implementation of the Neural Guide API, actually calling the Web API
 * endpoint.
 *
 * @author Henry Thompson
 */
public class NeuralGuideApi implements INeuralGuideApi {
    /** The HTTP Request Manager configured to contact the Web API. */
    private final IHttpRequestManager _requestManager;

    /** The ObjectManager used to construct the JSON objects. */
    private final ObjectMapper _objectManager;

    /**
     * The mapping between the Strings provided by the JSON to represent improvement tips and the
     * Java enums.
     */
    private final StringToImprovementTipMapping _stringMapper;

    /** The Dagger WebApiModule providing the query caption endpoint tasks. */
    private final WebApiModule _module;

    public NeuralGuideApi(@NonNull final WebApiModule module,
                          @NonNull final IHttpRequestManager requestManager,
                          @NonNull final ObjectMapper objectMapper,
                          @NonNull final StringToImprovementTipMapping stringMapper) {
        _module = module;
        _requestManager = requestManager;
        _objectManager = objectMapper;
        _stringMapper = stringMapper;
    }

    @Override
    public void tryCaptionImage(@NonNull final byte[] imageData,
                                @NonNull final OnImageCaptionedListener callback) {
        _module.provideQueryCaptionEndpoindTask(callback, _objectManager, _requestManager, _stringMapper)
                .execute(imageData);
    }
}