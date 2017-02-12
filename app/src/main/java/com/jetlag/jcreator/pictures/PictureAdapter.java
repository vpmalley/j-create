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

  private List<DevicePicture> devicePictures;

  private final boolean clickablePics;

  public PictureAdapter(Activity activity, int resource, List<DevicePicture> objects, boolean clickablePics) {
    super();
    this.activity = activity;
    this.resource = resource;
    this.devicePictures = objects;
    this.clickablePics = clickablePics;
  }

  public void setDevicePictures(List<DevicePicture> devicePictures) {
    this.devicePictures = devicePictures;
  }

  public ArrayList<DevicePicture> getPickedPictures() {
    ArrayList<DevicePicture> pickedPictures = new ArrayList<>();
    for (DevicePicture p : devicePictures) {
      if (p.isPicked()) {
        pickedPictures.add(p);
      }
    }
    return pickedPictures;
  }

  public void resetPickedPictures() {
    for (DevicePicture p : devicePictures) {
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
    DevicePicture p = devicePictures.get(position);
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
    return devicePictures.size();
  }

  public class OnPictureClickListener implements View.OnClickListener {

    private final PictureViewHolder pictureViewHolder;

    public OnPictureClickListener(PictureViewHolder pictureViewHolder) {
      this.pictureViewHolder = pictureViewHolder;
    }

    @Override
    public void onClick(View view) {
      DevicePicture picture = getPicture();
      picture.setPicked(!picture.isPicked());
      if (picture.isPicked()) {
        view.setBackgroundColor(view.getResources().getColor(android.R.color.holo_blue_dark));
      } else {
        view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.transparent));
      }
    }

    private DevicePicture getPicture() {
      int adapterPosition = pictureViewHolder.getAdapterPosition();
      return PictureAdapter.this.devicePictures.get(adapterPosition);
    }
  }
}
