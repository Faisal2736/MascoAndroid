package com.semicolons.masco.pk.uiActivities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;
import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.AppClass;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.dataModels.SignUpResponse;
import com.semicolons.masco.pk.viewModels.SignupActivityViewModel;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class RegisterActivity extends AppCompatActivity {

    SignupActivityViewModel signupActivityViewModel;

    EditText etFName;
    EditText etLName;
    EditText etEmail;
    EditText etPassword;
    EditText etMobile;
    private static final int PICKUP_REQUEST_CODE = 88;
    LinearLayout btnSignup;
    TextView etaddress;
    LatLng pickLatlng;
    String pickupAddress;
    private CountryCodePicker ccp;


    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        initListeners();
    }

    private void initViews() {

        signupActivityViewModel = new ViewModelProvider(this).get(SignupActivityViewModel.class);


        etLName = findViewById(R.id.et_usuername);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etMobile = findViewById(R.id.et_mobile);
        etMobile = findViewById(R.id.et_mobile);
        etaddress = findViewById(R.id.et_address);
        btnSignup = findViewById(R.id.btnSignup);

        progressDialog = new ProgressDialog(this, R.style.exitDialogTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void initListeners() {
        etaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkLocationPermission(view)) {

                    Intent intent = new Intent(RegisterActivity.this, SelectLocationActivity.class);
                    intent.putExtra(Constants.CALLED, "PICKUP");
                    startActivityForResult(intent, PICKUP_REQUEST_CODE);

                }


            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppClass.hideKeyboard(RegisterActivity.this, v);

                String lName = etLName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String mobile = etMobile.getText().toString();
                String address = etaddress.getText().toString();

                if (lName.isEmpty() || email.isEmpty() || password.isEmpty() ||
                        email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    if (lName.isEmpty()) {
                        etFName.setError(" Name should not be empty");
                    } else {
                        etFName.setError(null);
                    }


                    if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        etEmail.setError("Enter a valid Email address");
                    } else {
                        etEmail.setError(null);
                    }

                    if (mobile.isEmpty()) {
                        etMobile.setError("Please enter your mobile number");
                    } else {
                        etMobile.setError(null);
                    }

                    if (password.isEmpty()) {
                        etPassword.setError("Password cannot be empty");
                    } else {
                        etPassword.setError(null);
                    }

                    return;
                }

                if (AppClass.isOnline(RegisterActivity.this)) {

                    progressDialog.show();

                    signupActivityViewModel.signupUser(lName, email, password, mobile, address)
                            .observe(RegisterActivity.this, new Observer<SignUpResponse>() {
                                @Override
                                public void onChanged(SignUpResponse apiAuthResponse) {

                                    progressDialog.dismiss();

                                    if (apiAuthResponse.getStatus().equals("1")) {
                                        Toast.makeText(RegisterActivity.this, "" + apiAuthResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "" + apiAuthResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    AppClass.offline(RegisterActivity.this);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICKUP_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                String pickupLat = data.getStringExtra(Constants.LAT);
                String pickuplng = data.getStringExtra(Constants.LNG);

                if (pickupLat != null && pickuplng != null) {
                    pickLatlng = new LatLng(Float.parseFloat(pickupLat), Float.parseFloat(pickuplng));
                }
                pickupAddress = data.getStringExtra(Constants.ADDRESS);

                etaddress.setText(pickupAddress);


            }
        }

    }

    public boolean checkLocationPermission(View v) {

        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            return true;
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{ACCESS_FINE_LOCATION}, 1234);
            } else {
                Snackbar snackbar = Snackbar.make(v, "Please grant access to your Location", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Grant Access", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentSetting = new Intent();
                        intentSetting.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intentSetting.setData(uri);
                        startActivity(intentSetting);

                    }
                }).show();
            }
            return false;
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1234) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
//                finish();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // can be schedule in this way also
                //  Utils.scheduleJob(this, LocationUpdatesService.class);
                //doing this way to communicate via messenger
                // Start service and provide it a way to communicate with this class.
//                locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
//                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                    checkGPSAvailability();
//                }
//                else {
//                }
            } else {
                // Permission denied.
//                finish();
            }
        }
    }
}
