package com.jetlag.jcreator.activity.creator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.jetlag.jcreator.R;
import com.jetlag.jcreator.pictures.Picture;
import com.jetlag.jcreator.pictures.PictureAdapter;

import java.util.List;

/**
 * Created by vince on 04/06/16.
 */
public class TextWriterFragment extends Fragment {
  /**
   * The fragment argument representing the section number for this
   * fragment.
   */
  private static final String ARG_SECTION_NUMBER = "section_number";
  private RecyclerView picturesView;
  private EditText storyEditor;
  private Button nextButton;

  public TextWriterFragment() {
  }

  /**
   * Returns a new instance of this fragment for the given section
   * number.
   */
  public static TextWriterFragment newInstance(int sectionNumber) {
    TextWriterFragment fragment = new TextWriterFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_SECTION_NUMBER, sectionNumber);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_creator_text, container, false);
    findViews(rootView);
    displayPictures();
//    storyEditor.requestFocus();
    nextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onClickNextButton();
      }
    });
    return rootView;
  }

  private void findViews(View rootView) {
    picturesView = (RecyclerView) rootView.findViewById(R.id.pictures);
    storyEditor = (EditText) rootView.findViewById(R.id.story);
    nextButton = (Button) rootView.findViewById(R.id.next);
  }

  private void displayPictures() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    List<Picture> selectedPictures = ((CreatorActivity) getActivity()).getViewModel().getSelectedPictures();
    picturesView.setAdapter(new PictureAdapter(getActivity(), R.layout.picture_thumbnail_cell, selectedPictures, false));
    picturesView.setLayoutManager(layoutManager);
  }

  private void displayStory() {
    storyEditor.setText(((CreatorActivity) getActivity()).getViewModel().getStoryText());
  }

  private void onClickNextButton() {
    View view = this.getActivity().getCurrentFocus();
    if (view != null) {
      InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    String text = storyEditor.getText().toString();
    ((CreatorActivity) getActivity()).writeTextAndGoToArticlePreview(text);
  }

  public void updateStory() {
    picturesView.getAdapter().notifyDataSetChanged();
    displayStory();
  }
}