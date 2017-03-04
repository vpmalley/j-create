package com.jetlag.jcreator.activity.uploading;

import com.jetlag.jcreator.pictures.DevicePicture;

import java.util.ArrayList;

/**
 * Created by vince on 26/02/17.
 */

interface UploadingDisplay {

    void displayPictures(ArrayList<DevicePicture> pictures);

    void displayLoadingProgress(int percentage);
}
