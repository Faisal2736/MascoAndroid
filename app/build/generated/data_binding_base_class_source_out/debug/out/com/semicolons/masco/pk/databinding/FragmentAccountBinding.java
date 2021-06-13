// Generated by view binder compiler. Do not edit!
package com.semicolons.masco.pk.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.semicolons.masco.pk.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentAccountBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout frameLayout;

  @NonNull
  public final ImageView imageView10;

  @NonNull
  public final ImageView imageView12;

  @NonNull
  public final ImageView imageView16;

  @NonNull
  public final ImageView imageView4;

  @NonNull
  public final ImageView imageView6;

  @NonNull
  public final ImageView imageView7;

  @NonNull
  public final ImageView imageView8;

  @NonNull
  public final ImageView imgLounge;

  @NonNull
  public final LinearLayout linOrders;

  @NonNull
  public final LinearLayout lineMyClub;

  @NonNull
  public final LinearLayout lineProfile;

  @NonNull
  public final TextView textView25;

  @NonNull
  public final TextView textView26;

  @NonNull
  public final TextView textView28;

  @NonNull
  public final TextView textView30;

  @NonNull
  public final TextView textView5;

  @NonNull
  public final TextView textView8;

  @NonNull
  public final TextView textView9;

  private FragmentAccountBinding(@NonNull LinearLayout rootView, @NonNull LinearLayout frameLayout,
      @NonNull ImageView imageView10, @NonNull ImageView imageView12,
      @NonNull ImageView imageView16, @NonNull ImageView imageView4, @NonNull ImageView imageView6,
      @NonNull ImageView imageView7, @NonNull ImageView imageView8, @NonNull ImageView imgLounge,
      @NonNull LinearLayout linOrders, @NonNull LinearLayout lineMyClub,
      @NonNull LinearLayout lineProfile, @NonNull TextView textView25, @NonNull TextView textView26,
      @NonNull TextView textView28, @NonNull TextView textView30, @NonNull TextView textView5,
      @NonNull TextView textView8, @NonNull TextView textView9) {
    this.rootView = rootView;
    this.frameLayout = frameLayout;
    this.imageView10 = imageView10;
    this.imageView12 = imageView12;
    this.imageView16 = imageView16;
    this.imageView4 = imageView4;
    this.imageView6 = imageView6;
    this.imageView7 = imageView7;
    this.imageView8 = imageView8;
    this.imgLounge = imgLounge;
    this.linOrders = linOrders;
    this.lineMyClub = lineMyClub;
    this.lineProfile = lineProfile;
    this.textView25 = textView25;
    this.textView26 = textView26;
    this.textView28 = textView28;
    this.textView30 = textView30;
    this.textView5 = textView5;
    this.textView8 = textView8;
    this.textView9 = textView9;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAccountBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAccountBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_account, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAccountBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayout frameLayout = (LinearLayout) rootView;

      id = R.id.imageView10;
      ImageView imageView10 = rootView.findViewById(id);
      if (imageView10 == null) {
        break missingId;
      }

      id = R.id.imageView12;
      ImageView imageView12 = rootView.findViewById(id);
      if (imageView12 == null) {
        break missingId;
      }

      id = R.id.imageView16;
      ImageView imageView16 = rootView.findViewById(id);
      if (imageView16 == null) {
        break missingId;
      }

      id = R.id.imageView4;
      ImageView imageView4 = rootView.findViewById(id);
      if (imageView4 == null) {
        break missingId;
      }

      id = R.id.imageView6;
      ImageView imageView6 = rootView.findViewById(id);
      if (imageView6 == null) {
        break missingId;
      }

      id = R.id.imageView7;
      ImageView imageView7 = rootView.findViewById(id);
      if (imageView7 == null) {
        break missingId;
      }

      id = R.id.imageView8;
      ImageView imageView8 = rootView.findViewById(id);
      if (imageView8 == null) {
        break missingId;
      }

      id = R.id.img_lounge;
      ImageView imgLounge = rootView.findViewById(id);
      if (imgLounge == null) {
        break missingId;
      }

      id = R.id.lin_orders;
      LinearLayout linOrders = rootView.findViewById(id);
      if (linOrders == null) {
        break missingId;
      }

      id = R.id.line_my_club;
      LinearLayout lineMyClub = rootView.findViewById(id);
      if (lineMyClub == null) {
        break missingId;
      }

      id = R.id.line_profile;
      LinearLayout lineProfile = rootView.findViewById(id);
      if (lineProfile == null) {
        break missingId;
      }

      id = R.id.textView25;
      TextView textView25 = rootView.findViewById(id);
      if (textView25 == null) {
        break missingId;
      }

      id = R.id.textView26;
      TextView textView26 = rootView.findViewById(id);
      if (textView26 == null) {
        break missingId;
      }

      id = R.id.textView28;
      TextView textView28 = rootView.findViewById(id);
      if (textView28 == null) {
        break missingId;
      }

      id = R.id.textView30;
      TextView textView30 = rootView.findViewById(id);
      if (textView30 == null) {
        break missingId;
      }

      id = R.id.textView5;
      TextView textView5 = rootView.findViewById(id);
      if (textView5 == null) {
        break missingId;
      }

      id = R.id.textView8;
      TextView textView8 = rootView.findViewById(id);
      if (textView8 == null) {
        break missingId;
      }

      id = R.id.textView9;
      TextView textView9 = rootView.findViewById(id);
      if (textView9 == null) {
        break missingId;
      }

      return new FragmentAccountBinding((LinearLayout) rootView, frameLayout, imageView10,
          imageView12, imageView16, imageView4, imageView6, imageView7, imageView8, imgLounge,
          linOrders, lineMyClub, lineProfile, textView25, textView26, textView28, textView30,
          textView5, textView8, textView9);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}