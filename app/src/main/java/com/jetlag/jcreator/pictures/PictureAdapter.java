package com.jetlag.jcreator.pictures;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jetlag.jcreator.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vince on 24/05/16.
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureViewHolder> {

  private final Activity activity;

  private final int resource;

  private List<Picture> pictures;

  private final boolean clickablePics;

  public PictureAdapter(Activity activity, int resource, List<Picture> objects, boolean clickablePics) {
    super();
    this.activity = activity;
    this.resource = resource;
    this.pictures = objects;
    this.clickablePics = clickablePics;
  }

  public void setPictures(List<Picture> pictures) {
    this.pictures = pictures;
  }

  public ArrayList<Picture> getPickedPictures() {
    ArrayList<Picture> pickedPictures = new ArrayList<>();
    for (Picture p : pictures) {
      if (p.isPicked()) {
        pickedPictures.add(p);
      }
    }
    return pickedPictures;
  }

  public void resetPickedPictures() {
    for (Picture p : pictures) {
      p.setPicked(false);
    }
  }

  @Override
  public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(resource, parent, false);
    ImageView pictureView = (ImageView) v.findViewById(R.id.picture);
    return new PictureViewHolder(v, pictureView);
  }

  @Override
  public void onBindViewHolder(PictureViewHolder pictureHolder, int position) {
    Picture p = pictures.get(position);
    ImageView pictureView = pictureHolder.getPictureView();
    Glide
        .with(activity)
        .load(p.getUri())
        .into(pictureView);

    if (clickablePics) {
      pictureView.setOnClickListener(new OnPictureClickListener(pictureHolder));
      if (p.isPicked()) {
        pictureView.setBackgroundColor(pictureView.getResources().getColor(android.R.color.holo_blue_dark));
      } else {
        pictureView.setBackgroundColor(ContextCompat.getColor(pictureView.getContext(), R.color.transparent));
      }
    }
  }

  @Override
  public int getItemCount() {
    return pictures.size();
  }

  public class OnPictureClickListener implements View.OnClickListener {

    private final PictureViewHolder pictureViewHolder;

    public OnPictureClickListener(PictureViewHolder pictureViewHolder) {
      this.pictureViewHolder = pictureViewHolder;
    }

    @Override
    public void onClick(View view) {
      Picture picture = getPicture();
      picture.setPicked(!picture.isPicked());
      if (picture.isPicked()) {
        view.setBackgroundColor(view.getResources().getColor(android.R.color.holo_blue_dark));
      } else {
        view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.transparent));
      }
    }

    private Picture getPicture() {
      int adapterPosition = pictureViewHolder.getAdapterPosition();
      return PictureAdapter.this.pictures.get(adapterPosition);
    }
  }
}
