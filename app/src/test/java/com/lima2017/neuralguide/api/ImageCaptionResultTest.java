package com.lima2017.neuralguide.api;

import com.lima2017.neuralguide.api.web.NeuralGuideResultData;

import org.junit.Test;

import java.util.HashSet;

import java8.util.Optional;

import static org.junit.Assert.assertEquals;

public class ImageCaptionResultTest {
    private final String CAPTION = "A picture of a something";

    @Test
    public void create_with_only_given_status_code_instructor () {
        ImageCaptionResult instance = new ImageCaptionResult(null, new HashSet<>());

        assertEquals(Optional.empty(), instance.getCaption());
        assertEquals(false, instance.success());
        assertEquals(true, instance.getImprovementTips().isEmpty());
    }

    @Test
    public void create_with_caption_without_improvement_tips () {
        ImageCaptionResult result = new ImageCaptionResult(CAPTION, new HashSet<>());

        assertEquals(Optional.of(CAPTION), result.getCaption());
        assertEquals(true, result.success());
        assertEquals(true, result.getImprovementTips().isEmpty());
    }

    @Test
    public void on_creation_without_caption_with_improvement_tips () {

        HashSet<ImprovementTip> tips = new HashSet<>();
        tips.add(ImprovementTip.TooBlurry);
        tips.add(ImprovementTip.TooDark);

        ImageCaptionResult instance = new ImageCaptionResult(null, tips);

        assertEquals(Optional.empty(), instance.getCaption());
        assertEquals(false, instance.success());
        assertEquals(false, instance.getImprovementTips().isEmpty());
        assertEquals(2, instance.getImprovementTips().size());
        assertEquals(true, instance.getImprovementTips().contains(ImprovementTip.TooDark));
        assertEquals(true, instance.getImprovementTips().contains(ImprovementTip.TooBlurry));
    }
}
