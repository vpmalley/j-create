package com.jetlag.jcreator.flickr;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.jetlag.jcreator.pictures.DevicePicture;

import org.scribe.model.Token;

import java.util.List;

/**
 * Created by vince on 18/02/17.
 */

public class FlickrUploadService extends IntentService {

    public static final String EXTRA_DEVICE_PICTURE = "devicePicture";
    public static final String INTENT_FLICKR_UPLOAD_END = "com.jetlag.jcreator.FLICKR_UPLOAD_END";
    public static final String EXTRA_PHOTO_ID = "com.jetlag.jcreator.PHOTO_ID";

    private static final String FLICKR_UPLOAD_SERVICE = "FlickrUploadService";

    public FlickrUploadService() {
        super(FLICKR_UPLOAD_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DevicePicture pictureToUpload = intent.getParcelableExtra(EXTRA_DEVICE_PICTURE);
        FlickrPicturesUploader uploader = getUploader();

        List<String> photoIds = uploader.uploadPicture(pictureToUpload);

        Intent uploadEndLocalIntent =
                new Intent(INTENT_FLICKR_UPLOAD_END)
                        .putExtra(EXTRA_PHOTO_ID, photoIds.get(0));
        LocalBroadcastManager.getInstance(this).sendBroadcast(uploadEndLocalIntent);
    }

    private FlickrJPicturesUploader getUploader() {
        FlickrOAuthTokenStore flickrOAuthTokenStore = new FlickrOAuthTokenStore();
        Token token = flickrOAuthTokenStore.getStoredToken(getApplicationContext());
        return new FlickrJPicturesUploader(getApplicationContext(), token);
    }
}
