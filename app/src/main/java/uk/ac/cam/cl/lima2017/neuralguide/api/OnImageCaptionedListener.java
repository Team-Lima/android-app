package uk.ac.cam.cl.lima2017.neuralguide.api;

/**
 * Callback indicating that the attempt to caption the provided image via the Neural Guide API
 * has been completed.
 *
 * For Neural Guide WebAPI v0.1
 */

public interface OnImageCaptionedListener {
    void onImageCaptioned(ImageCaptionResult result);
}
