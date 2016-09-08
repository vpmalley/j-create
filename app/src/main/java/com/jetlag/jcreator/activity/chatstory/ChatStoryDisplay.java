package com.jetlag.jcreator.activity.chatstory;

import com.jetlag.jcreator.pictures.Picture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vince on 31/08/16.
 */
public interface ChatStoryDisplay {

  void displayParagraphs(List<String> newParagraphs);

  void displayPictures(ArrayList<Picture> pictures);
}
