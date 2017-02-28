package com.lima2017.neuralguide.api.web;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lima2017.neuralguide.api.OnImageCaptionedListener;
import com.lima2017.neuralguide.api.web.WebApiConfig;

import dagger.Module;
import dagger.Provides;

/**
 * Created by henry on 2/26/17.
 */
@Module
public class WebApiModule {
    @Provides WebApiConfig provideWebApiConfig() {
        return new WebApiConfig();
    }

    @Provides NeuralGuideApi provideNeuralGuideApi(@NonNull final WebApiConfig config) {
        return new NeuralGuideApi(config);
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