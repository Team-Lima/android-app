package com.lima2017.neuralguide.api.web;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lima2017.neuralguide.api.INeuralGuideApi;
import com.lima2017.neuralguide.api.OnApiAuthenticatedListener;
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
    @Provides WebApiConfig provideWebApiConfig() {
        return new WebApiConfig();
    }

    @Provides INeuralGuideApi provideApi(@NonNull final IHttpRequestManager requestManager,
                                         @NonNull final ObjectMapper objectMapper,
                                         @NonNull final StringToImprovementTipMapping stringMapper,
                                         @NonNull final WebApiConfig config) {
        return new NeuralGuideApi(this, requestManager, objectMapper, stringMapper, config);
    }

    @Provides StringToImprovementTipMapping provideStringToImprovementTipMapping() {
        return new StringToImprovementTipMapping();
    }

    @Provides IHttpRequestManager provideHttpRequestManager(@NonNull final WebApiConfig config) {
        return new HttpRequestManager(config);
    }

    @Provides ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }

    @Provides QueryCaptionEndpointTask provideQueryCaptionEndpoindTask(@NonNull final OnImageCaptionedListener listener,
                                                                       @NonNull final ObjectMapper mapper,
                                                                       @NonNull final IHttpRequestManager requestManager,
                                                                       @NonNull final StringToImprovementTipMapping stringMapping) {
        return new QueryCaptionEndpointTask(listener, mapper, requestManager, stringMapping);
    }

    @Provides AuthenticateGoogleAccountTask provideAuthenticationTask(@NonNull final OnApiAuthenticatedListener listener,
                                                                      @NonNull final WebApiConfig config) {
        return new AuthenticateGoogleAccountTask(listener, config);
    }
}