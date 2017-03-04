package com.lima2017.neuralguide.api.web;

import com.lima2017.neuralguide.api.ImageCaptionResult;

import org.junit.Test;

import java8.util.Optional;

import static org.junit.Assert.assertEquals;

public class IncomingPayloadDecoderTest {
   /* @Test
    public void when_invalid_status_code_returns_image_caption_result_with_no_data () throws Exception {
        ApiResponse input = new ApiResponse(100);

        IncomingPayloadDecoder instance = new IncomingPayloadDecoder();

        ImageCaptionResult result = instance.generateImageCaptureResultFromPayload(input);

        assertEquals(result.getStatusCode(), 100);
        assertEquals(result.getCaption(), Optional.empty());
        //assertNull(result.getImprovementTips());
        assertEquals(result.success(), false);
    }

    @Test
    public void when_given_valid_Json_with_classification_success () throws Exception {
        ApiResponse input = new ApiResponse(200, " {\n" +
                "  \"success\": true,\n" +
                "  \"status\": 200,\n" +
                "  \"data\": {\n" +
                "     \"text\": \"a dog\",\n" +
                "     \"confidence\": 0.01,\n" +
                "     \"improvementTips\": [ \"too dark\" ],\n" +
                "     \"classificationSuccess\": true\n" +
                "  }\n" +
                "}\n");
        IncomingPayloadDecoder instance = new IncomingPayloadDecoder();

        ImageCaptionResult result = instance.generateImageCaptureResultFromPayload(input);

        assertEquals(result.getStatusCode(), 200);
        assertEquals(result.getCaption(), Optional.of("a dog"));
        //assertNull(result.getImprovementTips());
        assertEquals(result.success(), true);
    }

    @Test
    public void when_given_valid_Json_without_classification_success () throws Exception {
        ApiResponse input = new ApiResponse(200, " {\n" +
                "  \"success\": true,\n" +
                "  \"status\": 200,\n" +
                "  \"data\": {\n" +
                "     \"confidence\": 0.01,\n" +
                "     \"improvementTips\": [ \"too blurry\" ],\n" +
                "     \"classificationSuccess\": false\n" +
                "  }\n" +
                "}\n");
        IncomingPayloadDecoder instance = new IncomingPayloadDecoder();

        ImageCaptionResult result = instance.generateImageCaptureResultFromPayload(input);

        assertEquals(result.getStatusCode(), 200);
        assertEquals(result.getCaption(), Optional.empty());
        //assertEquals(result.getImprovementTips().contains("too blurry"), true);
        assertEquals(result.success(), true);
    }*/
}
