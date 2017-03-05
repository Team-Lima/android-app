package com.lima2017.neuralguide.api.web;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lima2017.neuralguide.api.OnApiAuthenticatedListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Asynchronous task for contacting the Neural Guide backend server, to determine whether the
 * Google Sign-In ID token is valid.
 *
 * @author Henry Thompson
 * @version 1.0
 */
public class AuthenticateGoogleAccountTask extends AsyncTask<String, Void, String> {
    /** Callback for observers awaiting the result of the authentication attempt. */
    @NonNull private final OnApiAuthenticatedListener _listener;

    /** The configuration of the web API endpoint. */
    @NonNull private final WebApiConfig _config;

    /**
     * Any exception thrown by the asynchronous task during its call to the server. Set to null if
     * no exceptions were thrown.
     */
    @Nullable private Exception mException = null;

    /**
     * @param listener Callback for updating any listeners to the progress of the authentication
     *                 attempt.
     * @param config The configuration of the web API endpoint.
     */
    @Inject AuthenticateGoogleAccountTask(@NonNull final OnApiAuthenticatedListener listener,
                                         @NonNull final WebApiConfig config) {
        _listener = listener;
        _config = config;
    }

    @Override
    protected String doInBackground(String... params) {
        final String idToken = params[0];

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(_config.getAuthUrl());

        try {
            final List nameValuePairs = new ArrayList(1);
            nameValuePairs.add(new BasicNameValuePair("idToken", idToken));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            final HttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity());
            }
            else {
                return null;
            }

        } catch (IOException e) {
            Log.e(TAG, "Error sending ID token to backend.", e);
            mException = e;
            return null;
        }
    }

    protected void onPostExecute(String id) {
        if (id != null) {
            _listener.onNeuralGuideAuthenticationSuccess(id);
        }
        else if (mException == null) {
            _listener.onNeuralGuideAuthenticationFailure();
        }
        else {
            _listener.onNeuralGuideAuthenticationError();
        }
    }

    /** The Tag used when adding records to the log. */
    private static final String TAG = "Authenticate Account";
}