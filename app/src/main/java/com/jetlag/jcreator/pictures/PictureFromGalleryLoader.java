package com.jetlag.jcreator.pictures;

import android.content.Context;

import java.util.List;

/**
 * Created by vince on 17/04/16.
 */
public class PictureFromGalleryLoader {

  private final PictureLoadedListener pictureLoadedListener;
  private final GalleryPicturesSupplier galleryPicturesSupplier;

  public PictureFromGalleryLoader(Context context, PictureLoadedListener pictureLoadedListener) {
    galleryPicturesSupplier = new GalleryPicturesSupplier(context);
    this.pictureLoadedListener = pictureLoadedListener;
  }

  public void getLatestPics() {
    List<Picture> pics = galleryPicturesSupplier.get();
    pictureLoadedListener.onPicturesWithLocationLoaded(pics);
  }

}
