package com.lima2017.neuralguide.api.web;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.lima2017.neuralguide.Literals;
import com.lima2017.neuralguide.api.OnImageCaptionedListener;
import com.lima2017.neuralguide.api.ImageCaptionResult;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * This asynchronous task queries the API endpoint.
 */
public class QueryCaptionEndpointTask extends AsyncTask<byte[], Integer, ImageCaptionResult> {
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
    protected ImageCaptionResult doInBackground(@NonNull final byte[]... params) {
        try {
            /** Generates json string */
            String jsonStringImage = _creator.generateOutgoingJsonString(params[0]);

            /** Preforms http request */
            String jsonStringResult = _httpRequest.sendHttpPostRequest(jsonStringImage);

            /**Decodes Json and passes on ImageCaptureResult to the user */
            return _decoder.generateImageCaptureResultFromPayload(jsonStringResult);
        } catch (ClientProtocolException e){
            /** On client protocol exception pass on given status code and request fail string to user */
            String[] messageArray = e.getMessage().split(" ");
            int status = Integer.getInteger(messageArray[messageArray.length-1]);
            return new ImageCaptionResult(status, Literals.HTTP_REQUEST_FAIL);
        } catch (IOException e) {
            /** On general IOException send fail Http code as well as request fail string to user */
            return new ImageCaptionResult(HttpURLConnection.HTTP_UNSUPPORTED_TYPE, Literals.HTTP_REQUEST_FAIL);
        }
    }

    @Override
    protected void onPostExecute(@NonNull final ImageCaptionResult result) {
        _listener.onImageCaptioned(result);
    }
}
