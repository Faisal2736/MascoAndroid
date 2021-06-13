// Generated by view binder compiler. Do not edit!
package com.semicolons.masco.pk.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import com.semicolons.masco.pk.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class TempMainCategoriesItemBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final ImageView imgArrow;

  @NonNull
  public final ImageView imgCategory;

  @NonNull
  public final CardView relDeal;

  @NonNull
  public final TextView tvNoOfItems;

  @NonNull
  public final TextView tvTitle;

  private TempMainCategoriesItemBinding(@NonNull CardView rootView, @NonNull ImageView imgArrow,
      @NonNull ImageView imgCategory, @NonNull CardView relDeal, @NonNull TextView tvNoOfItems,
      @NonNull TextView tvTitle) {
    this.rootView = rootView;
    this.imgArrow = imgArrow;
    this.imgCategory = imgCategory;
    this.relDeal = relDeal;
    this.tvNoOfItems = tvNoOfItems;
    this.tvTitle = tvTitle;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static TempMainCategoriesItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static TempMainCategoriesItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.temp_main_categories_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static TempMainCategoriesItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.img_arrow;
      ImageView imgArrow = rootView.findViewById(id);
      if (imgArrow == null) {
        break missingId;
      }

      id = R.id.img_category;
      ImageView imgCategory = rootView.findViewById(id);
      if (imgCategory == null) {
        break missingId;
      }

      CardView relDeal = (CardView) rootView;

      id = R.id.tv_no_of_items;
      TextView tvNoOfItems = rootView.findViewById(id);
      if (tvNoOfItems == null) {
        break missingId;
      }

      id = R.id.tv_title;
      TextView tvTitle = rootView.findViewById(id);
      if (tvTitle == null) {
        break missingId;
      }

      return new TempMainCategoriesItemBinding((CardView) rootView, imgArrow, imgCategory, relDeal,
          tvNoOfItems, tvTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}