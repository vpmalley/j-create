package com.jetlag.jcreator.flickr;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.googlecode.flickrjandroid.photos.Photo;
import com.jetlag.jcreator.pictures.UploadedPicture;

/**
 * Created by vince on 18/02/17.
 */

public class FlickrPictureInfoService extends IntentService {

    public static final String INTENT_FLICKR_PICTURE_INFO_END = "com.jetlag.jcreator.FLICKR_PICTURE_INFO_END";
    public static final String EXTRA_PHOTO_ID = "com.jetlag.jcreator.PHOTO_ID";
    public static final String EXTRA_UPLOADED_PICTURE = "com.jetlag.jcreator.UPLOADED_PICTURE";

    private static final String FLICKR_PICTURE_INFO_SERVICE = "FlickrPictureInfoService";

    public FlickrPictureInfoService() {
        super(FLICKR_PICTURE_INFO_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String photoId = intent.getStringExtra(EXTRA_PHOTO_ID);
        FlickrProvider picturesProvider = new FlickrJPicturesProvider(getApplicationContext());

        Photo flickrPhoto = picturesProvider.getPhotoForId(photoId);
        UploadedPicture uploadedPicture = new UploadedPicture(flickrPhoto);

        Intent retrievalEndLocalIntent =
                new Intent(INTENT_FLICKR_PICTURE_INFO_END)
                        .putExtra(EXTRA_UPLOADED_PICTURE, uploadedPicture);
        LocalBroadcastManager.getInstance(this).sendBroadcast(retrievalEndLocalIntent);
    }
}
