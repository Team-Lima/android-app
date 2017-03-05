package com.lima2017.neuralguide.api.web;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lima2017.neuralguide.api.ImageCaptionResult;
import com.lima2017.neuralguide.api.ImprovementTip;
import com.lima2017.neuralguide.api.OnImageCaptionedListener;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import java8.util.Optional;

/**
 * This asynchronous task queries the API endpoint.
 *
 * @author Henry Thompson
 * @author Tamara Norman
 * @version 1.0
 */
public class QueryCaptionEndpointTask extends AsyncTask<byte[], Integer, Optional<ImageCaptionResult>> {
    private final OnImageCaptionedListener _listener;
    private final ObjectMapper _objectMapper;
    private final IHttpRequestManager _httpRequest;
    private final StringToImprovementTipMapping _stringMapping;

    @Inject
    public QueryCaptionEndpointTask(@NonNull final OnImageCaptionedListener listener,
                                    @NonNull final ObjectMapper mapper,
                                    @NonNull final IHttpRequestManager requestManager,
                                    @NonNull final StringToImprovementTipMapping stringMapping) {
        _listener = listener;
        _objectMapper = mapper;
        _httpRequest = requestManager;
        _stringMapping = stringMapping;
    }

    @Override
    protected void onPreExecute() {
        final TimerTask cancelTask = new TimerTask() {
            @Override
            public void run() {
                Log.e(LOG_TAG, "Timeout after " + _httpRequest.getTimeout() + "ms on captioning task!");
                QueryCaptionEndpointTask.this.cancel(true);

                _httpRequest.abort();
            }
        };

        new Timer().schedule(cancelTask, _httpRequest.getTimeout());
    }

    @Override
    protected Optional<ImageCaptionResult> doInBackground(@NonNull final byte[]... params) {
        try {
            NeuralGuideData data = new NeuralGuideData(params[0]);
            String jsonStringImage = _objectMapper.writeValueAsString(data);

            ApiResponse response = _httpRequest.sendHttpPostRequest(jsonStringImage);

            if (isCancelled()) {
                return Optional.empty();
            }

            return Optional.of(generateImageCaptureResultFromPayload(response));

        } catch (IOException e) {
            Log.e(LOG_TAG, "Caught IOException when trying to caption image with message: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Unpacks Json to turn it into correct form for UI layer
     * @param response The Json string return from HttpRequest
     * @return ImageCaptureResult to be consumed by the image layer
     * @throws IOException If Json is incorrectly formatted
     */
    private ImageCaptionResult generateImageCaptureResultFromPayload(ApiResponse response) throws IOException{
        if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
            /// Use Jackson library to unpack Json string to relevant Neural Guide results classes
            NeuralGuideResult decodedResult = _objectMapper.readValue(response.getResponse(), NeuralGuideResult.class);
            Set<ImprovementTip> tips = _stringMapping.createImprovementTipsSet(decodedResult.getData().getImprovementTips());
            return new ImageCaptionResult(decodedResult.getData().getText(), tips);
        }
        else {
            return new ImageCaptionResult(null, new HashSet<>());
        }
    }

    @Override
    protected void onPostExecute(@NonNull final Optional<ImageCaptionResult> result) {
        _listener.onImageCaptioned(result);
    }

    @Override
    protected void onCancelled(@NonNull final Optional<ImageCaptionResult> result) {
        _listener.onImageCaptioned(result);
    }

    private static final String LOG_TAG = "QueryEndpointTask";
}