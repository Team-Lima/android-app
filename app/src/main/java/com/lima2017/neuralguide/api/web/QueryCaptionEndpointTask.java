package com.lima2017.neuralguide.api.web;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.lima2017.neuralguide.Literals;
import com.lima2017.neuralguide.api.NeuralGuideData;
import com.lima2017.neuralguide.api.NeuralGuideResult;
import com.lima2017.neuralguide.api.OnImageCaptionedListener;
import com.lima2017.neuralguide.api.ImageCaptionResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;

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
        //Todo depency inject
    }

    @Override
    protected ImageCaptionResult doInBackground(@NonNull final byte[]... params) {
        try {
            //Todo dependency inject
            ObjectMapper mapper = new ObjectMapper();
            NeuralGuideData data = new NeuralGuideData(params[0]);
            String jsonStringImage = mapper.writeValueAsString(data);

            String jsonStringResult = sendHttpPostRequest(jsonStringImage);
            NeuralGuideResult decodedResult = mapper.readValue(jsonStringResult, NeuralGuideResult.class);
            return new ImageCaptionResult(decodedResult.getStatusCode(), decodedResult.getData().getText());
        } catch (ClientProtocolException e){
            String[] messageArray = e.getMessage().split(" ");
            int status = Integer.getInteger(messageArray[messageArray.length-1]);
            return new ImageCaptionResult(status, Literals.HTTP_REQUEST_FAIL);
        } catch (IOException e) {
            return new ImageCaptionResult(HttpURLConnection.HTTP_UNSUPPORTED_TYPE, Literals.HTTP_REQUEST_FAIL);
        }
    }

    @Override
    protected void onPostExecute(@NonNull final ImageCaptionResult result) {
        _listener.onImageCaptioned(result);
    }

    private String sendHttpPostRequest(String data) throws IOException{

        HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(_config.getUrl());
            httpPost.setEntity(new StringEntity(data));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            int status = httpResponse.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity httpEntity = httpResponse.getEntity();
                return httpEntity != null ? EntityUtils.toString(httpEntity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
    }
}

