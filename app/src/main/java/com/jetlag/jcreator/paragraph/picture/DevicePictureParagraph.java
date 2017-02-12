package com.jetlag.jcreator.paragraph.picture;

import com.jetlag.jcreator.paragraph.Paragraph;
import com.jetlag.jcreator.pictures.DevicePicture;

import java.util.List;

/**
 * Created by vince on 09/09/16.
 */
public class DevicePictureParagraph implements Paragraph {

  private final List<DevicePicture> devicePictures;

  public DevicePictureParagraph(List<DevicePicture> pictures) {
    this.devicePictures = pictures;
  }

  public List<DevicePicture> getDevicePictures() {
    return devicePictures;
  }
}
