package uk.ac.cam.cl.lima2017.neuralguide.api.web;

import android.media.Image;

import uk.ac.cam.cl.lima2017.neuralguide.api.INeuralGuideApi;
import uk.ac.cam.cl.lima2017.neuralguide.api.OnImageCaptionedListener;

/**
 * Represents a concrete implenetation of the Neural Guide API, actually calling the Web API
 * endpoint.
 */
public class NeuralGuideApi implements INeuralGuideApi {
    private final QueryCaptionEndpointTask _task;

    public NeuralGuideApi(QueryCaptionEndpointTask task) {
        _task = task;
    }

    @Override
    public void tryCaptionImage(Image image, OnImageCaptionedListener callback) {
        _task.execute(image);
    }
}