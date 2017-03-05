package com.lima2017.neuralguide.api.web;

import java.io.IOException;

/**
 * Created by Tamara Norman on 02/03/2017.
 */
public interface IHttpRequestManager {
    ApiResponse sendHttpPostRequest(String data) throws IOException;

    long getTimeout();

    void abort();
}
