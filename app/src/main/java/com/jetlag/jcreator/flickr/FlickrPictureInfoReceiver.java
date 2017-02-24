package com.jetlag.jcreator.flickr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jetlag.jcreator.pictures.UploadedPicture;

/**
 * Created by vince on 19/02/17.
 */

public class FlickrPictureInfoReceiver extends BroadcastReceiver {

    public interface PictureInfoListener {
        void onPictureInfoReceived(UploadedPicture uploadedPicture);
    }

    private PictureInfoListener listener;

    public void registerListener(PictureInfoListener listener) {
        this.listener = listener;
    }

    public void unregisterListener() {
        this.listener = null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (listener != null) {
            UploadedPicture uploadedPicture = intent.getParcelableExtra(FlickrPictureInfoService.EXTRA_UPLOADED_PICTURE);
            listener.onPictureInfoReceived(uploadedPicture);
        }
    }
}
