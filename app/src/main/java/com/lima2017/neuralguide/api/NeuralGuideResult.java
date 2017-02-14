package com.lima2017.neuralguide.api;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Tamara Norman on 08/02/2017.
 */

public class NeuralGuideResult {
    private boolean success;
    private int status_code;
    private NeuralGuideResultData data;

    @JsonProperty("success")
    public boolean getSuccess(){
        return success;
    }
    public void setSuccess(boolean success){
        this.success = success;
    }

    @JsonProperty("status")
    public int getStatusCode(){
        return status_code;
    }
    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    @JsonProperty("data")
    public NeuralGuideResultData getData() {
        return  data;
    }
    public void setData(NeuralGuideResultData data){
        this.data = data;
    }
}
