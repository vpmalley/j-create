package com.jetlag.jcreator.agera.observable;

import com.google.android.agera.BaseObservable;

/**
 * Created by vince on 09/09/16.
 */
public class GalleryPicturesGetterObservable extends BaseObservable {
  public void retrievePics() {
    dispatchUpdate();
  }
}
