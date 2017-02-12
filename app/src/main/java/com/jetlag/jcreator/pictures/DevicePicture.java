package com.jetlag.jcreator.pictures;

import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by vince on 17/04/16.
 */
public class DevicePicture {

  private String storeId;
  private double latitude;
  private double longitude;
  private String description;
  private boolean picked;

  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Uri getUri() {
    return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, storeId);
  }

  public void setPicked(boolean picked) {
    this.picked = picked;
  }

  public boolean isPicked() {
    return picked;
  }
}
