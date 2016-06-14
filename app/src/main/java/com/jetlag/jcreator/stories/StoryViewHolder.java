package com.jetlag.jcreator.stories;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by vince on 12/06/16.
 */
public class StoryViewHolder extends RecyclerView.ViewHolder {

  private final RecyclerView picturesView;

  private final TextView storyView;

  public StoryViewHolder(View itemView, RecyclerView picturesView, TextView storyView) {
    super(itemView);
    this.picturesView = picturesView;
    this.storyView = storyView;
  }

  public RecyclerView getPicturesView() {
    return picturesView;
  }

  public TextView getStoryView() {
    return storyView;
  }
}
