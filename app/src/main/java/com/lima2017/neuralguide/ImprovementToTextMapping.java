package com.lima2017.neuralguide;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.lima2017.neuralguide.api.ImprovementTip;

import java8.util.Optional;

/**
 * Maps the improvement tips returned from the server to the text to be read out to the user.
 *
 * @author Henry Thompson
 * @version 1.0
 */
public class ImprovementToTextMapping {

    /**
     * Maps the improvement tip to the string that should be read out to the user describing it.
     * @param tip The improvement tip from the server to be mapped.
     * @param resources The Android resources used to get the string from.
     * @return The string that is mapped to the improvement tip.
     */
    @NonNull
    public Optional<String> getText(@NonNull ImprovementTip tip, @NonNull Resources resources) {
        switch (tip) {
            case TooBlurry: return Optional.of(resources.getString(R.string.tip_too_blurry));
            case TooDark:   return Optional.of(resources.getString(R.string.tip_too_dark));
        }

        return Optional.empty();
    }
}