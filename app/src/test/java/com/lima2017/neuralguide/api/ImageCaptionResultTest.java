package com.lima2017.neuralguide.api;

import com.lima2017.neuralguide.api.web.NeuralGuideResultData;

import org.junit.Test;

import java8.util.Optional;

import static org.junit.Assert.assertEquals;

public class ImageCaptionResultTest {
    private final int statusCode = 100;

    @Test
    public void on_creation_when_only_given_status_code_instructor () {
        ImageCaptionResult instance = new ImageCaptionResult(statusCode);

        assertEquals(statusCode, instance.getStatusCode());
        assertEquals(Optional.empty(), instance.getCaption());
        assertEquals(false, instance.success());
        assertEquals(null, instance.getImprovementTips());
    }

    @Test
    public void on_creation_with_caption_without_improvement_tips () {
        NeuralGuideResultData data = new NeuralGuideResultData();
        data.setClassificationSuccess(true);
        data.setText("a dog");

        ImageCaptionResult instance = new ImageCaptionResult(statusCode, data);

        assertEquals(statusCode, instance.getStatusCode());
        assertEquals(Optional.of("a dog"), instance.getCaption());
        assertEquals(true, instance.success());
        assertEquals(true, instance.getImprovementTips().isEmpty());
    }

    @Test
    public void on_creation_without_caption_with_improvement_tips () {
        NeuralGuideResultData data = new NeuralGuideResultData();
        data.setClassificationSuccess(false);
        data.setImprovementTips(new String[]{"too dark", "too blurry"});

        ImageCaptionResult instance = new ImageCaptionResult(statusCode, data);

        assertEquals(statusCode, instance.getStatusCode());
        assertEquals(Optional.empty(), instance.getCaption());
        assertEquals(false, instance.success());
        assertEquals(false, instance.getImprovementTips().isEmpty());
        assertEquals(2, instance.getImprovementTips().size());
        assertEquals(true, instance.getImprovementTips().contains(ImprovementTip.TooDark));
        assertEquals(true, instance.getImprovementTips().contains(ImprovementTip.TooBlurry));
    }
}
