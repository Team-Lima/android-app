package com.lima2017.neuralguide.api.web;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.lima2017.neuralguide.api.OnImageCaptionedListener;
import com.lima2017.neuralguide.api.ImageCaptionResult;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import java8.util.Optional;

/**
 * This asynchronous task queries the API endpoint.
 */
public class QueryCaptionEndpointTask extends AsyncTask<byte[], Integer, Optional<ImageCaptionResult>> {
    private final OnImageCaptionedListener _listener;
    private final WebApiConfig _config;
    private final OutgoingRequestPayloadCreator _creator;
    private final HttpRequestManager _httpRequest;
    private final IncomingPayloadDecoder _decoder;


    public QueryCaptionEndpointTask(@NonNull final WebApiConfig config,
                                    @NonNull final OnImageCaptionedListener listener) {
        _config = config;
        _listener = listener;
        //Todo dependency inject
        _creator = new OutgoingRequestPayloadCreator();
        _httpRequest = new HttpRequestManager(_config);
        _decoder = new IncomingPayloadDecoder();
    }

    @Override
    protected Optional<ImageCaptionResult> doInBackground(@NonNull final byte[]... params) {
        try {
            String jsonStringImage = _creator.generateOutgoingJsonString(params[0]);
            ApiResponse response = _httpRequest.sendHttpPostRequest(jsonStringImage);

            return Optional.of(_decoder.generateImageCaptureResultFromPayload(response));

        } catch (IOException e) {
            Log.e(LOG_TAG, "Caught IOException when trying to caption image with message: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    protected void onPostExecute(@NonNull final Optional<ImageCaptionResult> result) {
        // TODO - we need to think about the situation when the internet is not connected, i.e. when
        // !result.isPresent(). Perhaps we should have an `onNoInternet` callback too?
        _listener.onImageCaptioned(result);
    }

    private static final String LOG_TAG = "QueryEndpointTask";
}