package com.lima2017.neuralguide.api.web;

import com.lima2017.neuralguide.api.ImageCaptionResult;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Takes the ApiResponse returned by the server and creates from it an image capture result.
 */
public class IncomingPayloadDecoder {
    /** Part of the jackson library used to unpack Json*/
    private final ObjectMapper _objectMapper;

    public IncomingPayloadDecoder(){
        //Todo dependency injection
        _objectMapper = new ObjectMapper();
    }

    /**
     * Unpacks Json to turn it into correct form for UI layer
     * @param payload The Json string return from HttpRequest
     * @return ImageCaptureResult to be consumed by the image layer
     * @throws IOException If Json is incorrectly formatted
     */
    public ImageCaptionResult generateImageCaptureResultFromPayload(ApiResponse payload) throws IOException{
        if (payload.getStatusCode() >= 200 && payload.getStatusCode() < 300) {
            /// Use Jackson library to unpack Json string to relevant Neural Guide results classes
            NeuralGuideResult decodedResult = _objectMapper.readValue(payload.getResponse(), NeuralGuideResult.class);

            // Extract relevant data for UI layer
            return new ImageCaptionResult(payload.getStatusCode(), decodedResult.getData());
        }
        else {
            return new ImageCaptionResult(payload.getStatusCode());
        }
    }
}
