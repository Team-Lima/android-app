package com.lima2017.neuralguide.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class NeuralGuideDataTest {
    @Test
    public void on_creation_with_valid_byte_array () {
        byte[] data = new byte[10];
        for (byte i = 0; i < 10; i++){
            data[i] = i;
        }

        NeuralGuideData instance = new NeuralGuideData(data);
        assertEquals(data, instance.getData());
    }

    @Test
    public void on_creation_with_null_byte_array () {
        NeuralGuideData instance = new NeuralGuideData(null);
        assertNull(instance.getData());
    }
}
