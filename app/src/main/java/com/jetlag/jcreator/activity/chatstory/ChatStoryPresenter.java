package com.jetlag.jcreator.activity.chatstory;

import com.google.android.agera.MutableRepository;
import com.google.android.agera.Repositories;
import com.google.android.agera.Updatable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vince on 31/08/16.
 */
public class ChatStoryPresenter implements ChatStoryActions {

  private MutableRepository<String> nextParagraphTextRepo;
  private ParagraphsRepository paragraphsRepo;

  public void createRepos() {
    nextParagraphTextRepo = Repositories.mutableRepository("");
  }

  public void addParagraph(String newParagraph) {
    nextParagraphTextRepo.accept(newParagraph);
  }

  public void bindUpdatables(ChatStoryDisplay chatStoryDisplay) {
    paragraphsRepo = new ParagraphsRepository(chatStoryDisplay);
    nextParagraphTextRepo.addUpdatable(paragraphsRepo);
  }

  public void unbindUpdatables() {
    nextParagraphTextRepo.removeUpdatable(paragraphsRepo);
  }

  private class ParagraphsRepository implements Updatable {

    private List<String> paragraphs = new ArrayList<>();

    private final ChatStoryDisplay chatStoryDisplay;

    private ParagraphsRepository(ChatStoryDisplay chatStoryDisplay) {
      this.chatStoryDisplay = chatStoryDisplay;
    }

    @Override
    public void update() {
      paragraphs.add(nextParagraphTextRepo.get());
      chatStoryDisplay.displayParagraphs(paragraphs);
    }
  }
}
