package com.jetlag.jcreator.activity.chatstory;

import android.support.annotation.NonNull;

import com.google.android.agera.Merger;
import com.google.android.agera.MutableRepository;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Updatable;

import java.util.ArrayList;

/**
 * Created by vince on 31/08/16.
 */
public class ChatStoryPresenter implements ChatStoryActions {

  private MutableRepository<String> nextParagraphTextRepo;
  private ParagraphsUpdatable paragraphsUpdatable;
  private MutableRepository<ArrayList<String>> paragraphsRepo;
  private Repository<ArrayList<String>> paragraphsBuilder;

  public void createRepos() {
    nextParagraphTextRepo = Repositories.mutableRepository("");
    paragraphsRepo = Repositories.mutableRepository(new ArrayList<String>());
    paragraphsBuilder = Repositories.repositoryWithInitialValue(new ArrayList<String>())
        .observe(nextParagraphTextRepo)
        .onUpdatesPerLoop()
        .getFrom(nextParagraphTextRepo)
        .mergeIn(paragraphsRepo, new Merger<String, ArrayList<String>, ArrayList<String>>() {
          @NonNull
          @Override
          public ArrayList<String> merge(@NonNull String nextParagraph, @NonNull ArrayList<String> paragraphs) {
            paragraphs.add(nextParagraph);
            return paragraphs;
          }
        })
        .thenGetFrom(paragraphsRepo)
        .compile();
  }

  public void addParagraph(String newParagraph) {
    nextParagraphTextRepo.accept(newParagraph);
  }

  public void bindUpdatables(ChatStoryDisplay chatStoryDisplay) {
    paragraphsUpdatable = new ParagraphsUpdatable(chatStoryDisplay);
    paragraphsBuilder.addUpdatable(paragraphsUpdatable);
  }

  public void unbindUpdatables() {
    paragraphsBuilder.removeUpdatable(paragraphsUpdatable);
  }

  @Override
  public void getLatestPicturesOnDevice() {
    // TODO get them
  }

  private class ParagraphsUpdatable implements Updatable {

    private final ChatStoryDisplay chatStoryDisplay;

    private ParagraphsUpdatable(ChatStoryDisplay chatStoryDisplay) {
      this.chatStoryDisplay = chatStoryDisplay;
    }

    @Override
    public void update() {
      chatStoryDisplay.displayParagraphs(paragraphsBuilder.get());
    }
  }
}
