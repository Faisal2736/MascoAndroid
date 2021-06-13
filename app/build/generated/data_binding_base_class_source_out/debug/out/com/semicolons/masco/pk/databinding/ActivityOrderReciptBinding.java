// Generated by view binder compiler. Do not edit!
package com.semicolons.masco.pk.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.semicolons.masco.pk.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityOrderReciptBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView checkoutBillingAddressTwoEditText;

  @NonNull
  public final TextInputEditText checkoutEmailEditText;

  @NonNull
  public final TextInputEditText checkoutMobileEditText;

  @NonNull
  public final TextInputEditText checkoutUserNameEditText;

  @NonNull
  public final TextInputEditText checloutCityEditText;

  @NonNull
  public final Button confirmOrderButton;

  @NonNull
  public final LinearLayout linearLayout6;

  private ActivityOrderReciptBinding(@NonNull RelativeLayout rootView,
      @NonNull TextView checkoutBillingAddressTwoEditText,
      @NonNull TextInputEditText checkoutEmailEditText,
      @NonNull TextInputEditText checkoutMobileEditText,
      @NonNull TextInputEditText checkoutUserNameEditText,
      @NonNull TextInputEditText checloutCityEditText, @NonNull Button confirmOrderButton,
      @NonNull LinearLayout linearLayout6) {
    this.rootView = rootView;
    this.checkoutBillingAddressTwoEditText = checkoutBillingAddressTwoEditText;
    this.checkoutEmailEditText = checkoutEmailEditText;
    this.checkoutMobileEditText = checkoutMobileEditText;
    this.checkoutUserNameEditText = checkoutUserNameEditText;
    this.checloutCityEditText = checloutCityEditText;
    this.confirmOrderButton = confirmOrderButton;
    this.linearLayout6 = linearLayout6;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityOrderReciptBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityOrderReciptBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_order_recipt, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityOrderReciptBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.checkoutBillingAddressTwoEditText;
      TextView checkoutBillingAddressTwoEditText = rootView.findViewById(id);
      if (checkoutBillingAddressTwoEditText == null) {
        break missingId;
      }

      id = R.id.checkoutEmailEditText;
      TextInputEditText checkoutEmailEditText = rootView.findViewById(id);
      if (checkoutEmailEditText == null) {
        break missingId;
      }

      id = R.id.checkoutMobileEditText;
      TextInputEditText checkoutMobileEditText = rootView.findViewById(id);
      if (checkoutMobileEditText == null) {
        break missingId;
      }

      id = R.id.checkoutUserNameEditText;
      TextInputEditText checkoutUserNameEditText = rootView.findViewById(id);
      if (checkoutUserNameEditText == null) {
        break missingId;
      }

      id = R.id.checloutCityEditText;
      TextInputEditText checloutCityEditText = rootView.findViewById(id);
      if (checloutCityEditText == null) {
        break missingId;
      }

      id = R.id.confirmOrderButton;
      Button confirmOrderButton = rootView.findViewById(id);
      if (confirmOrderButton == null) {
        break missingId;
      }

      id = R.id.linearLayout6;
      LinearLayout linearLayout6 = rootView.findViewById(id);
      if (linearLayout6 == null) {
        break missingId;
      }

      return new ActivityOrderReciptBinding((RelativeLayout) rootView,
          checkoutBillingAddressTwoEditText, checkoutEmailEditText, checkoutMobileEditText,
          checkoutUserNameEditText, checloutCityEditText, confirmOrderButton, linearLayout6);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}