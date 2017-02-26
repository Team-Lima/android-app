package com.lima2017.neuralguide.api.web;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the format of the Json request to be sent to the server via the web API.
 */
public class NeuralGuideData {
    /**The image data to send to the server*/
    private final byte[] mData;

    /**
     * Initialises a NeuralGuideData with the specified values
     * @param data The image data to be sent to the server
     */
    public NeuralGuideData(byte[] data){
        this.mData = data;
    }

    /**
     * @return The image data within the NeuralGuideData
     */
    @JsonProperty("data")
    public byte[] getData(){
        return mData;
    }
}