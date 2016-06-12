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
import com.jetlag.jcreator.pictures.Picture;
import com.jetlag.jcreator.pictures.PictureFromGalleryLoader;
import com.jetlag.jcreator.pictures.PictureLoadedListener;

import java.util.List;

public class PicturePickerFragment extends Fragment {
  /**
   * The fragment argument representing the section number for this
   * fragment.
   */
  private static final String ARG_SECTION_NUMBER = "section_number";
  private RecyclerView picturesView;
  private Button nextButton;
  private PictureFromGalleryLoader picLoader;

  public PicturePickerFragment() {
  }

  /**
   * Returns a new instance of this fragment for the given section
   * number.
   */
  public static PicturePickerFragment newInstance(int sectionNumber) {
    PicturePickerFragment fragment = new PicturePickerFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_SECTION_NUMBER, sectionNumber);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_creator_picture, container, false);
    findViews(rootView);
    displayPictures();
    picLoader = new PictureFromGalleryLoader(getContext(), (PictureLoadedListener) getActivity());
    if (((CreatorActivity) getActivity()).checkPermission(CreatorActivity.PERM_READ_PICS, CreatorActivity.REQ_READ_PICS)) {
      picLoader.getLatestPics();
    }
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
    nextButton = (Button) rootView.findViewById(R.id.next);
  }

  public void displayPictures() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    List<Picture> selectablePictures = ((CreatorActivity) getActivity()).getViewModel().getSelectablePictures();
    picturesView.setAdapter(new PictureAdapter(getActivity(), R.layout.picture_thumbnail_cell, selectablePictures));
    picturesView.setLayoutManager(layoutManager);
  }

  private void onClickNextButton() {
    ((CreatorActivity) getActivity()).pickPicturesAndGoToTextWriter();
  }

  public void updateSelectablePictures() {
    picturesView.getAdapter().notifyDataSetChanged();
  }
}
