package com.jetlag.jcreator.activity.creator;

import com.jetlag.jcreator.pictures.Picture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vince on 04/06/16.
 */
public class CreatorPresenter {

  private List<Picture> selectablePictures;
  private List<Picture> selectedPictures;
  private String text;

  public CreatorPresenter() {
    selectablePictures = new ArrayList<>();
    selectedPictures = new ArrayList<>();
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

  public void setText(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }
}
