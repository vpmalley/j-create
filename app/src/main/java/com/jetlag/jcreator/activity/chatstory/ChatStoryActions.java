package com.jetlag.jcreator.activity.chatstory;

/**
 * Created by vince on 31/08/16.
 */
public interface ChatStoryActions {

  void createRepos();

  void addParagraph(String newParagraph);

  void bindUpdatables(ChatStoryDisplay chatStoryDisplay);

  void unbindUpdatables();
}
