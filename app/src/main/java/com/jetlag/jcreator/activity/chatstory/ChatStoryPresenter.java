package com.jetlag.jcreator.activity.chatstory;

import android.content.Context;

import com.google.android.agera.MutableRepository;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.jetlag.jcreator.agera.cast.PictureParagraphCaster;
import com.jetlag.jcreator.agera.cast.TextParagraphCaster;
import com.jetlag.jcreator.agera.merger.ParagraphListMerger;
import com.jetlag.jcreator.agera.observable.GalleryPicturesGetterObservable;
import com.jetlag.jcreator.agera.predicate.NonEmptyPictureParagraphPredicate;
import com.jetlag.jcreator.agera.predicate.NonEmptyTextParagraphPredicate;
import com.jetlag.jcreator.agera.updatable.ParagraphsUpdatable;
import com.jetlag.jcreator.agera.updatable.PicturesInputUpdatable;
import com.jetlag.jcreator.paragraph.Paragraph;
import com.jetlag.jcreator.paragraph.picture.DevicePictureParagraph;
import com.jetlag.jcreator.paragraph.text.TextParagraph;
import com.jetlag.jcreator.pictures.DevicePicture;
import com.jetlag.jcreator.pictures.GalleryPicturesSupplier;

import java.util.ArrayList;

/**
 * Created by vince on 31/08/16.
 */
public class ChatStoryPresenter implements ChatStoryActions {

  private MutableRepository<TextParagraph> nextTextParagraphRepo;
  private ParagraphsUpdatable paragraphsUpdatable;
  private Repository<ArrayList<Paragraph>> textParagraphsRepo;
  private final Context context;
  private Repository<ArrayList<DevicePicture>> galleryPicturesRepo;
  private GalleryPicturesGetterObservable galleryPicturesGetterObservable;
  private PicturesInputUpdatable picturesInputUpdatable;
  private MutableRepository<ArrayList<Paragraph>> paragraphsRepo;
  private MutableRepository<DevicePictureParagraph> nextPictureParagraphRepo;
  private Repository<ArrayList<Paragraph>> picturesParagraphsRepo;

  public ChatStoryPresenter(Context context) {
    this.context = context;
  }

  public void createRepos() {
    nextTextParagraphRepo = Repositories.mutableRepository(new TextParagraph(""));
    nextPictureParagraphRepo = Repositories.mutableRepository(new DevicePictureParagraph(new ArrayList<DevicePicture>()));
    createNextGalleryPicturesParagraphRepo();
    createTextParagraphsRepo();
    createPicturesParagraphsRepo();
  }

  private void createNextGalleryPicturesParagraphRepo() {
    galleryPicturesGetterObservable = new GalleryPicturesGetterObservable();
    galleryPicturesRepo = Repositories.repositoryWithInitialValue(new ArrayList<DevicePicture>())
        .observe(galleryPicturesGetterObservable)
        .onUpdatesPerLoop()
        .thenGetFrom(new GalleryPicturesSupplier(context))
        .compile();
  }

  private void createTextParagraphsRepo() {
    paragraphsRepo = Repositories.mutableRepository(new ArrayList<Paragraph>());
    textParagraphsRepo = Repositories.repositoryWithInitialValue(new ArrayList<Paragraph>())
        .observe(nextTextParagraphRepo)
        .onUpdatesPerLoop()
        .getFrom(nextTextParagraphRepo)
        .check(new NonEmptyTextParagraphPredicate())
        .orSkip()
        .transform(new TextParagraphCaster())
        .thenMergeIn(paragraphsRepo, new ParagraphListMerger())
        .compile();
  }

  private void createPicturesParagraphsRepo() {
    picturesParagraphsRepo = Repositories.repositoryWithInitialValue(new ArrayList<Paragraph>())
        .observe(nextPictureParagraphRepo)
        .onUpdatesPerLoop()
        .getFrom(nextPictureParagraphRepo)
        .check(new NonEmptyPictureParagraphPredicate())
        .orSkip()
        .transform(new PictureParagraphCaster())
        .thenMergeIn(paragraphsRepo, new ParagraphListMerger())
        .compile();
  }

  public void addTextParagraph(String newParagraph) {
    nextTextParagraphRepo.accept(new TextParagraph(newParagraph));
  }

  public void addPictureParagraph(ArrayList<DevicePicture> newPictures) {
    nextPictureParagraphRepo.accept(new DevicePictureParagraph(newPictures));
  }

  public void bindUpdatables(ChatStoryDisplay chatStoryDisplay) {
    paragraphsUpdatable = new ParagraphsUpdatable(chatStoryDisplay, paragraphsRepo);
    textParagraphsRepo.addUpdatable(paragraphsUpdatable);
    picturesParagraphsRepo.addUpdatable(paragraphsUpdatable);

    picturesInputUpdatable = new PicturesInputUpdatable(chatStoryDisplay, galleryPicturesRepo);
    galleryPicturesRepo.addUpdatable(picturesInputUpdatable);
  }

  public void unbindUpdatables() {
    textParagraphsRepo.removeUpdatable(paragraphsUpdatable);
    picturesParagraphsRepo.removeUpdatable(paragraphsUpdatable);
    galleryPicturesRepo.removeUpdatable(picturesInputUpdatable);
  }

  @Override
  public void getLatestPicturesOnDevice() {
    galleryPicturesGetterObservable.retrievePics();
  }

}
