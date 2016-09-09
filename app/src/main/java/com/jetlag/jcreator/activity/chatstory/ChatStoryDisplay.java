package com.jetlag.jcreator.activity.chatstory;

import com.jetlag.jcreator.paragraph.TextParagraph;
import com.jetlag.jcreator.pictures.Picture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vince on 31/08/16.
 */
public interface ChatStoryDisplay {

  void displayParagraphs(List<TextParagraph> newParagraphs);

  void displayPictures(ArrayList<Picture> pictures);
}
