package com.lima2017.neuralguide.api;

import com.lima2017.neuralguide.api.web.NeuralGuideResultData;

import org.junit.Test;

import java.util.HashSet;

import java8.util.Optional;

import static org.junit.Assert.assertEquals;

public class ImageCaptionResultTest {
    private final int STATUS_CODE_100 = 100;

    @Test
    public void create_with_only_given_status_code_instructor () {
        ImageCaptionResult instance = new ImageCaptionResult(STATUS_CODE_100);

        assertEquals(STATUS_CODE_100, instance.getStatusCode());
        assertEquals(Optional.empty(), instance.getCaption());
        assertEquals(false, instance.success());
        assertEquals(null, instance.getImprovementTips());
    }

    @Test
    public void create_with_caption_without_improvement_tips () {
        NeuralGuideResultData data = new NeuralGuideResultData();
        data.setClassificationSuccess(true);
        data.setText("a dog");

        ImageCaptionResult instance = new ImageCaptionResult(STATUS_CODE_100, data, new HashSet<>());

        assertEquals(STATUS_CODE_100, instance.getStatusCode());
        assertEquals(Optional.of("a dog"), instance.getCaption());
        assertEquals(true, instance.success());
        assertEquals(true, instance.getImprovementTips().isEmpty());
    }

    @Test
    public void on_creation_without_caption_with_improvement_tips () {
        NeuralGuideResultData data = new NeuralGuideResultData();
        data.setClassificationSuccess(false);

        HashSet<ImprovementTip> tips = new HashSet<>();
        tips.add(ImprovementTip.TooBlurry);
        tips.add(ImprovementTip.TooDark);

        ImageCaptionResult instance = new ImageCaptionResult(STATUS_CODE_100, data, tips);

        assertEquals(STATUS_CODE_100, instance.getStatusCode());
        assertEquals(Optional.empty(), instance.getCaption());
        assertEquals(false, instance.success());
        assertEquals(false, instance.getImprovementTips().isEmpty());
        assertEquals(2, instance.getImprovementTips().size());
        assertEquals(true, instance.getImprovementTips().contains(ImprovementTip.TooDark));
        assertEquals(true, instance.getImprovementTips().contains(ImprovementTip.TooBlurry));
    }
}
