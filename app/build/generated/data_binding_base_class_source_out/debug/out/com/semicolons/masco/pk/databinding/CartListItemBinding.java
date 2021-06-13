// Generated by view binder compiler. Do not edit!
package com.semicolons.masco.pk.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.semicolons.masco.pk.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CartListItemBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button cartAddButton;

  @NonNull
  public final Button cartMinusButton;

  @NonNull
  public final TextView cartProductBrandTextView;

  @NonNull
  public final ImageView cartProductImageView;

  @NonNull
  public final TextView cartProductNameTextView;

  @NonNull
  public final TextView cartProductPriceTextView;

  @NonNull
  public final TextView cartProductQuantityTextView;

  @NonNull
  public final RelativeLayout cartRelativeLayout;

  private CartListItemBinding(@NonNull RelativeLayout rootView, @NonNull Button cartAddButton,
      @NonNull Button cartMinusButton, @NonNull TextView cartProductBrandTextView,
      @NonNull ImageView cartProductImageView, @NonNull TextView cartProductNameTextView,
      @NonNull TextView cartProductPriceTextView, @NonNull TextView cartProductQuantityTextView,
      @NonNull RelativeLayout cartRelativeLayout) {
    this.rootView = rootView;
    this.cartAddButton = cartAddButton;
    this.cartMinusButton = cartMinusButton;
    this.cartProductBrandTextView = cartProductBrandTextView;
    this.cartProductImageView = cartProductImageView;
    this.cartProductNameTextView = cartProductNameTextView;
    this.cartProductPriceTextView = cartProductPriceTextView;
    this.cartProductQuantityTextView = cartProductQuantityTextView;
    this.cartRelativeLayout = cartRelativeLayout;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CartListItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CartListItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.cart_list_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CartListItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cartAddButton;
      Button cartAddButton = rootView.findViewById(id);
      if (cartAddButton == null) {
        break missingId;
      }

      id = R.id.cartMinusButton;
      Button cartMinusButton = rootView.findViewById(id);
      if (cartMinusButton == null) {
        break missingId;
      }

      id = R.id.cartProductBrandTextView;
      TextView cartProductBrandTextView = rootView.findViewById(id);
      if (cartProductBrandTextView == null) {
        break missingId;
      }

      id = R.id.cartProductImageView;
      ImageView cartProductImageView = rootView.findViewById(id);
      if (cartProductImageView == null) {
        break missingId;
      }

      id = R.id.cartProductNameTextView;
      TextView cartProductNameTextView = rootView.findViewById(id);
      if (cartProductNameTextView == null) {
        break missingId;
      }

      id = R.id.cartProductPriceTextView;
      TextView cartProductPriceTextView = rootView.findViewById(id);
      if (cartProductPriceTextView == null) {
        break missingId;
      }

      id = R.id.cartProductQuantityTextView;
      TextView cartProductQuantityTextView = rootView.findViewById(id);
      if (cartProductQuantityTextView == null) {
        break missingId;
      }

      RelativeLayout cartRelativeLayout = (RelativeLayout) rootView;

      return new CartListItemBinding((RelativeLayout) rootView, cartAddButton, cartMinusButton,
          cartProductBrandTextView, cartProductImageView, cartProductNameTextView,
          cartProductPriceTextView, cartProductQuantityTextView, cartRelativeLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}