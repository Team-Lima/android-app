package com.lima2017.neuralguide.api.web;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.lima2017.neuralguide.api.OnImageCaptionedListener;
import com.lima2017.neuralguide.api.ImageCaptionResult;

/**
 * This asynchronous task queries the API endpoint.
 */
public class QueryCaptionEndpointTask extends AsyncTask<byte[], Integer, ImageCaptionResult> {
    private final OnImageCaptionedListener _listener;
    private final WebApiConfig _config;

    public QueryCaptionEndpointTask(@NonNull final WebApiConfig config,
                                    @NonNull final OnImageCaptionedListener listener) {
        _config = config;
        _listener = listener;
    }

    @Override
    protected ImageCaptionResult doInBackground(@NonNull final byte[]... params) {
        return null;
    }

    @Override
    protected void onPostExecute(@NonNull final ImageCaptionResult result) {
        _listener.onImageCaptioned(result);
    }
}