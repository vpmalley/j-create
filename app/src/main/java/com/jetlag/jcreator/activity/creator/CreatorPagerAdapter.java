package com.jetlag.jcreator.activity.creator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class CreatorPagerAdapter extends FragmentPagerAdapter {

  public CreatorPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 1:
        return TextWriterFragment.newInstance(position + 1);
      case 0:
      default:
        return PicturePickerFragment.newInstance(position + 1);
    }
  }

  @Override
  public int getCount() {
    return 3;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    switch (position) {
      case 0:
        return "Pick your pictures";
      case 1:
        return "Write your experience";
      case 2:
        return "Publish";
    }
    return null;
  }
}
