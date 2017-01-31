package uk.ac.cam.cl.lima2017.neuralguide.api.web;

import android.media.Image;
import android.os.AsyncTask;

import uk.ac.cam.cl.lima2017.neuralguide.api.OnImageCaptionedListener;
import uk.ac.cam.cl.lima2017.neuralguide.api.ImageCaptionResult;

/**
 * Created by henry on 1/31/17.
 */
public class QueryCaptionEndpointTask extends AsyncTask<Image, Integer, ImageCaptionResult> {
    final OnImageCaptionedListener _listener;

    public QueryCaptionEndpointTask(OnImageCaptionedListener listener) {
        _listener = listener;
    }

    @Override
    protected ImageCaptionResult doInBackground(Image... params) {
        return null;
    }

    @Override
    protected void onPostExecute(ImageCaptionResult result) {
        _listener.onImageCaptioned(result);
    }
}
