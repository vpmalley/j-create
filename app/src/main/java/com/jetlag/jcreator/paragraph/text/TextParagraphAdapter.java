package com.jetlag.jcreator.paragraph.text;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jetlag.jcreator.R;

/**
 * Created by vince on 28/08/16.
 */
public class TextParagraphAdapter {

  public static final int TEXT_PARAGRAPH_CELL_RESOURCE = R.layout.text_paragraph_cell;

  public TextParagraphViewHolder onCreateViewHolder(ViewGroup parent) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(TEXT_PARAGRAPH_CELL_RESOURCE, parent, false);
    TextView storyView = (TextView) v.findViewById(R.id.paragraph_text);
    return new TextParagraphViewHolder(v, storyView);
  }

  public void onBindViewHolder(TextParagraphViewHolder holder, TextParagraph paragraph) {
    holder.getParagraphView().setText(paragraph.getText());
  }

}
