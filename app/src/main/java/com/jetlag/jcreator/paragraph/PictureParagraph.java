package com.jetlag.jcreator.paragraph;

import com.jetlag.jcreator.pictures.Picture;

import java.util.List;

/**
 * Created by vince on 09/09/16.
 */
public class PictureParagraph implements Paragraph {

  private final List<Picture> pictures;

  public PictureParagraph(List<Picture> pictures) {
    this.pictures = pictures;
  }

  public List<Picture> getPictures() {
    return pictures;
  }
}
