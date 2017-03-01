package com.lima2017.neuralguide.api.web;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lima2017.neuralguide.api.INeuralGuideApi;
import com.lima2017.neuralguide.api.OnImageCaptionedListener;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger dependencies module for the Web API.
 *
 * @author Henry Thompson
 * @version 1.0
 */
@Module
public class WebApiModule {
    @Provides INeuralGuideApi provideApi(@NonNull final HttpRequestManager requestManager,
                                         @NonNull final ObjectMapper objectMapper) {
        return new NeuralGuideApi(requestManager, objectMapper);
    }

    @Provides WebApiConfig provideWebApiConfig() {
        return new WebApiConfig();
    }

    @Provides NeuralGuideApi provideNeuralGuideApi(@NonNull final HttpRequestManager requestManager,
                                                   @NonNull final ObjectMapper objectMapper) {
        return new NeuralGuideApi(requestManager, objectMapper);
    }

    @Provides ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }

    @Provides QueryCaptionEndpointTask provideQueryCaptionEndpoindTask(@NonNull final OnImageCaptionedListener listener,
                                                                       @NonNull final ObjectMapper mapper,
                                                                       @NonNull final HttpRequestManager requestManager) {
        return new QueryCaptionEndpointTask(listener, mapper, requestManager);
    }
}