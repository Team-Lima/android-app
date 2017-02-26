package com.lima2017.neuralguide.api.web;

import android.support.annotation.NonNull;

import com.lima2017.neuralguide.api.ImprovementTip;

/**
 * Maps the strings returned from the server to the ImprovementTips.
 */

public class StringToImprovementTipMapping {
    /**
     * Maps the string format of the improvement tip to the Improvement enum to be passed to the client.
     * @param text The string from the server to be mapped.
     * @return The improvement tip which is mapped to the given string.
     */
    @NonNull
    public ImprovementTip getImprovementTip(@NonNull String text){
        switch (text) {
            case "too_blurry": return ImprovementTip.TooBlurry;
            case "too_dark": return  ImprovementTip.TooDark;
        }

        throw new RuntimeException("Missing mapping between String and Improvement Tip");
    }
}