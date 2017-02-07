package com.lima2017.neuralguide.api.web;

import android.media.Image;
import android.os.AsyncTask;

import com.lima2017.neuralguide.api.OnImageCaptionedListener;
import com.lima2017.neuralguide.api.ImageCaptionResult;

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
        return null;
    }

    @Override
    protected void onPostExecute(final ImageCaptionResult result) {
        _listener.onImageCaptioned(result);
    }
}
