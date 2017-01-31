package uk.ac.cam.cl.lima2017.neuralguide.api;

import java.net.HttpURLConnection;

/**
 * A DTO for passing the results of an image captioning attempt to the user.
 *
 * For Neural Guide WebAPI v0.1
 */

public class ImageCaptionResult {
    private final int _statusCode;
    private final String _caption;

    public ImageCaptionResult(final int statusCode, final String caption) {
        _statusCode = statusCode;
        _caption = caption;
    }

    public boolean success() {
        return _statusCode == HttpURLConnection.HTTP_OK;
    }

    public int getStatusCode() {
        return _statusCode;
    }

    public String getCaption() {
        return _caption;
    }
}