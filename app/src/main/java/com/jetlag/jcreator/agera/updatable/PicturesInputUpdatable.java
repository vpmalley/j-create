package com.jetlag.jcreator.agera.updatable;

import com.google.android.agera.Repository;
import com.google.android.agera.Updatable;
import com.jetlag.jcreator.activity.chatstory.ChatStoryDisplay;
import com.jetlag.jcreator.pictures.DevicePicture;

import java.util.ArrayList;

/**
 * Created by vince on 09/09/16.
 */
public class PicturesInputUpdatable implements Updatable {

  private final ChatStoryDisplay chatStoryDisplay;
  private final Repository<ArrayList<DevicePicture>> picturesRepo;

  public PicturesInputUpdatable(ChatStoryDisplay chatStoryDisplay, Repository<ArrayList<DevicePicture>> picturesRepo) {
    this.chatStoryDisplay = chatStoryDisplay;
    this.picturesRepo = picturesRepo;
  }

  @Override
  public void update() {
    chatStoryDisplay.displayPictures(picturesRepo.get());
  }
}
