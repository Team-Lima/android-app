package com.lima2017.neuralguide.api.web;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Manages the creation of the http post request using the apache library.
 */
public class HttpRequestManager {
    private final WebApiConfig _config;
    private final HttpPost _httpPost;

    public HttpRequestManager(WebApiConfig config) {
        _config = config;
        _httpPost = new HttpPost(_config.getUrl());//Does this need dependency injecting
    }

    /**
     * Execution of the http post request
     * @param data the Json string that is the payload to be sent
     * @return returns an Api config holding status and payload if returned
     * @throws IOException
     */
    public ApiResponse sendHttpPostRequest(String data) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();

        // Creating post request and setting up given string as entity
        _httpPost.setEntity(new StringEntity(data));

        // Executing the request and adapting response
        HttpResponse httpResponse = httpClient.execute(_httpPost);

        // Checks if it has a valid status i.e if an entity can be reliably got from response
        // If so returns response as string
        // Else throws http error - subclass of IOException

        int status = httpResponse.getStatusLine().getStatusCode();

        if (status >= 200 && status < 300) {
            HttpEntity httpEntity = httpResponse.getEntity();
            String payload = httpEntity != null ? EntityUtils.toString(httpEntity) : null;
            return new ApiResponse(status, payload);
        }
        else {
            Log.d(LOG_TAG, "Request with status " + status + " returned!");
            return new ApiResponse(status);
        }
    }

    private static final String LOG_TAG = "Http Request Manager";

    void abort() {
        _httpPost.abort();
    }
}