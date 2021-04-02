package com.semicolons.masco.pk.uiActivities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.Utils.Utilities;

public class SelectLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    FusedLocationProviderClient fusedLocationClient;
    Location location;
    TextView locAddress;
    Button btnConfirm;
    PlacesClient placesClient;
    AutocompleteSupportFragment places_fragment;
    List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
    LatLng selectedLatLng;
    String selectedAddress;
    String called;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (googleServicesAvailable()) {
            initMap();
        }

        called = getIntent().getStringExtra(Constants.CALLED);

        initViews();
        initListeners();
    }

    private void initViews() {

        Places.initialize(SelectLocationActivity.this, getString(R.string.google_map_api));
        placesClient = Places.createClient(SelectLocationActivity.this);
        locAddress = findViewById(R.id.locAddress);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setText("Confirm " + called);
    }

    private void initListeners() {

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedLatLng != null) {
                    Intent intent = new Intent();
                    intent.putExtra(Constants.LAT, String.valueOf(selectedLatLng.latitude));
                    intent.putExtra(Constants.LNG, String.valueOf(selectedLatLng.longitude));
                    intent.putExtra(Constants.ADDRESS, selectedAddress);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(SelectLocationActivity.this, "Plese select a valid location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

//        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            checkGPSAvailability();
//        }
//        else {
        getLocation();

        places_fragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.places_fragment);
        places_fragment.setPlaceFields(placeFields);

        places_fragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

                Log.d("sncbsmn", "onPlaceSelected: " + place.getLatLng().latitude);
                Log.d("sncbsmn", "onPlaceSelected: " + place.getLatLng().longitude);
                Log.d("sncbsmn", "onPlaceSelected: " + place.getAddress());
                Log.d("sncbsmn", "onPlaceSelected: " + place.getName());
                Log.d("sncbsmn", "onPlaceSelected: " + place.getPhoneNumber());

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 18));
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                selectedLatLng = mMap.getCameraPosition().target;
//                    selectedAddress = "";
                selectedAddress = Utilities.getCompleteAddressString(
                        SelectLocationActivity.this, selectedLatLng.latitude, selectedLatLng.longitude);

                if (selectedAddress.isEmpty() || selectedAddress.equalsIgnoreCase("")) {
                    selectedAddress = getAddress(selectedLatLng.latitude, selectedLatLng.longitude);

                    if (selectedAddress.isEmpty() || selectedAddress.equalsIgnoreCase("")) {
                        selectedAddress = "Latitude: " + selectedLatLng.latitude + " Longitude: " + selectedLatLng.longitude;
                    }
                }

                locAddress.setText(selectedAddress);
            }
        });
//        }
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

//        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        fusedLocationClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            location = task.getResult();

                            if (location != null) {
                                mMap.animateCamera(CameraUpdateFactory.
                                        newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17));

                                MarkerOptions markerOptions = new MarkerOptions()
                                        .position(new LatLng(location.getLatitude(), location.getLongitude()))
                                        .title("Current Location")
                                        .snippet("")
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location));

                                Marker marker = mMap.addMarker(markerOptions);

                            } else {
                                Toast.makeText(SelectLocationActivity.this, "Unable to find location.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SelectLocationActivity.this, "Failed to get location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void checkGPSAvailability() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void initMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    public boolean googleServicesAvailable() {

        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);

        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cant connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(SelectLocationActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getAddressLine(0)).append(", ");
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        return result.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
