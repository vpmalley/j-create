package com.jetlag.jcreator.paragraph.picture;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jetlag.jcreator.paragraph.ParagraphViewHolder;

/**
 * Created by vince on 28/08/16.
 */
public class PictureParagraphViewHolder extends ParagraphViewHolder {

  private final RecyclerView picturesView;

  public PictureParagraphViewHolder(View itemView, RecyclerView picturesView) {
    super(itemView);
    this.picturesView = picturesView;
  }

  public RecyclerView getPicturesView() {
    return picturesView;
  }
}
