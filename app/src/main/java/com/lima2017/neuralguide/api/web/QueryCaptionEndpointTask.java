package com.lima2017.neuralguide.api.web;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

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
import java.io.UnsupportedEncodingException;

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
        try {
            //To be dependency injected
            ObjectMapper mapper = new ObjectMapper();
            NeuralGuideData data = new NeuralGuideData(params[0]);
            String jsonStringImage = mapper.writeValueAsString(data);



            String jsonStringResult = sendPut(jsonStringImage);
            NeuralGuideResult decodedResult = mapper.readValue(jsonStringResult, NeuralGuideResult.class);
            return new ImageCaptionResult(decodedResult.getStatusCode(), decodedResult.getData().getText());
        } catch (IOException e) {
            //What do we want to do on exceptions
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(@NonNull final ImageCaptionResult result) {
        _listener.onImageCaptioned(result);
    }

    private String sendPut(String data) {

        HttpClient httpClient = HttpClients.createDefault();
        try {
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
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            //TODO
        }
        catch (IOException e){
            e.printStackTrace();
            //TODO
        }
        finally {
        }
        return null;
    }
}

