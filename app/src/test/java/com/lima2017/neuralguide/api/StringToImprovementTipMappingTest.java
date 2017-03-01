package com.lima2017.neuralguide.api;

import com.lima2017.neuralguide.api.web.StringToImprovementTipMapping;

import org.junit.Test;

import java8.util.Optional;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StringToImprovementTipMappingTest {

    @Test
    public void maps_blurry_to_enum () {
        StringToImprovementTipMapping instance = new StringToImprovementTipMapping();
        Optional<ImprovementTip> result = instance.getImprovementTip("blurry");

        assertEquals(ImprovementTip.TooBlurry, result.get());
    }

    @Test
    public void maps_dark_to_enum () {
        StringToImprovementTipMapping instance = new StringToImprovementTipMapping();
        Optional<ImprovementTip> result = instance.getImprovementTip("dark");

        assertEquals(ImprovementTip.TooDark, result.get());
    }

    @Test
    public void when_given_incorrect_string_returns_empty () {
        StringToImprovementTipMapping instance = new StringToImprovementTipMapping();
        Optional<ImprovementTip> result = instance.getImprovementTip("hello");
        assertFalse(result.isPresent());
    }
}
