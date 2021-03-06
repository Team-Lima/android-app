package com.lima2017.neuralguide.api.web;

import java8.util.Optional;

/**
 * DTO to hold the data returned from the Api request before decoding.
 *
 * @author Tamara Norman
 * @version 1.0
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

    public Optional<String> getResponse() {
        return Optional.ofNullable(_response);
    }
}