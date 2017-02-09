package com.lima2017.neuralguide.api.web;

import android.media.Image;
import android.os.AsyncTask;

import com.lima2017.neuralguide.api.NeuralGuideResult;
import com.lima2017.neuralguide.api.OnImageCaptionedListener;
import com.lima2017.neuralguide.api.ImageCaptionResult;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * This asynchronous task queries the API endpoint.
 */
public class QueryCaptionEndpointTask extends AsyncTask<Image, Integer, ImageCaptionResult> {
    private final OnImageCaptionedListener _listener;
    private final WebApiConfig _config;

    public QueryCaptionEndpointTask(final OnImageCaptionedListener listener, final WebApiConfig config) {
        _listener = listener;
        _config = config;
    }

    @Override
    protected ImageCaptionResult doInBackground(final Image... params) {
        try {
            //Do work in here
            //Create API request
            //Send request
            //Wait for result
            String jsonString = "";
            //To be dependency injected
            ObjectMapper mapper = new ObjectMapper();
            NeuralGuideResult decodedResult = mapper.readValue(jsonString, NeuralGuideResult.class);
            //Decode result
            //return decoded image
            return null;
        } catch (IOException e) {
            //What do we want to do on exceptions
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(final ImageCaptionResult result) {
        _listener.onImageCaptioned(result);
    }
}
