package com.jetlag.jcreator.pictures;

import com.googlecode.flickrjandroid.photos.Photo;

/**
 * Created by vince on 12/02/17.
 */

public class UploadedPicture {

    private final String flickrId;
    private final String url;

    public UploadedPicture(Photo photo) {
        flickrId = photo.getId();
        url = photo.getUrl();
    }

    public String getFlickrId() {
        return flickrId;
    }

    public String getUrl() {
        return url;
    }
}
