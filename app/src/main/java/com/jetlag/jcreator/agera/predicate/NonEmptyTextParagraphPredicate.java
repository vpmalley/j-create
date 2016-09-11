package com.jetlag.jcreator.agera.predicate;

import android.support.annotation.NonNull;

import com.google.android.agera.Predicate;
import com.jetlag.jcreator.paragraph.text.TextParagraph;

/**
 * Created by vince on 11/09/16.
 */
public class NonEmptyTextParagraphPredicate implements Predicate<TextParagraph> {
  @Override
  public boolean apply(@NonNull TextParagraph value) {
    return !value.getText().isEmpty();
  }
}
