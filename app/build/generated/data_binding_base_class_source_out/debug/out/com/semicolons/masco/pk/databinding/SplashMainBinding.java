// Generated by view binder compiler. Do not edit!
package com.semicolons.masco.pk.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.semicolons.masco.pk.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class SplashMainBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView appname;

  @NonNull
  public final ImageView logo;

  @NonNull
  public final TextView tagName;

  private SplashMainBinding(@NonNull ConstraintLayout rootView, @NonNull TextView appname,
      @NonNull ImageView logo, @NonNull TextView tagName) {
    this.rootView = rootView;
    this.appname = appname;
    this.logo = logo;
    this.tagName = tagName;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static SplashMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static SplashMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.splash_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static SplashMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.appname;
      TextView appname = rootView.findViewById(id);
      if (appname == null) {
        break missingId;
      }

      id = R.id.logo;
      ImageView logo = rootView.findViewById(id);
      if (logo == null) {
        break missingId;
      }

      id = R.id.tag_name;
      TextView tagName = rootView.findViewById(id);
      if (tagName == null) {
        break missingId;
      }

      return new SplashMainBinding((ConstraintLayout) rootView, appname, logo, tagName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}