package com.lima2017.neuralguide.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NeuralGuideResultTest {

    @Test
    public void testing_getter_and_setter_for_success () {
        NeuralGuideResult instance = new NeuralGuideResult();
        instance.setSuccess(true);

        assertEquals(true, instance.getSuccess());
    }

    @Test
    public void testing_getter_and_setter_for_status_code () {
        NeuralGuideResult instance = new NeuralGuideResult();
        instance.setStatusCode(100);

        assertEquals(100, instance.getStatusCode());
    }

    @Test
    public void testing_getter_and_setter_for_data () {
        NeuralGuideResult instance = new NeuralGuideResult();
        NeuralGuideResultData data = new NeuralGuideResultData();
        instance.setData(data);

        assertEquals(data, instance.getData());
    }
}
