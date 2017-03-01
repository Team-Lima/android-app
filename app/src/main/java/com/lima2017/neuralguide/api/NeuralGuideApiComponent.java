package com.lima2017.neuralguide.api;

import com.lima2017.neuralguide.api.web.WebApiModule;

import dagger.Component;

/**
 * Dagger dependencies component for the API. There is only one module exposed, which is
 * the API interface.
 *
 * @author Henry Thompson
 * @version 1.0
 */
@Component(modules = WebApiModule.class)
public interface NeuralGuideApiComponent {
    INeuralGuideApi api();
}
