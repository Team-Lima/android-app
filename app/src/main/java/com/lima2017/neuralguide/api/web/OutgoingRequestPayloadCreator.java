package com.lima2017.neuralguide.api.web;

import com.lima2017.neuralguide.api.NeuralGuideData;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Takes the image as a JPEG byte array and turns it into a Json string.
 */
public class OutgoingRequestPayloadCreator {
    /** Part of the jackson library used to generate Json*/
    private final ObjectMapper _objectMapper;

    public OutgoingRequestPayloadCreator(){
        _objectMapper = new ObjectMapper();
    }

    /**
     * Takes the image and converts it to a base64 json string
     * @param imageData the image that has been provided
     * @return the Json string to send to the server
     * @throws IOException
     */
    public String generateOutgoingJsonString(byte[] imageData) throws IOException{
        /** Puts image into class in order for the object mapper to generate Json */
        NeuralGuideData data = new NeuralGuideData(imageData);

        /** Maps class to Json string */
        return _objectMapper.writeValueAsString(data);
    }
}
