package com.jetlag.jcreator.merger;

import android.support.annotation.NonNull;

import com.google.android.agera.Merger;
import com.jetlag.jcreator.paragraph.Paragraph;

import java.util.ArrayList;

/**
 * Created by vince on 09/09/16.
 */
public class ParagraphListMerger implements Merger<Paragraph, ArrayList<Paragraph>, ArrayList<Paragraph>> {
  @NonNull
  @Override
  public ArrayList<Paragraph> merge(@NonNull Paragraph nextParagraph, @NonNull ArrayList<Paragraph> paragraphs) {
    paragraphs.add(nextParagraph);
    return paragraphs;
  }
}
