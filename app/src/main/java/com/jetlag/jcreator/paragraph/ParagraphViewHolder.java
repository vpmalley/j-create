package com.jetlag.jcreator.paragraph;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by vince on 28/08/16.
 */
public class ParagraphViewHolder extends RecyclerView.ViewHolder {

  private final TextView paragraphView;

  public ParagraphViewHolder(View itemView, TextView paragraphView) {
    super(itemView);
    this.paragraphView = paragraphView;
  }

  public TextView getParagraphView() {
    return paragraphView;
  }
}
