package com.jetlag.jcreator.stories;

import com.jetlag.jcreator.pictures.Picture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vince on 12/06/16.
 */
public class Story {

  private List<Picture> pictures;
  private String storyText;

  public Story() {
    pictures = new ArrayList<>();
  }

  public List<Picture> getPictures() {
    return pictures;
  }

  public void setPictures(List<Picture> pictures) {
    this.pictures.addAll(pictures);
  }

  public String getStoryText() {
    return storyText;
  }

  public void setStoryText(String storyText) {
    this.storyText = storyText;
  }
}
