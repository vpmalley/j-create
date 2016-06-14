package com.jetlag.jcreator.stories;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jetlag.jcreator.R;
import com.jetlag.jcreator.pictures.PictureAdapter;

import java.util.List;

/**
 * Created by vince on 12/06/16.
 */
public class StoryAdapter extends RecyclerView.Adapter<StoryViewHolder> {


  private final Activity activity;

  private final int resource;

  private final List<Story> stories;

  public StoryAdapter(Activity activity, int resource, List<Story> stories) {
    this.activity = activity;
    this.resource = resource;
    this.stories = stories;
  }

  @Override
  public StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(resource, parent, false);
    RecyclerView picturesView = (RecyclerView) v.findViewById(R.id.pictures);
    TextView storyView = (TextView) v.findViewById(R.id.story);
    return new StoryViewHolder(v, picturesView, storyView);
  }

  @Override
  public void onBindViewHolder(StoryViewHolder holder, int position) {
    Story s = stories.get(position);
    holder.getStoryView().setText(s.getStoryText());
    LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
    holder.getPicturesView().setAdapter(new PictureAdapter(activity, R.layout.picture_thumbnail_cell, s.getPictures(), false));
    holder.getPicturesView().setLayoutManager(layoutManager);
  }

  @Override
  public int getItemCount() {
    return stories.size();
  }
}
