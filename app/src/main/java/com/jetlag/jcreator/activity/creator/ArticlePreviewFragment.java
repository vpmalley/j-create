package com.jetlag.jcreator.activity.creator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jetlag.jcreator.R;
import com.jetlag.jcreator.stories.Story;
import com.jetlag.jcreator.stories.StoryAdapter;

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
  private RecyclerView storiesView;
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
    displayStories();
    nextStoryButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onClickNextStoryButton();
      }
    });
    return rootView;
  }

  private void findViews(View rootView) {
    storiesView = (RecyclerView) rootView.findViewById(R.id.stories);
    nextStoryButton = (Button) rootView.findViewById(R.id.nextStory);
  }

  private void displayStories() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    List<Story> stories = ((CreatorActivity) getActivity()).getViewModel().getStories();
    storiesView.setAdapter(new StoryAdapter(getActivity(), R.layout.story_preview_cell, stories));
    storiesView.setLayoutManager(layoutManager);
  }

  private void onClickNextStoryButton() {
    ((CreatorActivity) getActivity()).addStoryAndStartNewStory();
  }

  public void updateStories() {
    storiesView.getAdapter().notifyDataSetChanged();
  }
}