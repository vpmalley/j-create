package com.jetlag.jcreator.flickr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by vince on 19/02/17.
 */

public class FlickrUploadStateReceiver extends BroadcastReceiver {

    public interface UploadEndListener {
        void onUploadEnd(String photoId);
    }

    private UploadEndListener listener;

    public void registerListener(UploadEndListener listener) {
        this.listener = listener;
    }

    public void unregisterListener() {
        this.listener = null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (listener != null) {
            listener.onUploadEnd(intent.getStringExtra(FlickrUploadService.EXTRA_PHOTO_ID));
        }
    }
}
