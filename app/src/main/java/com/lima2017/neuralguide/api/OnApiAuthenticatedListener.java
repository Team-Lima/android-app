package com.lima2017.neuralguide.api;

import android.support.annotation.NonNull;

/**
 * Callback for when the Google Sign-In ID token is authenticated with the backend server.
 *
 * @author Henry Thompson
 */
public interface OnApiAuthenticatedListener {
    /**
     * Called when the asynchronous requets to authenticate against the API succeeds.
     * @param id The Token ID returned from the API server.
     */
    void onNeuralGuideAuthenticationSuccess(@NonNull final String id);

    /** Indicates that the server returned that the Google token ID provided was not valid. */
    void onNeuralGuideAuthenticationFailure();

    /**
     * Called when the server was unable to determine whether the Google token ID was valid due to an error.
     */
    void onNeuralGuideAuthenticationError();
}