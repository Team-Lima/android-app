package com.lima2017.neuralguide;

import com.lima2017.neuralguide.api.ImprovementTip;
import android.support.v4.app.Fragment;
import android.content.res.Resources;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ImprovementToTextMappingTest {

    @Test
    public void when_given_too_blurry_returns_correct_string () {
        ImprovementToTextMapping instance = new ImprovementToTextMapping();
        //Todo add resources
        String result = instance.getText(ImprovementTip.TooBlurry, null);

        //assertEquals("The image was too blurry - hold your phone steadier.", result);
    }

    @Test
    public void when_given_invalid_string_throws_exception () {
        ImprovementToTextMapping instance = new ImprovementToTextMapping();
        //Todo do we need for false
        //String result = instance.getText(ImprovementTip., null);

        //assertEquals("The image was too blurry - hold your phone steadier.", result);
    }
}
