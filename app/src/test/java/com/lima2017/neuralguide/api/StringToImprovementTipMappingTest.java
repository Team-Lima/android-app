package com.lima2017.neuralguide.api;

import com.lima2017.neuralguide.api.web.StringToImprovementTipMapping;

import org.junit.Test;

import java.util.Set;

import java8.util.Optional;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StringToImprovementTipMappingTest {
    private static final String BLURRY = "blurry";
    private static final String DARK = "dark";

    @Test
    public void maps_blurry_to_enum() {
        StringToImprovementTipMapping instance = new StringToImprovementTipMapping();
        Optional<ImprovementTip> result = instance.getImprovementTip(BLURRY);

        assertEquals(ImprovementTip.TooBlurry, result.get());
    }

    @Test
    public void maps_dark_to_enum() {
        StringToImprovementTipMapping instance = new StringToImprovementTipMapping();
        Optional<ImprovementTip> result = instance.getImprovementTip(DARK);

        assertEquals(ImprovementTip.TooDark, result.get());
    }

    @Test
    public void when_given_incorrect_string_returns_empty() {
        StringToImprovementTipMapping instance = new StringToImprovementTipMapping();
        Optional<ImprovementTip> result = instance.getImprovementTip("hello");
        assertFalse(result.isPresent());
    }

    @Test
    public void when_given_empty_array_returns_empty_hash_set() {
        StringToImprovementTipMapping instance = new StringToImprovementTipMapping();
        Set<ImprovementTip> result = instance.createImprovementTipsSet(null);

        assertEquals(true, result.isEmpty());
    }

    @Test
    public void when_given_array_with_correct_strings_returns_set() {
        StringToImprovementTipMapping instance = new StringToImprovementTipMapping();
        Set<ImprovementTip> result = instance.createImprovementTipsSet(new String[]{"dark", "blurry"});

        assertEquals(true, result.contains(ImprovementTip.TooDark));
        assertEquals(true, result.contains(ImprovementTip.TooBlurry));
    }

    @Test
    public void when_given_array_contains_a_mix_of_valid_and_invalid_set_contains_only_valid() {
        StringToImprovementTipMapping instance = new StringToImprovementTipMapping();
        Set<ImprovementTip> result = instance.createImprovementTipsSet(new String[]{"dark", "hello"});

        assertEquals(true, result.contains(ImprovementTip.TooDark));
        assertEquals(1, result.size());
    }
}
