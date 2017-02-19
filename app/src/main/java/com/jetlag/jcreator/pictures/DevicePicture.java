package com.jetlag.jcreator.pictures;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

/**
 * Created by vince on 17/04/16.
 */
public class DevicePicture implements Parcelable {

  private String storeId;
  private double latitude;
  private double longitude;
  private String description;
  private boolean picked;

  public DevicePicture() {}

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

  /*
   * Parcelable
   */

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Parcelable.Creator<DevicePicture> CREATOR
          = new Parcelable.Creator<DevicePicture>() {
    public DevicePicture createFromParcel(Parcel in) {
      return new DevicePicture(in);
    }

    public DevicePicture[] newArray(int size) {
      return new DevicePicture[size];
    }
  };

  private DevicePicture(Parcel in) {
    storeId = in.readString();
    latitude = in.readDouble();
    longitude = in.readDouble();
    description = in.readString();
    picked = in.readInt() > 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int flags) {
    parcel.writeString(storeId);
    parcel.writeDouble(latitude);
    parcel.writeDouble(longitude);
    parcel.writeString(description);
    parcel.writeInt(picked ? 1 : 0);
  }

}
