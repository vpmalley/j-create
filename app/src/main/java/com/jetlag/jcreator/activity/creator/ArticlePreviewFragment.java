package com.jetlag.jcreator.activity.creator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jetlag.jcreator.R;
import com.jetlag.jcreator.pictures.Picture;

import java.util.List;

/**
 * Created by vince on 04/06/16.
 */
public class ArticlePreviewFragment extends Fragment {
  /**
   * The fragment argument representing the section number for this
   * fragment.
   */
  private static final String ARG_SECTION_NUMBER = "section_number";
  private RecyclerView picturesView;
  private TextView storyView;
  private Button nextStoryButton;

  public ArticlePreviewFragment() {
  }

  /**
   * Returns a new instance of this fragment for the given section
   * number.
   */
  public static ArticlePreviewFragment newInstance(int sectionNumber) {
    ArticlePreviewFragment fragment = new ArticlePreviewFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_SECTION_NUMBER, sectionNumber);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_creator_preview, container, false);
    findViews(rootView);
    displayPictures();
    nextStoryButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onClickNextStoryButton();
      }
    });
    return rootView;
  }

  private void findViews(View rootView) {
    picturesView = (RecyclerView) rootView.findViewById(R.id.pictures);
    storyView = (TextView) rootView.findViewById(R.id.story);
    nextStoryButton = (Button) rootView.findViewById(R.id.nextStory);
  }

  private void displayPictures() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    List<Picture> selectedPictures = ((CreatorActivity) getActivity()).getViewModel().getSelectedPictures();
    picturesView.setAdapter(new PictureAdapter(getActivity(), R.layout.picture_thumbnail_cell, selectedPictures));
    picturesView.setLayoutManager(layoutManager);
  }

  private void displayStory() {
    storyView.setText(((CreatorActivity) getActivity()).getViewModel().getStoryText());
  }

  private void onClickNextStoryButton() {
    ((CreatorActivity) getActivity()).addStoryAndStartNewStory();
  }

  public void updateStory() {
    picturesView.getAdapter().notifyDataSetChanged();
    displayStory();
  }
}