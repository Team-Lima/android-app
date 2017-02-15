package com.lima2017.neuralguide.api.web;

import android.support.annotation.NonNull;

/**
 * Represents the configuration of the web API to be used by the API querying task.
 */
public class WebApiConfig {
    /** The version of the endpoint to query */
    private final String _version;

    /** The base URL at which the endpoint is hosted */
    private final String _baseUrl;

    /**
     * Intialises a Web API configuration with the default values. This includes using the
     * latest available version and the default base URL.
     */
    public WebApiConfig() {
        this("1", "neuralguide.com");
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
     * @return The verion of the Web API to query.
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
        return _baseUrl + "/v" + _version + "/caption";
    }
}