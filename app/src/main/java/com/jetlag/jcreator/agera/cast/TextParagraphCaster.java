package com.jetlag.jcreator.agera.cast;

import android.support.annotation.NonNull;

import com.google.android.agera.Function;
import com.jetlag.jcreator.paragraph.Paragraph;
import com.jetlag.jcreator.paragraph.text.TextParagraph;

/**
 * Created by vince on 11/09/16.
 */
public class TextParagraphCaster implements Function<TextParagraph, Paragraph> {
  @NonNull
  @Override
  public Paragraph apply(@NonNull TextParagraph input) {
    return input;
  }
}
