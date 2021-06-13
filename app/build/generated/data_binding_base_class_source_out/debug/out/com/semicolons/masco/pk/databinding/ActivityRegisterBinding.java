// Generated by view binder compiler. Do not edit!
package com.semicolons.masco.pk.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;
import com.semicolons.masco.pk.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegisterBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final LinearLayout btnSignup;

  @NonNull
  public final CountryCodePicker ccp;

  @NonNull
  public final TextView etAddress;

  @NonNull
  public final EditText etEmail;

  @NonNull
  public final EditText etMobile;

  @NonNull
  public final EditText etPassword;

  @NonNull
  public final EditText etUsuername;

  private ActivityRegisterBinding(@NonNull ConstraintLayout rootView,
      @NonNull LinearLayout btnSignup, @NonNull CountryCodePicker ccp, @NonNull TextView etAddress,
      @NonNull EditText etEmail, @NonNull EditText etMobile, @NonNull EditText etPassword,
      @NonNull EditText etUsuername) {
    this.rootView = rootView;
    this.btnSignup = btnSignup;
    this.ccp = ccp;
    this.etAddress = etAddress;
    this.etEmail = etEmail;
    this.etMobile = etMobile;
    this.etPassword = etPassword;
    this.etUsuername = etUsuername;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_register, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegisterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnSignup;
      LinearLayout btnSignup = rootView.findViewById(id);
      if (btnSignup == null) {
        break missingId;
      }

      id = R.id.ccp;
      CountryCodePicker ccp = rootView.findViewById(id);
      if (ccp == null) {
        break missingId;
      }

      id = R.id.et_address;
      TextView etAddress = rootView.findViewById(id);
      if (etAddress == null) {
        break missingId;
      }

      id = R.id.et_email;
      EditText etEmail = rootView.findViewById(id);
      if (etEmail == null) {
        break missingId;
      }

      id = R.id.et_mobile;
      EditText etMobile = rootView.findViewById(id);
      if (etMobile == null) {
        break missingId;
      }

      id = R.id.et_password;
      EditText etPassword = rootView.findViewById(id);
      if (etPassword == null) {
        break missingId;
      }

      id = R.id.et_usuername;
      EditText etUsuername = rootView.findViewById(id);
      if (etUsuername == null) {
        break missingId;
      }

      return new ActivityRegisterBinding((ConstraintLayout) rootView, btnSignup, ccp, etAddress,
          etEmail, etMobile, etPassword, etUsuername);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}