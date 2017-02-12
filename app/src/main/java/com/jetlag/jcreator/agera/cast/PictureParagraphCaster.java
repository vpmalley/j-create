package com.jetlag.jcreator.agera.cast;

import android.support.annotation.NonNull;

import com.google.android.agera.Function;
import com.jetlag.jcreator.paragraph.Paragraph;
import com.jetlag.jcreator.paragraph.picture.DevicePictureParagraph;

/**
 * Created by vince on 11/09/16.
 */
public class PictureParagraphCaster implements Function<DevicePictureParagraph, Paragraph> {
  @NonNull
  @Override
  public Paragraph apply(@NonNull DevicePictureParagraph input) {
    return input;
  }
}
