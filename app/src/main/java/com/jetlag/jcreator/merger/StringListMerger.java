package com.jetlag.jcreator.merger;

import android.support.annotation.NonNull;

import com.google.android.agera.Merger;

import java.util.ArrayList;

/**
 * Created by vince on 09/09/16.
 */
public class StringListMerger implements Merger<String, ArrayList<String>, ArrayList<String>> {
  @NonNull
  @Override
  public ArrayList<String> merge(@NonNull String nextParagraph, @NonNull ArrayList<String> paragraphs) {
    paragraphs.add(nextParagraph);
    return paragraphs;
  }
}
