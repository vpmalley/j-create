package com.jetlag.jcreator.activity.chatstory;

import com.jetlag.jcreator.flickr.FlickrUploadStateReceiver;
import com.jetlag.jcreator.pictures.DevicePicture;

import java.util.ArrayList;

/**
 * Created by vince on 31/08/16.
 */
public interface ChatStoryActions extends FlickrUploadStateReceiver.UploadEndListener {

  void createRepos();

  void addTextParagraph(String newParagraph);

  void addPictureParagraph(ArrayList<DevicePicture> newPictures);

  void bindUpdatables(ChatStoryDisplay chatStoryDisplay);

  void unbindUpdatables();

  void getLatestPicturesOnDevice();

  void uploadPictures();
}
