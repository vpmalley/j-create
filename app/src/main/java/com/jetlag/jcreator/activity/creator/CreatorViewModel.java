package com.jetlag.jcreator.activity.creator;

import com.jetlag.jcreator.pictures.Picture;
import com.jetlag.jcreator.stories.Story;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vince on 04/06/16.
 */
public class CreatorViewModel {

  private List<Picture> selectablePictures;
  private List<Picture> selectedPictures;
  private String storyText;
  private List<Story> stories;

  public CreatorViewModel() {
    selectablePictures = new ArrayList<>();
    selectedPictures = new ArrayList<>();
    stories = new ArrayList<>();
  }

  public List<Picture> getSelectablePictures() {
    return selectablePictures;
  }

  public void setSelectablePictures(List<Picture> selectablePictures) {
    this.selectablePictures.clear();
    this.selectablePictures.addAll(selectablePictures);
  }

  public List<Picture> getSelectedPictures() {
    return selectedPictures;
  }

  public void setSelectedPictures(List<Picture> selectedPictures) {
    this.selectedPictures.clear();
    this.selectedPictures.addAll(selectedPictures);
  }

  public void updateSelectedPictures() {
    selectedPictures.clear();
    for (Picture p : selectablePictures) {
      if (p.isPicked()) {
        selectedPictures.add(p);
      }
    }
  }

  public void setStoryText(String storyText) {
    this.storyText = storyText;
  }

  public String getStoryText() {
    return storyText;
  }

  public List<Story> getStories() {
    return stories;
  }

  public void addStory(Story story) {
    this.stories.add(story);
  }

  public void commitStory() {
    Story story = new Story();
    story.setPictures(new ArrayList<>(getSelectedPictures()));
    story.setStoryText(getStoryText());
    addStory(story);
    setStoryText("");
    getSelectedPictures().clear();
  }
}
