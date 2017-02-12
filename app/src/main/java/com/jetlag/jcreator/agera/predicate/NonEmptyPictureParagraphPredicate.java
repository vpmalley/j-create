package com.jetlag.jcreator.agera.predicate;

import android.support.annotation.NonNull;

import com.google.android.agera.Predicate;
import com.jetlag.jcreator.paragraph.picture.DevicePictureParagraph;

/**
 * Created by vince on 11/09/16.
 */
public class NonEmptyPictureParagraphPredicate implements Predicate<DevicePictureParagraph> {
  @Override
  public boolean apply(@NonNull DevicePictureParagraph value) {
    return !value.getDevicePictures().isEmpty();
  }
}
