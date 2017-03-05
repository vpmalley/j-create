package com.jetlag.jcreator.paragraph.picture;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jetlag.jcreator.R;
import com.jetlag.jcreator.pictures.PictureAdapter;

/**
 * Created by vince on 28/08/16.
 */
public class PictureParagraphAdapter {

  private static final int PICTURE_PARAGRAPH_CELL_RESOURCE = R.layout.picture_paragraph_cell;
  private static final int PICTURE_THUMBNAIL_CELL_RESOURCE = R.layout.picture_thumbnail_cell;

  private final Activity activity;

  public PictureParagraphAdapter(Activity activity) {
    this.activity = activity;
  }

  public PictureParagraphViewHolder onCreateViewHolder(ViewGroup parent) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(PICTURE_PARAGRAPH_CELL_RESOURCE, parent, false);
    RecyclerView picturesView = (RecyclerView) v.findViewById(R.id.paragraph_pictures);
    return new PictureParagraphViewHolder(v, picturesView);
  }

  public void onBindViewHolder(PictureParagraphViewHolder holder, DevicePictureParagraph paragraph) {
    RecyclerView picturesView = holder.getPicturesView();
    LinearLayoutManager layoutManager = new LinearLayoutManager(picturesView.getContext(), LinearLayoutManager.HORIZONTAL, false);
    picturesView.setAdapter(new PictureAdapter(activity, PICTURE_THUMBNAIL_CELL_RESOURCE, paragraph.getDevicePictures(), false));
    picturesView.setLayoutManager(layoutManager);
  }

}
