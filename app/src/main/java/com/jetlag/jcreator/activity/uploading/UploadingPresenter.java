package com.jetlag.jcreator.activity.uploading;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.jetlag.jcreator.flickr.FlickrPictureInfoService;
import com.jetlag.jcreator.paragraph.Paragraph;
import com.jetlag.jcreator.pictures.DevicePicture;
import com.jetlag.jcreator.pictures.UploadedPicture;

import java.util.ArrayList;

/**
 * Created by vince on 26/02/17.
 */

public class UploadingPresenter implements UploadingActions {

    private final Context context;
    private final ArrayList<Paragraph> uploadingParagraphs;
    private UploadingDisplay uploadingDisplay;

    private ArrayList<DevicePicture> uploadingPictures;
    private ArrayList<UploadedPicture> uploadedPictures = new ArrayList<>();

    public UploadingPresenter(Context context, ArrayList<Paragraph> uploadingParagraphs) {
        this.context = context;
        this.uploadingParagraphs = uploadingParagraphs;
    }

    @Override
    public void bindUpdatables(UploadingDisplay uploadingDisplay) {
        this.uploadingDisplay = uploadingDisplay;
    }

    @Override
    public void getUploadingPictures() {
        uploadingDisplay.displayPictures(extractUploadingPictures());
    }

    @Override
    public void cancelPendingUploads() {
        // TODO
    }

    @Override
    public void onUploadEnd(String photoId) {
        Toast.makeText(context, "picture uploaded: " + photoId, Toast.LENGTH_SHORT).show();
        Log.d("upload", "picture uploaded: " + photoId);
        Intent pictureInfoIntent = new Intent(context, FlickrPictureInfoService.class);
        pictureInfoIntent.putExtra(FlickrPictureInfoService.EXTRA_PHOTO_ID, photoId);
        context.startService(pictureInfoIntent);
    }

    @Override
    public void onPictureInfoReceived(UploadedPicture uploadedPicture) {
        Toast.makeText(context, "picture uploaded: " + uploadedPicture.getUrl(), Toast.LENGTH_SHORT).show();
        Log.d("upload", "picture uploaded: " + uploadedPicture.getUrl());
        uploadedPictures.add(uploadedPicture);
        updateProgress();
    }

    private ArrayList<DevicePicture> extractUploadingPictures() {
        for (Paragraph p : uploadingParagraphs) {

        }
        return null;
    }

    private void updateProgress() {
        uploadingDisplay.displayLoadingProgress(0);
    }

}
