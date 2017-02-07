package com.lima2017.neuralguide.api;

/**
 * Callback indicating that the attempt to caption the provided image via the Neural Guide API
 * has been completed.
 *
 * For Neural Guide WebAPI v0.1.
 *
 * @author Henry Thompson
 * @version 1.0
 */

public interface OnImageCaptionedListener {
    /**
     * Called when the attempt to caption the image has finished.
     * @param result The DTO representing the result of the attempt to caption the image.
     */
    void onImageCaptioned(final ImageCaptionResult result);
}
