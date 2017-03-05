package com.lima2017.neuralguide.api.web;

import android.support.annotation.NonNull;

/**
 * Represents the configuration of the web API to be used by the API querying task.
 *
 * @author Henry Thompson
 * @author Tamara Norman
 * @version 1.0
 */
public class WebApiConfig {
    /** The version of the endpoint to query */
    private final String _version;

    /** The base URL at which the endpoint is hosted */
    private final String _baseUrl;

    /** The getTimeout after which we give up connecting to the API */
    private long mTimeout = 500000;

    /**
     * Initialises a Web API configuration with the default values. This includes using the
     * latest available version and the default base URL.
     */
    public WebApiConfig() {
        this("1", "ec2-54-215-196-11.us-west-1.compute.amazonaws.com:8000");
    }

    /**
     * Initialise a Web API configuration with the specified values.
     * @param version The version of the web API to query.
     * @param baseUrl The base URL at which the web API is being hosted.
     */
    public WebApiConfig(@NonNull final String version, @NonNull final String baseUrl) {
        _version = version;
        _baseUrl = baseUrl;
    }

    /**
     * @return The version of the Web API to query.
     */
    public String getVersion() {
        return _version;
    }

    /**
     * @return The base URL of the Web API to query.
     */
    public String getBaseUrl() {
        return _baseUrl;
    }

    /**
     * @return The full URL of the Web API to query.
     */
    public String getUrl() {
        return "http://" + _baseUrl + "/v" + _version + "/caption";
    }

    /**
     * Sets the getTimeout after which we should give up trying to connect to the API.
     * @param timeout The getTimeout value, in milliseconds.
     */
    public void setTimeout(final long timeout) {
        mTimeout = timeout;
    }

    /**
     * @return The timeout after which we should give up on trying to caption the image.
     */
    public long getTimeout() {
        return mTimeout;
    }
}