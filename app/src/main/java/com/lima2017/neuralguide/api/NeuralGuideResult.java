package com.lima2017.neuralguide.api;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Represents the formal of the Json returned by the server
 */

public class NeuralGuideResult {
    /**Need to clarify if this http success or otherwise*/
    private boolean mSuccess;
    /**Status code of the HttpResponce, gives success - is this coupling to Http*/
    private int mStatusCode;
    /**The data returned by the server that is sent held in NeuralGuideResultData*/
    private NeuralGuideResultData mData;


    @JsonProperty("success")
    /**
     * @return The success of the operation
     */
    public boolean getSuccess(){
        return mSuccess;
    }

    /**
     * @param success Set by the Json libary(need to check if needed)
     */
    public void setSuccess(boolean success){
        this.mSuccess = success;
    }

    @JsonProperty("status")
    /**
     * @return The status code returned within the operation
     */
    public int getStatusCode(){
        return mStatusCode;
    }

    /**
     * @param status_code Set by the Json libary(need to check if needed)
     */
    public void setStatus_code(int status_code) {
        this.mStatusCode = status_code;
    }

    @JsonProperty("data")
    /**
     * @return The data returned by the server passsed on to the user via ImageCaptionResult
     */
    public NeuralGuideResultData getData() {
        return  mData;
    }

    /**
     * @param data Set by the Json libary(need to check if needed)
     */
    public void setData(NeuralGuideResultData data){
        this.mData = data;
    }
}
