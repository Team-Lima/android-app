package com.lima2017.neuralguide.api;

import android.media.Image;

/**
 * A generic representation of the Neural Guide API. This should match
 * exactly the endpoints provided by the JSON API.
 *
 * For Neural Guide WebAPI v0.1.
 *
 * @author Henry Thompson
 * @version 1.0
 */
public interface INeuralGuideApi {
    /**
     * Attempts to caption the image, and returns the result via a callback. The request may be
     * made asynchronously. The callback will receive a DTO which indicates the status of the
     * result - whether it succeeds or fails, and any associated data.
     * @param imageData The bytes representing the JPEG image that we wish to caption.
     * @param callback  The callback that should be invoked when the attempt to caption the image
     *                  is completed.
     */
    void tryCaptionImage(final byte[] imageData, final OnImageCaptionedListener callback);
}
