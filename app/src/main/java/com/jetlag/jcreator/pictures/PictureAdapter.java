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

import java.util.List;

/**
 * Created by vince on 24/05/16.
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureViewHolder> {

  private final Activity activity;

  private final int resource;

  private final List<Picture> pictures;

  public PictureAdapter(Activity activity, int resource, List<Picture> objects) {
    super();
    this.activity = activity;
    this.resource = resource;
    this.pictures = objects;
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
    Glide
        .with(activity)
        .load(p.getUri())
        .into(pictureHolder.getPictureView());

    pictureHolder.getPictureView().setOnClickListener(new OnPictureClickListener(pictureHolder));
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
