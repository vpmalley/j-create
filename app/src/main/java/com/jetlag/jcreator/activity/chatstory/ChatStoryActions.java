package com.jetlag.jcreator.activity.chatstory;

import com.jetlag.jcreator.pictures.Picture;

import java.util.ArrayList;

/**
 * Created by vince on 31/08/16.
 */
public interface ChatStoryActions {

  void createRepos();

  void addTextParagraph(String newParagraph);

  void addPictureParagraph(ArrayList<Picture> newPictures);

  void bindUpdatables(ChatStoryDisplay chatStoryDisplay);

  void unbindUpdatables();

  void getLatestPicturesOnDevice();
}
