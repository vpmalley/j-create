package com.jetlag.jcreator.merger;

import android.support.annotation.NonNull;

import com.google.android.agera.Merger;
import com.jetlag.jcreator.paragraph.TextParagraph;

import java.util.ArrayList;

/**
 * Created by vince on 09/09/16.
 */
public class StringListMerger implements Merger<String, ArrayList<TextParagraph>, ArrayList<TextParagraph>> {
  @NonNull
  @Override
  public ArrayList<TextParagraph> merge(@NonNull String nextParagraph, @NonNull ArrayList<TextParagraph> paragraphs) {
    paragraphs.add(new TextParagraph(nextParagraph));
    return paragraphs;
  }
}
