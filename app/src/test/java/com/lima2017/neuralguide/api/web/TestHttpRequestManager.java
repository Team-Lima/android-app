package com.lima2017.neuralguide.api.web;

import java.io.IOException;

public class TestHttpRequestManager implements IHttpRequestManager {
    @Override
    public ApiResponse sendHttpPostRequest(String data) throws IOException {
        if (data == "{\"data\":\"AAECAwQFBgcICQ==\"}"){
            return new ApiResponse(200, " {\n" +
                    "  \"success\": true,\n" +
                    "  \"status\": 200,\n" +
                    "  \"data\": {\n" +
                    "     \"text\": \"a dog\",\n" +
                    "     \"confidence\": 0.01,\n" +
                    "     \"improvementTips\": [ \"too dark\" ],\n" +
                    "     \"classificationSuccess\": true\n" +
                    "  }\n" +
                    "}\n");
        }
        else {
            return new ApiResponse(100);
        }
    }

    @Override
    public long getTimeout() {
        return 0;
    }

    @Override
    public void abort() {

    }
}
