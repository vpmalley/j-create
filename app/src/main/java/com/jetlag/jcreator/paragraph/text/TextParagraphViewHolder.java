package com.jetlag.jcreator.paragraph.text;

import android.view.View;
import android.widget.TextView;

import com.jetlag.jcreator.paragraph.ParagraphViewHolder;

/**
 * Created by vince on 28/08/16.
 */
public class TextParagraphViewHolder extends ParagraphViewHolder {

  private final TextView paragraphView;

  public TextParagraphViewHolder(View itemView, TextView paragraphView) {
    super(itemView);
    this.paragraphView = paragraphView;
  }

  public TextView getParagraphView() {
    return paragraphView;
  }
}
