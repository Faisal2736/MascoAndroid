package com.semicolons.masco.pk.uiActivities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.informramiz.daypickerlibrary.views.DayPickerDialog;
import com.github.informramiz.daypickerlibrary.views.DayPickerView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;

import com.michaldrabik.classicmaterialtimepicker.CmtpTimeDialogFragment;
import com.michaldrabik.classicmaterialtimepicker.OnTime12PickedListener;
import com.michaldrabik.classicmaterialtimepicker.model.CmtpTime12;
import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.dataModels.PlaceOrderResponse;
import com.semicolons.masco.pk.viewModels.CartViewModel;
import com.semicolons.masco.pk.viewModels.OrderViewModel;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class CheckoutActivity extends AppCompatActivity {


    private TextView addressOneEditText;
    private TextView tv_select_day;
    private TextView tv_select_time;
    private static final int PICKUP_REQUEST_CODE = 88;
    private Button confirmOrderButton;


    private Context context;
    LatLng pickLatlng;
    String pickupAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Checkout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
    }

    private void initViews() {
        context=this;
        addressOneEditText=findViewById(R.id.checkoutAddressEditText);
        confirmOrderButton=findViewById(R.id.confirmOrderButton);
        tv_select_day=findViewById(R.id.tv_select_day);
        tv_select_time=findViewById(R.id.tv_select_time);

        initListener();


    }

    private void initListener() {

        addressOneEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkLocationPermission(view)) {

                    Intent intent = new Intent(CheckoutActivity.this, SelectLocationActivity.class);
                    intent.putExtra(Constants.CALLED, "PICKUP");
                    startActivityForResult(intent, PICKUP_REQUEST_CODE);

                }


            }
        });
        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pickupAddress!=null) {

                    Intent intent = new Intent(CheckoutActivity.this, OrderReciptActivity.class);
                    intent.putExtra(Constants.ORDER_PLACE_ADDRESS, pickupAddress);

                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(context, "Address is Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_select_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DayPickerDialog.Builder builder = new DayPickerDialog.Builder(CheckoutActivity.this)
                        .setMultiSelectionAllowed(false)
                        .setOnDaysSelectedListener(new DayPickerDialog.OnDaysSelectedListener() {
                            @Override
                            public void onDaysSelected(DayPickerView dayPickerView, boolean[] selectedDays) {
                                //do something with selected days
                              boolean sunday=  selectedDays[0];
                              boolean monday=  selectedDays[1];
                              boolean tuesday=  selectedDays[2];
                              boolean wednesday=  selectedDays[3];
                              boolean thursday=  selectedDays[4];
                              boolean friday=  selectedDays[5];
                              boolean saturday=  selectedDays[6];
                              if (sunday){
                                  tv_select_day.setText("Sunday");
                              }
                                if (monday){
                                    tv_select_day.setText("Monday");
                                }
                                if (tuesday){
                                    tv_select_day.setText("Tuesday");
                                }
                                if (wednesday){
                                    tv_select_day.setText("Wednesday");
                                }
                                if (thursday){
                                    tv_select_day.setText("Thursday");
                                }
                                if (friday){
                                    tv_select_day.setText("Friday");
                                }
                                if (saturday){
                                    tv_select_day.setText("saturday");
                                }

                            }
                        });
                builder.build().show();



            }
        });



        tv_select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CmtpTimeDialogFragment cmtpTimeDialogFragment       =  CmtpTimeDialogFragment.newInstance();
                cmtpTimeDialogFragment.show(getSupportFragmentManager(),"TimePicker");
                cmtpTimeDialogFragment.setInitialTime12(5,2, CmtpTime12.PmAm.PM);

                cmtpTimeDialogFragment.setOnTime12PickedListener(new OnTime12PickedListener() {
                    @Override
                    public void onTimePicked(CmtpTime12 cmtpTime12) {

                      int  hour =  cmtpTime12.getHour();
                      int  minute =  cmtpTime12.getMinute();

                        tv_select_time.setText(String.valueOf(hour)+":"+String.valueOf(minute));
                    }
                });

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

                addressOneEditText.setText(pickupAddress);


            }
        }

    }

    public boolean checkLocationPermission(View v) {

        if (ContextCompat.checkSelfPermission(CheckoutActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            return true;
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(CheckoutActivity.this, ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(CheckoutActivity.this, new String[]{ACCESS_FINE_LOCATION}, 1234);
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