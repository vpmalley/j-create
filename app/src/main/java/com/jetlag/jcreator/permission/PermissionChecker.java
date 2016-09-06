package com.jetlag.jcreator.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * Created by vince on 05/09/16.
 */
public class PermissionChecker {

  public boolean checkPermission(final Activity activity, final String permission, final int requestCode, final String explanationMessage) {
    int perm = ContextCompat.checkSelfPermission(activity, permission);
    if (PackageManager.PERMISSION_GRANTED != perm) {
      if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
        Snackbar.make(activity.getCurrentFocus(), explanationMessage, Snackbar.LENGTH_LONG)
            .setAction("Allow", new RequestPermissionOnClick(activity, permission, requestCode))
            .show();
      } else {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
      }
    }
    return PackageManager.PERMISSION_GRANTED == perm;
  }

  private class RequestPermissionOnClick implements View.OnClickListener {

    private final Activity activity;
    private final String permission;
    private final int requestCode;

    public RequestPermissionOnClick(Activity activity, String permission, int requestCode) {
      this.activity = activity;
      this.permission = permission;
      this.requestCode = requestCode;
    }

    @Override
    public void onClick(View view) {
      ActivityCompat.requestPermissions(activity,
          new String[]{permission}, requestCode);
    }
  }

}
