package com.jetlag.jcreator.updatable;

import com.google.android.agera.Repository;
import com.google.android.agera.Updatable;
import com.jetlag.jcreator.activity.chatstory.ChatStoryDisplay;
import com.jetlag.jcreator.pictures.Picture;

import java.util.ArrayList;

/**
 * Created by vince on 09/09/16.
 */
public class PicturesInputUpdatable implements Updatable {

  private final ChatStoryDisplay chatStoryDisplay;
  private final Repository<ArrayList<Picture>> picturesRepo;

  public PicturesInputUpdatable(ChatStoryDisplay chatStoryDisplay, Repository<ArrayList<Picture>> picturesRepo) {
    this.chatStoryDisplay = chatStoryDisplay;
    this.picturesRepo = picturesRepo;
  }

  @Override
  public void update() {
    chatStoryDisplay.displayPictures(picturesRepo.get());
  }
}
