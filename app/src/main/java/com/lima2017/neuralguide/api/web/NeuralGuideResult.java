package com.lima2017.neuralguide.api.web;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the formal of the Json returned by the server.
 *
 * @author Tamara Norman
 * @version 1.0
 */
public class NeuralGuideResult {
    /**Success of http request overall operation*/
    private boolean mSuccess;
    /**Status code of the HttpResponse, gives success*/
    private int mStatusCode;
    /**The data returned by the server that is sent held in NeuralGuideResultData*/
    private NeuralGuideResultData mData;


    /**
     * @return The success of the operation
     */
    @JsonProperty("success")
    public boolean getSuccess(){
        return mSuccess;
    }

    /**
     * @param success Sets the success of the overall operation from the Json
     */
    public void  setSuccess(boolean success){
        mSuccess = success;
    }

    /**
     * @return The status code returned within the operation
     */
    @JsonProperty("status")
    public int getStatusCode(){
        return mStatusCode;
    }

    /**
     * @param statusCode Sets the status code of the operation from the Json
     */
    public void setStatusCode(int statusCode){
        mStatusCode = statusCode;
    }

    /**
     * @return The data returned by the server passsed on to the user via ImageCaptionResult
     */
    @JsonProperty("data")
    public NeuralGuideResultData getData() {
        return  mData;
    }

    /**
     * @param data Sets the data from the server and unpacks it into the NeuralGuideResult class
     */
    public void setData(NeuralGuideResultData data){
        mData = data;
    }
}
