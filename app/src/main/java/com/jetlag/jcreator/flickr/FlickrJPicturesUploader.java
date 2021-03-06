package com.jetlag.jcreator.flickr;

import android.content.Context;
import android.util.Log;

import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.REST;
import com.googlecode.flickrjandroid.RequestContext;
import com.googlecode.flickrjandroid.oauth.OAuth;
import com.googlecode.flickrjandroid.oauth.OAuthToken;
import com.googlecode.flickrjandroid.photos.Photo;
import com.googlecode.flickrjandroid.photos.upload.Ticket;
import com.googlecode.flickrjandroid.photos.upload.UploadInterface;
import com.googlecode.flickrjandroid.uploader.UploadMetaData;
import com.googlecode.flickrjandroid.uploader.Uploader;
import com.jetlag.jcreator.R;
import com.jetlag.jcreator.pictures.DevicePicture;
import com.jetlag.jcreator.pictures.UploadedPicture;

import org.json.JSONException;
import org.scribe.model.Token;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by vince on 17/04/15.
 */
public class FlickrJPicturesUploader implements FlickrPicturesUploader {

  private static final String API_FLICKR_SERVICES = "api.flickr.com/services";
  private final Context context;

  private final Token accessToken;

  private final List<String> userNotifs = new ArrayList<>();
  private FlickrJPicturesProvider picturesProvider;

  public FlickrJPicturesUploader(Context context, Token accessToken) {
    this.context = context;
    this.accessToken = accessToken;
    this.picturesProvider = new FlickrJPicturesProvider(context);
  }

  @Override
  public List<String> uploadPicture(DevicePicture picture) {
    List<DevicePicture> pictures = new ArrayList<>(1);
    pictures.add(picture);
    return requestUpload(pictures);
  }

  private List<String> requestUpload(List<DevicePicture> pictures) {
    RequestContext requestContext = RequestContext.getRequestContext();
    OAuth oauth = new OAuth();
    oauth.setToken(new OAuthToken(accessToken.getToken(), accessToken.getSecret()));

    requestContext.setOAuth(oauth);
    Uploader uploader = new Uploader(context.getString(R.string.flickr_api_key), context.getString(R.string.flickr_api_secret));
    List<String> photoIds = new ArrayList<>();
    for (DevicePicture pic : pictures) {
      InputStream picStream = null;
      try {
        picStream = context.getContentResolver().openInputStream(pic.getUri());
      } catch (FileNotFoundException e) {
        Log.w("inputStream", "failed opening the stream for picture to upload. " + e.toString());
      }

      UploadMetaData uploadMetaData = new UploadMetaData();
      uploadMetaData.setAsync(false);
      uploadMetaData.setTitle("untitled");
      uploadMetaData.setDescription(pic.getDescription());
      if (picStream != null) {
        try {
          String photoId = uploader.upload("untitled", picStream, uploadMetaData);
          photoIds.add(photoId);
          Log.d("upload successful", "with photo id :" + photoIds.get(photoIds.size() - 1));
        } catch (IOException | FlickrException | SAXException e) {
          Log.w("inputStream", "failed uploading picture. " + e.toString());
        }
      }
    }
    return photoIds;
  }

  private List<UploadedPicture> requestPictureInformation(List<String> ticketIds) {
    List<UploadedPicture> pictures = new ArrayList<>();
    RequestContext requestContext = RequestContext.getRequestContext();
    OAuth oauth = new OAuth();
    oauth.setToken(new OAuthToken(accessToken.getToken(), accessToken.getSecret()));
    requestContext.setOAuth(oauth);

    try {
      UploadInterface ui = new UploadInterface(context.getString(R.string.flickr_api_key), context.getString(R.string.flickr_api_secret), new REST(API_FLICKR_SERVICES));
      List<Ticket> tickets = ui.checkTickets(new HashSet<Object>(ticketIds));
      for (Ticket t : tickets) {
        if (Ticket.COMPLETED != t.getStatus()) {
          Photo p = picturesProvider.getPhotoForId(t.getPhotoId());
          pictures.add(new UploadedPicture(p));
          Log.d("upload", "uploaded a picture with title " + p.getTitle());
          userNotifs.add("uploaded a picture with title " + p.getTitle());
        } else if (Ticket.FAILED == t.getStatus()) {
          Log.d("upload", "a picture upload has failed");
          userNotifs.add("a picture upload has failed");
        } else {
          Log.d("upload", "picture still uploading");
          userNotifs.add("picture still uploading");
        }
      }
    } catch (ParserConfigurationException | JSONException | IOException | FlickrException e) {
      Log.w("uploader", "failed to retrieve ticket");
    }
    return pictures;
  }

}
