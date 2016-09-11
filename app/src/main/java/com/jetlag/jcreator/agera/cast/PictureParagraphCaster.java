package com.jetlag.jcreator.agera.cast;

import android.support.annotation.NonNull;

import com.google.android.agera.Function;
import com.jetlag.jcreator.paragraph.Paragraph;
import com.jetlag.jcreator.paragraph.picture.PictureParagraph;

/**
 * Created by vince on 11/09/16.
 */
public class PictureParagraphCaster implements Function<PictureParagraph, Paragraph> {
  @NonNull
  @Override
  public Paragraph apply(@NonNull PictureParagraph input) {
    return input;
  }
}
