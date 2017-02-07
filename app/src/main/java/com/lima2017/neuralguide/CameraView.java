package com.lima2017.neuralguide;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * This class provides a view showing what is currently seen by the rear camera of the device.
 *
 * @author Henry Thompson, based on https://developer.android.com/guide/topics/media/camera.html
 * @version 1.0
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback {
    /** The surface holder used to maintain the drawn surface */
    private final SurfaceHolder _holder;

    /** The <code>Camera</code> instance repreenting the camera resource the app is holding */
    private final Camera _camera;

    /**
     * Instantiates a Camera view within the given <code>Context</code>.
     * @param context The <code>Context</code> within which this view exists. Usually, this will
     *                be the <code>Activity</code> holding this view.
     * @param camera The <code>Camera</code> resource held by this application. Note that this
     *               application should only ever hold one <code>Camera</code> resource at a time.
     */
    public CameraView(final Context context, final Camera camera) {
        super(context);
        _camera = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        _holder = getHolder();
        _holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            _camera.setPreviewDisplay(holder);
            _camera.startPreview();
        }
        catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(final SurfaceHolder holder) {
        // The Camera should be released by the activity, not here.
    }

    @Override
    public void surfaceChanged(final SurfaceHolder holder, final int format, final int w, final int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (_holder.getSurface() == null){
            // Preview surface does not exist.
            return;
        }

        // Stop preview before making changes.
        try {
            _camera.stopPreview();
        }
        catch (Exception e){
            Log.d(TAG, "Tried to stop a non-exist preview: " + e.getMessage());
        }

        // Set preview size and make any resize, rotate or
        // reformatting changes here.

        // Start preview with new settings.
        try {
            _camera.setPreviewDisplay(_holder);
            _camera.startPreview();
        }
        catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
}