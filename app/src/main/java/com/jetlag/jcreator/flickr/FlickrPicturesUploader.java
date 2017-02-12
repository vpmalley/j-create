package com.jetlag.jcreator.flickr;


import com.jetlag.jcreator.pictures.DevicePicture;

import java.util.List;

/**
 * Created by vince on 17/04/15.
 */
public interface FlickrPicturesUploader {

  /**
   * Uploads the picture to Flickr. This uses the UI to display authorization request
   *
   * @param picture the picture to upload
   * @return whether the picture is being uploaded
   */
  List<String> uploadPicture(DevicePicture picture);
}
