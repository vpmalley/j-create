package com.jetlag.jcreator.activity.uploading;

import com.jetlag.jcreator.flickr.FlickrPictureInfoReceiver;
import com.jetlag.jcreator.flickr.FlickrUploadStateReceiver;

/**
 * Created by vince on 26/02/17.
 */

public interface UploadingActions extends FlickrUploadStateReceiver.UploadEndListener,
        FlickrPictureInfoReceiver.PictureInfoListener {

    void bindUpdatables(UploadingDisplay uploadingDisplay);

    void getUploadingPictures();

    void cancelPendingUploads();
}
