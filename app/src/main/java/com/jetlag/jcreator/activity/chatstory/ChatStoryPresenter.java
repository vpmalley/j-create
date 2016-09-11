package com.jetlag.jcreator.activity.chatstory;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.agera.Function;
import com.google.android.agera.MutableRepository;
import com.google.android.agera.Predicate;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.jetlag.jcreator.merger.ParagraphListMerger;
import com.jetlag.jcreator.observable.GalleryPicturesGetterObservable;
import com.jetlag.jcreator.paragraph.Paragraph;
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

  private MutableRepository<TextParagraph> nextTextParagraphRepo;
  private ParagraphsUpdatable paragraphsUpdatable;
  private Repository<ArrayList<Paragraph>> paragraphsRepo;
  private final Context context;
  private Repository<ArrayList<Picture>> galleryPicturesRepo;
  private GalleryPicturesGetterObservable galleryPicturesGetterObservable;
  private PicturesInputUpdatable picturesInputUpdatable;
  private MutableRepository<ArrayList<Paragraph>> innerParagraphsRepo;
  private MutableRepository<ArrayList<Picture>> nextPictureParagraphRepo;

  public ChatStoryPresenter(Context context) {
    this.context = context;
  }

  public void createRepos() {
    nextTextParagraphRepo = Repositories.mutableRepository(new TextParagraph(""));
    createGalleryPicturesRepo();
    createParagraphsRepo();
    nextPictureParagraphRepo = Repositories.mutableRepository(new ArrayList<Picture>());
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
    innerParagraphsRepo = Repositories.mutableRepository(new ArrayList<Paragraph>());
    paragraphsRepo = Repositories.repositoryWithInitialValue(new ArrayList<Paragraph>())
        .observe(nextTextParagraphRepo)
        .onUpdatesPerLoop()
        .getFrom(nextTextParagraphRepo)
        .check(new Predicate<TextParagraph>() {
          @Override
          public boolean apply(@NonNull TextParagraph value) {
            return !value.getText().isEmpty();
          }
        })
        .orSkip()
        .transform(new Function<TextParagraph, Paragraph>() {
          @NonNull
          @Override
          public Paragraph apply(@NonNull TextParagraph input) {
            return input;
          }
        })
        .thenMergeIn(innerParagraphsRepo, new ParagraphListMerger())
        .compile();
  }

  public void addParagraph(String newParagraph) {
    nextTextParagraphRepo.accept(new TextParagraph(newParagraph));
  }

  public void addPictures(ArrayList<Picture> newPictures) {
    nextPictureParagraphRepo.accept(newPictures);
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
