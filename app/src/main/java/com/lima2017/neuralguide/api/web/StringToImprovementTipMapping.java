package com.lima2017.neuralguide.api.web;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lima2017.neuralguide.api.ImprovementTip;

import java.util.HashSet;

import java8.util.Optional;

/**
 * Maps the strings returned from the server to the ImprovementTips.
 *
 * @author Tamara Norman
 * @author Henry Thompson
 * @version 1.0
 */
public class StringToImprovementTipMapping {
    /**
     * Maps the string format of the improvement tip to the Improvement enum to be passed to the client.
     * @param text The string from the server to be mapped.
     * @return The improvement tip which is mapped to the given string.
     */
    @NonNull
    public Optional<ImprovementTip> getImprovementTip(@NonNull String text){
        switch (text) {
            case "blurry": return Optional.of(ImprovementTip.TooBlurry);
            case "dark": return Optional.of(ImprovementTip.TooDark);
        }

        return Optional.empty();
    }

    /**
     * Maps a list of strings representing the improvement tip from the JSON API to the Java enum
     * representation as a set to remove duplicates.
     * @param improvementTips The list of strings representing the improvement tip from the JSON API.
     * @return The set of improvement tips which are mapped to the given string.
     */
    @NonNull
    public HashSet<ImprovementTip> createImprovementTipsSet(@Nullable final String[] improvementTips) {
        final HashSet<ImprovementTip> set = new HashSet<>();

        if (improvementTips != null){
            for (final String tip: improvementTips) {
                Optional<ImprovementTip> tipEnum = getImprovementTip(tip);

                if (tipEnum.isPresent()) {
                    set.add(tipEnum.get());
                }
            }
        }

        return set;
    }
}