package com.lima2017.neuralguide.api;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Tamara Norman on 14/02/2017.
 */

public class NeuralGuideData {
    private final byte[] data;

    public NeuralGuideData(byte[] data){
        this.data = data;
    }

    @JsonProperty("data")
    public byte[] getData(){
        return data;
    }

}
