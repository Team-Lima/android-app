package uk.ac.cam.cl.lima2017.neuralguide.api.demo;

import android.media.Image;

import java.net.HttpURLConnection;

import uk.ac.cam.cl.lima2017.neuralguide.api.INeuralGuideApi;
import uk.ac.cam.cl.lima2017.neuralguide.api.OnImageCaptionedListener;
import uk.ac.cam.cl.lima2017.neuralguide.api.ImageCaptionResult;

public class NeuralGuideApiDemoMock implements INeuralGuideApi {
    @Override
    public void tryCaptionImage(Image image, OnImageCaptionedListener callback) {
        callback.onImageCaptioned(new ImageCaptionResult(HttpURLConnection.HTTP_OK, "I think this is a picture of a flower"));
    }
}
