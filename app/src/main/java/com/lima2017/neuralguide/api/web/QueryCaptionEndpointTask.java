package com.lima2017.neuralguide.api.web;

import android.media.Image;
import android.os.AsyncTask;

import com.lima2017.neuralguide.api.NeuralGuideResult;
import com.lima2017.neuralguide.api.OnImageCaptionedListener;
import com.lima2017.neuralguide.api.ImageCaptionResult;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URL;

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
            //To be dependency injected
            ObjectMapper mapper = new ObjectMapper();
            //Do work in here
            //Create API request
            //Send request
            //Wait for result
            String jsonStringImage = "";
            String jsonStringResult = "";
            NeuralGuideResult decodedResult = mapper.readValue(jsonStringResult, NeuralGuideResult.class);
            return new ImageCaptionResult(decodedResult.getStatusCode(), decodedResult.getData().getText());
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

    private int sendPut(String data, WebApiConfig webApiConfig) {
        int responseCode = -1;


        HttpClient httpClient = HttpClients.createDefault();
        try {
            URL url = new URL(webApiConfig.getBaseUrl() + webApiConfig.getVersion());

        } catch (Exception ex) {

        } finally {
        }
        return responseCode;
    }
}
