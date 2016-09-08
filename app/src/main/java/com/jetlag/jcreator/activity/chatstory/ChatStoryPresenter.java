package com.jetlag.jcreator.activity.chatstory;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.agera.BaseObservable;
import com.google.android.agera.Merger;
import com.google.android.agera.MutableRepository;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Updatable;
import com.jetlag.jcreator.pictures.GalleryPicturesSupplier;
import com.jetlag.jcreator.pictures.Picture;

import java.util.ArrayList;

/**
 * Created by vince on 31/08/16.
 */
public class ChatStoryPresenter implements ChatStoryActions {

  private MutableRepository<String> nextParagraphTextRepo;
  private ParagraphsUpdatable paragraphsUpdatable;
  private MutableRepository<ArrayList<String>> paragraphsRepo;
  private Repository<ArrayList<String>> paragraphsBuilder;
  private final Context context;
  private Repository<ArrayList<Picture>> galleryPicturesRepo;
  private GalleryPicturesGetterObservable galleryPicturesGetterObservable;
  private PicturesInputUpdatable picturesInputUpdatable;

  public ChatStoryPresenter(Context context) {
    this.context = context;
  }

  public void createRepos() {
    nextParagraphTextRepo = Repositories.mutableRepository("");
    galleryPicturesGetterObservable = new GalleryPicturesGetterObservable();
    galleryPicturesRepo = Repositories.repositoryWithInitialValue(new ArrayList<Picture>())
        .observe(galleryPicturesGetterObservable)
        .onUpdatesPerLoop()
        //.goTo(Executors.newSingleThreadExecutor())
        .thenGetFrom(new GalleryPicturesSupplier(context))
        .compile();
    paragraphsRepo = Repositories.mutableRepository(new ArrayList<String>());
    paragraphsBuilder = Repositories.repositoryWithInitialValue(new ArrayList<String>())
        .observe(nextParagraphTextRepo)
        .onUpdatesPerLoop()
        .getFrom(nextParagraphTextRepo)
        .thenMergeIn(paragraphsRepo, new Merger<String, ArrayList<String>, ArrayList<String>>() {
          @NonNull
          @Override
          public ArrayList<String> merge(@NonNull String nextParagraph, @NonNull ArrayList<String> paragraphs) {
            paragraphs.add(nextParagraph);
            return paragraphs;
          }
        })
        .compile();
  }

  public void addParagraph(String newParagraph) {
    nextParagraphTextRepo.accept(newParagraph);
  }

  public void bindUpdatables(ChatStoryDisplay chatStoryDisplay) {
    paragraphsUpdatable = new ParagraphsUpdatable(chatStoryDisplay);
    paragraphsBuilder.addUpdatable(paragraphsUpdatable);

    picturesInputUpdatable = new PicturesInputUpdatable(chatStoryDisplay);
    galleryPicturesRepo.addUpdatable(picturesInputUpdatable);
  }

  public void unbindUpdatables() {
    paragraphsBuilder.removeUpdatable(paragraphsUpdatable);
  }

  @Override
  public void getLatestPicturesOnDevice() {
    galleryPicturesGetterObservable.retrievePics();
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

  private class PicturesInputUpdatable implements Updatable {

    private final ChatStoryDisplay chatStoryDisplay;

    private PicturesInputUpdatable(ChatStoryDisplay chatStoryDisplay) {
      this.chatStoryDisplay = chatStoryDisplay;
    }

    @Override
    public void update() {
      chatStoryDisplay.displayPictures(galleryPicturesRepo.get());
    }
  }

  private class GalleryPicturesGetterObservable extends BaseObservable {
    public void retrievePics() {
      dispatchUpdate();
    }
  }
}
