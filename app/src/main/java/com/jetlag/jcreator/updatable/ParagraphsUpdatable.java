package com.jetlag.jcreator.updatable;

import com.google.android.agera.Repository;
import com.google.android.agera.Updatable;
import com.jetlag.jcreator.activity.chatstory.ChatStoryDisplay;

import java.util.ArrayList;

/**
 * Created by vince on 09/09/16.
 */
public class ParagraphsUpdatable implements Updatable {

  private final ChatStoryDisplay chatStoryDisplay;
  private final Repository<ArrayList<String>> paragraphsRepo;

  public ParagraphsUpdatable(ChatStoryDisplay chatStoryDisplay, Repository<ArrayList<String>> paragraphsRepo) {
    this.chatStoryDisplay = chatStoryDisplay;
    this.paragraphsRepo = paragraphsRepo;
  }

  @Override
  public void update() {
    chatStoryDisplay.displayParagraphs(paragraphsRepo.get());
  }
}
