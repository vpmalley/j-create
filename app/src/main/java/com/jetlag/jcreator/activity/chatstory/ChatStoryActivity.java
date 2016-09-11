package com.jetlag.jcreator.activity.chatstory;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jetlag.jcreator.R;
import com.jetlag.jcreator.paragraph.Paragraph;
import com.jetlag.jcreator.paragraph.ParagraphAdapter;
import com.jetlag.jcreator.permission.PermissionChecker;
import com.jetlag.jcreator.pictures.Picture;
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

  /**
   * lifecycle
   */

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat_story);
    setSupportActionBar(toolbar);

    findAllViews();
    setupFab();
    bindActions();
    initParagraphs();
    initPictures();
    new PermissionChecker().checkPermission(this, PERM_READ_PICS, REQ_READ_PICS, "Allow to add pictures from your device?");
  }

  @Override
  protected void onStart() {
    super.onStart();
    chatStoryPresenter = new ChatStoryPresenter(this);
    chatStoryPresenter.createRepos();
    chatStoryPresenter.bindUpdatables(this);
  }

  @Override
  protected void onStop() {
    super.onStop();
    chatStoryPresenter.unbindUpdatables();
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
    nextPictures.setAdapter(new PictureAdapter(this, R.layout.picture_thumbnail_cell, new ArrayList<Picture>(), true));
    nextPictures.setLayoutManager(layoutManager);
  }

  /**
   * bind user actions
   */

  private void bindActions() {
    bindAddParagraph();
    bindTextInput();
    bindPictureInput();
  }

  private void bindAddParagraph() {
    addParagraph.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        chatStoryPresenter.addParagraph(nextParagraph.getText().toString());
        nextParagraph.setText("");
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
    paragraphs.smoothScrollToPosition(newParagraphs.size() - 1);
  }

  @Override
  public void displayPictures(ArrayList<Picture> pictures) {
    ((PictureAdapter) nextPictures.getAdapter()).setPictures(pictures);
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
