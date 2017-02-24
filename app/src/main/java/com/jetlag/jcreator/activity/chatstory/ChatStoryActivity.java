package com.jetlag.jcreator.activity.chatstory;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jetlag.jcreator.R;
import com.jetlag.jcreator.flickr.FlickrPictureInfoReceiver;
import com.jetlag.jcreator.flickr.FlickrPictureInfoService;
import com.jetlag.jcreator.flickr.FlickrUploadService;
import com.jetlag.jcreator.flickr.FlickrUploadStateReceiver;
import com.jetlag.jcreator.paragraph.Paragraph;
import com.jetlag.jcreator.paragraph.ParagraphAdapter;
import com.jetlag.jcreator.permission.PermissionChecker;
import com.jetlag.jcreator.pictures.DevicePicture;
import com.jetlag.jcreator.pictures.PictureAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatStoryActivity extends AppCompatActivity implements ChatStoryDisplay {


  public static final int REQ_READ_PICS = 101;
  public static final String PERM_READ_PICS = Manifest.permission.READ_EXTERNAL_STORAGE;

  private Toolbar toolbar;
  private FloatingActionButton fab;
  private RecyclerView paragraphs;
  private RelativeLayout textInput;
  private EditText nextParagraph;
  private Button addParagraph;
  private RelativeLayout pictureInput;
  private RecyclerView nextPictures;
  private Button addPictures;
  private TextView textInputButton;
  private TextView pictureInputButton;

  private ChatStoryActions chatStoryPresenter;

  private List<BroadcastReceiver> registeredReceivers;

  /**
   * lifecycle
   */

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat_story);

    findAllViews();
    setSupportActionBar(toolbar);
    setupFab();
    bindActions();
    initParagraphs();
    initPictures();
    new PermissionChecker().checkPermission(this, PERM_READ_PICS, REQ_READ_PICS, "Allow to add pictures from your device?");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_chat_story, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_publish) {
      chatStoryPresenter.uploadPictures();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onStart() {
    super.onStart();
    chatStoryPresenter = new ChatStoryPresenter(this);
    chatStoryPresenter.createRepos();
    chatStoryPresenter.bindUpdatables(this);
    registerReceivers();
  }

  @Override
  protected void onStop() {
    super.onStop();
    chatStoryPresenter.unbindUpdatables();
    unregisterReceivers();
  }

  private FloatingActionButton findAllViews() {
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    fab = (FloatingActionButton) findViewById(R.id.fab);
    paragraphs = (RecyclerView) findViewById(R.id.paragraphs);
    textInput = (RelativeLayout) findViewById(R.id.next_text);
    nextParagraph = (EditText) findViewById(R.id.text_paragraph);
    addParagraph = (Button) findViewById(R.id.add_paragraph);
    pictureInput = (RelativeLayout) findViewById(R.id.next_pictures);
    nextPictures = (RecyclerView) findViewById(R.id.pictures_paragraph);
    addPictures = (Button) findViewById(R.id.add_pictures);
    textInputButton = (TextView) findViewById(R.id.text_input);
    pictureInputButton = (TextView) findViewById(R.id.picture_input);
    return fab;
  }

  private void setupFab() {
    if (fab != null) {
      fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  .setAction("Action", null).show();
        }
      });
    }
  }

  /**
   * init UI
   */

  private void initParagraphs() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    paragraphs.setAdapter(new ParagraphAdapter(this, new ArrayList<Paragraph>()));
    paragraphs.setLayoutManager(layoutManager);
  }

  private void initPictures() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    nextPictures.setAdapter(new PictureAdapter(this, R.layout.picture_thumbnail_cell, new ArrayList<DevicePicture>(), true));
    nextPictures.setLayoutManager(layoutManager);
  }

  private void registerReceivers() {
    registeredReceivers = new ArrayList<>();
    registerUploadStateReceiver();
    registerPictureInfoReceiver();
  }

  private void registerUploadStateReceiver() {
    IntentFilter uploadEndIntentFilter = new IntentFilter(
            FlickrUploadService.INTENT_FLICKR_UPLOAD_END);
    FlickrUploadStateReceiver flickrUploadStateReceiver = new FlickrUploadStateReceiver();
    registeredReceivers.add(flickrUploadStateReceiver);
    flickrUploadStateReceiver.registerListener(chatStoryPresenter);
    LocalBroadcastManager.getInstance(this).registerReceiver(flickrUploadStateReceiver, uploadEndIntentFilter);
  }

  private void registerPictureInfoReceiver() {
    IntentFilter pictureInfoIntentFilter = new IntentFilter(
            FlickrPictureInfoService.INTENT_FLICKR_PICTURE_INFO_END);
    FlickrPictureInfoReceiver flickrPictureInfoReceiver = new FlickrPictureInfoReceiver();
    registeredReceivers.add(flickrPictureInfoReceiver);
    flickrPictureInfoReceiver.registerListener(chatStoryPresenter);
    LocalBroadcastManager.getInstance(this).registerReceiver(flickrPictureInfoReceiver, pictureInfoIntentFilter);
  }

  private void unregisterReceivers() {
    for (BroadcastReceiver broadcastReceiver: registeredReceivers) {
      LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
  }

  /**
   * bind user actions
   */

  private void bindActions() {
    bindAddTextParagraph();
    bindAddPictureParagraph();
    bindTextInput();
    bindPictureInput();
  }

  private void bindAddTextParagraph() {
    addParagraph.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        chatStoryPresenter.addTextParagraph(nextParagraph.getText().toString());
        nextParagraph.setText("");
      }
    });
  }

  private void bindAddPictureParagraph() {
    addPictures.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        PictureAdapter nextPicturesAdapter = (PictureAdapter) nextPictures.getAdapter();
        ArrayList<DevicePicture> pickedPictures = nextPicturesAdapter.getPickedPictures();
        chatStoryPresenter.addPictureParagraph(pickedPictures);
        nextPicturesAdapter.resetPickedPictures();
        nextPicturesAdapter.notifyDataSetChanged();
      }
    });
  }

  private void bindTextInput() {
    textInputButton.setClickable(true);
    textInputButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        pictureInput.setVisibility(View.GONE);
        textInput.setVisibility(View.VISIBLE);
      }
    });
  }

  private void bindPictureInput() {
    pictureInputButton.setClickable(true);
    pictureInputButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        textInput.setVisibility(View.GONE);
        pictureInput.setVisibility(View.VISIBLE);
        if (new PermissionChecker().checkPermission(ChatStoryActivity.this, PERM_READ_PICS, REQ_READ_PICS, "")) {
          chatStoryPresenter.getLatestPicturesOnDevice();
        }
      }
    });
  }

  /**
   * display data
   */

  public void displayParagraphs(List<Paragraph> newParagraphs) {
    ((ParagraphAdapter) paragraphs.getAdapter()).setParagraphs(newParagraphs);
    paragraphs.getAdapter().notifyDataSetChanged();
    if (!newParagraphs.isEmpty()) {
      paragraphs.smoothScrollToPosition(newParagraphs.size() - 1);
    }
  }

  @Override
  public void displayPictures(ArrayList<DevicePicture> pictures) {
    ((PictureAdapter) nextPictures.getAdapter()).setDevicePictures(pictures);
    nextPictures.getAdapter().notifyDataSetChanged();
  }

  /**
   * callbacks
   */

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
      case REQ_READ_PICS: {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          chatStoryPresenter.getLatestPicturesOnDevice();
        }
      }
    }
  }
}
