package com.lima2017.neuralguide.api.web;

/**
 * Holder to hold the data returned from the Api request before decoding.
 */

public class ApiResponse {
    private final int _statusCode;
    private final String _response;

    public ApiResponse(int statusCode, String response){
        _statusCode = statusCode;
        _response = response;
    }

    public ApiResponse(int statusCode){
        _statusCode = statusCode;
        _response = null;
    }


    public int getStatusCode() {
        return _statusCode;
    }

    public String getResponse() {
        return _response;
    }

}
