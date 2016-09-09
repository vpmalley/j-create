package com.jetlag.jcreator.activity.chatstory;

import android.content.Context;

import com.google.android.agera.MutableRepository;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.jetlag.jcreator.merger.StringListMerger;
import com.jetlag.jcreator.observable.GalleryPicturesGetterObservable;
import com.jetlag.jcreator.paragraph.TextParagraph;
import com.jetlag.jcreator.pictures.GalleryPicturesSupplier;
import com.jetlag.jcreator.pictures.Picture;
import com.jetlag.jcreator.updatable.ParagraphsUpdatable;
import com.jetlag.jcreator.updatable.PicturesInputUpdatable;

import java.util.ArrayList;

/**
 * Created by vince on 31/08/16.
 */
public class ChatStoryPresenter implements ChatStoryActions {

  private MutableRepository<String> nextTextParagraphRepo;
  private ParagraphsUpdatable paragraphsUpdatable;
  private Repository<ArrayList<TextParagraph>> paragraphsRepo;
  private final Context context;
  private Repository<ArrayList<Picture>> galleryPicturesRepo;
  private GalleryPicturesGetterObservable galleryPicturesGetterObservable;
  private PicturesInputUpdatable picturesInputUpdatable;
  private MutableRepository<ArrayList<TextParagraph>> innerParagraphsRepo;

  public ChatStoryPresenter(Context context) {
    this.context = context;
  }

  public void createRepos() {
    nextTextParagraphRepo = Repositories.mutableRepository("");
    createGalleryPicturesRepo();
    createParagraphsRepo();
  }

  private void createGalleryPicturesRepo() {
    galleryPicturesGetterObservable = new GalleryPicturesGetterObservable();
    galleryPicturesRepo = Repositories.repositoryWithInitialValue(new ArrayList<Picture>())
        .observe(galleryPicturesGetterObservable)
        .onUpdatesPerLoop()
        //.goTo(Executors.newSingleThreadExecutor())
        .thenGetFrom(new GalleryPicturesSupplier(context))
        .compile();
  }

  private void createParagraphsRepo() {
    innerParagraphsRepo = Repositories.mutableRepository(new ArrayList<TextParagraph>());
    paragraphsRepo = Repositories.repositoryWithInitialValue(new ArrayList<TextParagraph>())
        .observe(nextTextParagraphRepo)
        .onUpdatesPerLoop()
        .getFrom(nextTextParagraphRepo)
        .thenMergeIn(innerParagraphsRepo, new StringListMerger())
        .compile();
  }

  public void addParagraph(String newParagraph) {
    nextTextParagraphRepo.accept(newParagraph);
  }

  public void bindUpdatables(ChatStoryDisplay chatStoryDisplay) {
    paragraphsUpdatable = new ParagraphsUpdatable(chatStoryDisplay, paragraphsRepo);
    paragraphsRepo.addUpdatable(paragraphsUpdatable);

    picturesInputUpdatable = new PicturesInputUpdatable(chatStoryDisplay, galleryPicturesRepo);
    galleryPicturesRepo.addUpdatable(picturesInputUpdatable);
  }

  public void unbindUpdatables() {
    paragraphsRepo.removeUpdatable(paragraphsUpdatable);
    galleryPicturesRepo.removeUpdatable(picturesInputUpdatable);
  }

  @Override
  public void getLatestPicturesOnDevice() {
    galleryPicturesGetterObservable.retrievePics();
  }

}
