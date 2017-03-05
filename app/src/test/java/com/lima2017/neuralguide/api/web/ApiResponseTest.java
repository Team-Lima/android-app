package com.lima2017.neuralguide.api.web;

import org.junit.Test;

import java8.util.Optional;

import static org.junit.Assert.assertEquals;

public class ApiResponseTest {
    int statusCode = 100;
    String responseString = "Hello";

    @Test
    public void On_creation_with_status_code_and_response_string () throws Exception {

        ApiResponse instance = new ApiResponse(statusCode, responseString);

        assertEquals(statusCode, instance.getStatusCode());
        assertEquals(responseString, instance.getResponse().get());
    }

    @Test
    public void On_creation_with_only_status_code () throws Exception {
        ApiResponse instance = new ApiResponse(statusCode);

        assertEquals(statusCode, instance.getStatusCode());
        assertEquals(Optional.empty(), instance.getResponse());
    }
}