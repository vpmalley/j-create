package com.jetlag.jcreator.activity.creator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jetlag.jcreator.R;
import com.jetlag.jcreator.pictures.Picture;

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
    storyEditor.requestFocus();
    return rootView;
  }

  private void findViews(View rootView) {
    picturesView = (RecyclerView) rootView.findViewById(R.id.pictures);
    storyEditor = (EditText) rootView.findViewById(R.id.story);
    nextButton = (Button) rootView.findViewById(R.id.next);
  }

  private void displayPictures() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    List<Picture> selectedPictures = ((CreatorActivity) getActivity()).getPresenter().getSelectedPictures();
    picturesView.setAdapter(new PictureAdapter(getActivity(), R.layout.picture_thumbnail_cell, selectedPictures));
    picturesView.setLayoutManager(layoutManager);
  }

  public void updatePickedPictures() {
    picturesView.getAdapter().notifyDataSetChanged();
  }
}