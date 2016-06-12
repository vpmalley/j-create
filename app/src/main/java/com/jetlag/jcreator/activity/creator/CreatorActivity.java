package com.jetlag.jcreator.activity.creator;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jetlag.jcreator.R;
import com.jetlag.jcreator.pictures.Picture;
import com.jetlag.jcreator.pictures.PictureLoadedListener;

import java.util.List;

public class CreatorActivity extends AppCompatActivity implements PictureLoadedListener {

  public static final int REQ_READ_PICS = 101;
  public static final String PERM_READ_PICS = Manifest.permission.READ_EXTERNAL_STORAGE;
  private static final int PICTURE_FRAGMENT_POSITION = 0;
  private static final int TEXT_FRAGMENT_POSITION = 1;
  private static final int PREVIEW_FRAGMENT_POSITION = 2;

  /**
   * The {@link android.support.v4.view.PagerAdapter} that will provide
   * fragments for each of the sections. We use a
   * {@link FragmentPagerAdapter} derivative, which will keep every
   * loaded fragment in memory. If this becomes too memory intensive, it
   * may be best to switch to a
   * {@link android.support.v4.app.FragmentStatePagerAdapter}.
   */
  private CreatorPagerAdapter mCreatorPagerAdapter;

  /**
   * The {@link ViewPager} that will host the section contents.
   */
  private ViewPager mViewPager;

  private CreatorViewModel creatorViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_creator);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.
    mCreatorPagerAdapter = new CreatorPagerAdapter(getSupportFragmentManager());
    creatorViewModel = new CreatorViewModel();

    // Set up the ViewPager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.container);
    mViewPager.setAdapter(mCreatorPagerAdapter);
    checkPermission(PERM_READ_PICS, REQ_READ_PICS);
  }

  public boolean checkPermission(final String permission, final int requestCode) {
    int perm = ContextCompat.checkSelfPermission(this,
        permission);
    if (PackageManager.PERMISSION_GRANTED != perm) {
      if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
        new AlertDialog.Builder(this)
            .setMessage("You need to allow this app to read your pictures")
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCompat.requestPermissions(CreatorActivity.this,
                    new String[]{permission}, requestCode);
              }
            })
            .setNegativeButton("Cancel", null)
            .create()
            .show();
      } else {
        ActivityCompat.requestPermissions(this,
            new String[]{permission}, requestCode);
      }
    }
    return PackageManager.PERMISSION_GRANTED == perm;
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_creator, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         String permissions[], int[] grantResults) {
    switch (requestCode) {
      case REQ_READ_PICS: {
        if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          Toast.makeText(this, "cool, pics", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(this, "too bad, no pic", Toast.LENGTH_SHORT).show();
        }
        return;
      }
    }
  }

  public CreatorViewModel getViewModel() {
    return creatorViewModel;
  }

  private String getFragmentTag(int position)
  {
    return "android:switcher:" + R.id.container + ":" + position;
  }

  @Override
  public void onPicturesWithLocationLoaded(List<Picture> pictures) {
    getViewModel().setSelectablePictures(pictures);
    PicturePickerFragment picturePickerFragment = (PicturePickerFragment) getSupportFragmentManager().findFragmentByTag(getFragmentTag(PICTURE_FRAGMENT_POSITION));
    picturePickerFragment.updateSelectablePictures();
  }

  public void pickPicturesAndGoToTextWriter() {
    getViewModel().updateSelectedPictures();
    moveToStoryWriter();
  }

  private void moveToStoryWriter() {
    mViewPager.setCurrentItem(TEXT_FRAGMENT_POSITION);
    TextWriterFragment textWriterFragment = (TextWriterFragment) getSupportFragmentManager().findFragmentByTag(getFragmentTag(TEXT_FRAGMENT_POSITION));
    textWriterFragment.updateStory();
  }

  public void writeTextAndGoToArticlePreview(String text) {
    getViewModel().setStoryText(text);
    moveToStoryPreview();
  }

  private void moveToStoryPreview() {
    mViewPager.setCurrentItem(PREVIEW_FRAGMENT_POSITION);
    ArticlePreviewFragment articlePreviewFragment = (ArticlePreviewFragment) getSupportFragmentManager().findFragmentByTag(getFragmentTag(PREVIEW_FRAGMENT_POSITION));
    articlePreviewFragment.updateStory();
  }

  public void addStoryAndStartNewStory() {
    getViewModel().commitStory();
    moveToPicturePicking();
  }

  private void moveToPicturePicking() {
    mViewPager.setCurrentItem(PICTURE_FRAGMENT_POSITION);
  }
}
