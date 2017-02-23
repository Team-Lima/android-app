package com.lima2017.neuralguide.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StringToImprovementTipMappingTest {

    @Test
    public void when_given_correct_string_too_blurry () {
        StringToImprovementTipMapping instance = new StringToImprovementTipMapping();
        ImprovementTip result = instance.getImprovementTip("too blurry");

        assertEquals(ImprovementTip.TooBlurry, result);
    }

    @Test
    public void when_given_incorrect_string_throws_exception () {
        StringToImprovementTipMapping instance = new StringToImprovementTipMapping();
        boolean exceptionThrown = false;
        try {
            instance.getImprovementTip("hello");
        } catch (RuntimeException e){
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);
    }
}
