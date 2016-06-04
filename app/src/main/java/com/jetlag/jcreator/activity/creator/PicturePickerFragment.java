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

/**
 * A placeholder fragment containing a simple view.
 */
public class PicturePickerFragment extends Fragment implements PictureLoadedListener {
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
    View rootView = inflater.inflate(R.layout.fragment_creator, container, false);
    findViews(rootView);
    picLoader = new PictureFromGalleryLoader(getContext(), this);
    if (((CreatorActivity) getActivity()).checkPermission(CreatorActivity.PERM_READ_PICS, CreatorActivity.REQ_READ_PICS)) {
      picLoader.getLatestPics();
    }
    return rootView;
  }

  private void findViews(View rootView) {
    picturesView = (RecyclerView) rootView.findViewById(R.id.pictures);
    nextButton = (Button) rootView.findViewById(R.id.next);
  }

  @Override
  public void onPicturesWithLocationLoaded(List<Picture> pictures) {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    picturesView.setAdapter(new PictureAdapter(getActivity(), R.layout.picture_thumbnail_cell, pictures));
    picturesView.setLayoutManager(layoutManager);

  }
}