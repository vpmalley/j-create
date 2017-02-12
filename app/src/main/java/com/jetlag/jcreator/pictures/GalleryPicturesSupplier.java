package com.jetlag.jcreator.pictures;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.agera.Supplier;

import java.util.ArrayList;

/**
 * Created by vince on 06/09/16.
 */
public class GalleryPicturesSupplier implements Supplier<ArrayList<DevicePicture>> {

  private static final String[] PICTURES_PROJECTION = new String[]{
      MediaStore.Images.ImageColumns._ID,
      MediaStore.Images.ImageColumns.DATE_TAKEN,
      MediaStore.Images.ImageColumns.LATITUDE,
      MediaStore.Images.ImageColumns.LONGITUDE,
      MediaStore.Images.ImageColumns.DATA
  };

  private final Context context;

  public GalleryPicturesSupplier(Context context) {
    this.context = context;
  }

  @NonNull
  @Override
  public ArrayList<DevicePicture> get() {
    final Cursor pictureCursor = context.getContentResolver()
        .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, PICTURES_PROJECTION, null,
            null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC limit 10");
    ArrayList<DevicePicture> pics = new ArrayList<>();
    Log.d("pictures", String.valueOf(pictureCursor.getCount()));
    while (pictureCursor.moveToNext()) {
      DevicePicture p = pictureFromCursor(pictureCursor);
      pics.add(p);
      Log.d("pictures", "added pic " + p.getDescription());
    }
    return pics;
  }

  @NonNull
  private DevicePicture pictureFromCursor(Cursor cursor) {
    DevicePicture p = new DevicePicture();
    p.setStoreId(cursor.getString(0));
    if ((cursor.getString(2) != null) && (cursor.getString(3) != null)) {
      p.setLatitude(Double.valueOf(cursor.getString(2)));
      p.setLongitude(Double.valueOf(cursor.getString(3)));
    }
    p.setDescription(cursor.getString(4));
    return p;
  }
}
