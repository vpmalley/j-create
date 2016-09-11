package com.jetlag.jcreator.agera.predicate;

import android.support.annotation.NonNull;

import com.google.android.agera.Predicate;
import com.jetlag.jcreator.paragraph.picture.PictureParagraph;

/**
 * Created by vince on 11/09/16.
 */
public class NonEmptyPictureParagraphPredicate implements Predicate<PictureParagraph> {
  @Override
  public boolean apply(@NonNull PictureParagraph value) {
    return !value.getPictures().isEmpty();
  }
}
