package com.jetlag.jcreator.pictures;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by vince on 24/05/16.
 */
public class PictureViewHolder extends RecyclerView.ViewHolder {

  private final View mainView;

  private final ImageView pictureView;

  public PictureViewHolder(View mainView, ImageView pictureView) {
    super(pictureView);
    this.mainView = mainView;
    this.pictureView = pictureView;
  }

  public ImageView getPictureView() {
    return pictureView;
  }
}
