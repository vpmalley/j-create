package com.jetlag.jcreator.activity.chatstory;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.agera.MutableRepository;
import com.google.android.agera.Repositories;
import com.google.android.agera.Updatable;
import com.jetlag.jcreator.R;
import com.jetlag.jcreator.paragraph.ParagraphAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatStoryActivity extends AppCompatActivity {

  private FloatingActionButton fab;
  private RecyclerView paragraphs;
  private EditText nextParagraph;
  private Button addParagraph;
  private TextView textInput;
  private TextView pictureInput;

  private MutableRepository<String> nextParagraphText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat_story);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    findAllViews();
    setupFab(fab);
    createRepos();
    initParagraphs();
    bindActions();

    Updatable paragraphsRepo = new Updatable() {

      private List<String> paragraphs = new ArrayList<>();

      @Override
      public void update() {
        paragraphs.add(nextParagraphText.get());
        displayParagraphs(paragraphs);
      }
    };
    nextParagraphText.addUpdatable(paragraphsRepo);
  }

  private void bindActions() {
    addParagraph.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        nextParagraphText.accept(nextParagraph.getText().toString());
        nextParagraph.setText("");
      }
    });
  }

  private void createRepos() {
    nextParagraphText = Repositories.mutableRepository("");
  }

  private void initParagraphs() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    paragraphs.setAdapter(new ParagraphAdapter(this, R.layout.paragraph_cell, Collections.singletonList("Hello world")));
    paragraphs.setLayoutManager(layoutManager);
  }

  private void displayParagraphs(List<String> newParagraphs) {
    ((ParagraphAdapter) paragraphs.getAdapter()).setParagraphs(newParagraphs);
    paragraphs.getAdapter().notifyDataSetChanged();
  }

  private FloatingActionButton findAllViews() {
    fab = (FloatingActionButton) findViewById(R.id.fab);
    paragraphs = (RecyclerView) findViewById(R.id.paragraphs);
    nextParagraph = (EditText) findViewById(R.id.text_paragraph);
    addParagraph = (Button) findViewById(R.id.add_paragraph);
    textInput = (TextView) findViewById(R.id.text_input);
    pictureInput = (TextView) findViewById(R.id.picture_input);

    return fab;
  }

  private void setupFab(FloatingActionButton fab) {
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
}
