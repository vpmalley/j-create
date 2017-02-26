package com.jetlag.jcreator.pictures;

import android.os.Parcel;
import android.os.Parcelable;

import com.googlecode.flickrjandroid.photos.Photo;

/**
 * Created by vince on 12/02/17.
 */

public class UploadedPicture implements Picture, Parcelable {

    private final String flickrId;
    private final String url;

    public UploadedPicture(Photo photo) {
        flickrId = photo.getId();
        url = photo.getUrl();
    }

    public String getFlickrId() {
        return flickrId;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<UploadedPicture> CREATOR
            = new Parcelable.Creator<UploadedPicture>() {
        public UploadedPicture createFromParcel(Parcel in) {
            return new UploadedPicture(in);
        }

        public UploadedPicture[] newArray(int size) {
            return new UploadedPicture[size];
        }
    };

    private UploadedPicture(Parcel in) {
        flickrId = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(flickrId);
        parcel.writeString(url);
    }
}
