package com.lima2017.neuralguide.api.web;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OutgoingRequestPayloadCreatorTest {
    @Test
    public void when_given_valid_byte_array_produces_string () throws Exception {
        byte[] bytes = new byte[10];
        for (byte i = 0; i < 10; i++){
            bytes[i] = i;
        }

        OutgoingRequestPayloadCreator instance = new OutgoingRequestPayloadCreator();
        String result = instance.generateOutgoingJsonString(bytes);

        assertEquals("{\"data\":\"AAECAwQFBgcICQ==\"}", result);
    }

    @Test
    public void when_given_empty_byte_array_sends_Json_with_null_data () throws Exception {
        byte[] bytes = null;

        OutgoingRequestPayloadCreator instance = new OutgoingRequestPayloadCreator();
        String result = instance.generateOutgoingJsonString(bytes);

        assertEquals("{\"data\":null}", result);
    }
}