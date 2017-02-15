package com.lima2017.neuralguide.api.web;

import com.lima2017.neuralguide.api.ImageCaptionResult;
import com.lima2017.neuralguide.api.NeuralGuideResult;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Takes the Json returned by the server and creates from it an image capture result.
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
    public ImageCaptionResult generateImageCaptureResultFromPayload(String payload) throws IOException{
        /** Use Jackson library to unpack Json string to relevant Neural Guide results classes*/
        NeuralGuideResult decodedResult = _objectMapper.readValue(payload, NeuralGuideResult.class);

        /** Extract relevant data for UI layer */
        return new ImageCaptionResult(decodedResult.getStatusCode(), decodedResult.getData().getText());
    }
}
