package com.jetlag.jcreator.stories;

import com.jetlag.jcreator.pictures.Picture;

import java.util.List;

/**
 * Created by vince on 12/06/16.
 */
public class Story {

  private List<Picture> pictures;
  private String storyText;

  public List<Picture> getPictures() {
    return pictures;
  }

  public void setPictures(List<Picture> pictures) {
    this.pictures = pictures;
  }

  public String getStoryText() {
    return storyText;
  }

  public void setStoryText(String storyText) {
    this.storyText = storyText;
  }
}
