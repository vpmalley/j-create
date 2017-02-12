package com.jetlag.jcreator.pictures;

import java.util.List;

/**
 * Created by vince on 25/04/16.
 */
public interface PictureLoadedListener {

  void onPicturesWithLocationLoaded(List<DevicePicture> pictures);
}
