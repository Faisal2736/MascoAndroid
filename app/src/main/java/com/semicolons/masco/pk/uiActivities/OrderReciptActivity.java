package com.semicolons.masco.pk.uiActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.dataModels.OrderDetailResponse;
import com.semicolons.masco.pk.dataModels.PlaceOrderResponse;
import com.semicolons.masco.pk.viewModels.CartViewModel;
import com.semicolons.masco.pk.viewModels.OrderViewModel;

public class OrderReciptActivity extends AppCompatActivity {


    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText mobileEditText;
    private static final int PICKUP_REQUEST_CODE = 88;
    private EditText cityEditText;
    private Button confirmOrderButton;
    private OrderViewModel orderViewModel;
    private CartViewModel cartViewModel;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private boolean isLogin;
    private ProgressDialog progressDialog;
    private Context context;
    LatLng pickLatlng;
    String pickupAddress;
    private TextView addressTwoEditText;
    private String address;

    int orderid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_recipt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Delivery Details");
        address = getIntent().getStringExtra(Constants.ORDER_PLACE_ADDRESS);

        initViews();

    }

    private void initViews() {
        context=this;
        usernameEditText=findViewById(R.id.checkoutUserNameEditText);
        emailEditText=findViewById(R.id.checkoutEmailEditText);
        mobileEditText=findViewById(R.id.checkoutMobileEditText);
        cityEditText=findViewById(R.id.checloutCityEditText);
        confirmOrderButton=findViewById(R.id.confirmOrderButton);
        sharedPreferences = getApplicationContext().getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);
        isLogin=sharedPreferences.getBoolean(Constants.USER_IS_LOGIN,false);
        orderViewModel=new ViewModelProvider(this).get(OrderViewModel.class);
        cartViewModel=new ViewModelProvider(this).get(CartViewModel.class);

        initListener();

        if(isLogin){
            usernameEditText.setText(sharedPreferences.getString(Constants.USER_NAME,""));
            emailEditText.setText(sharedPreferences.getString(Constants.USER_EMAIL,""));
            mobileEditText.setText(sharedPreferences.getString(Constants.USER_MOBILE_NUMBER,""));
        }else {
            Toast.makeText(this,"Please login to continue",Toast.LENGTH_SHORT).show();
        }
    }

    private void initListener() {


        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isLogin){
                    if(usernameEditText.getText().toString().isEmpty() ||
                            emailEditText.getText().toString().isEmpty() ||
                            mobileEditText.getText().toString().isEmpty() ||
                    cityEditText.getText().toString().isEmpty()) {
                        //||
                        //                            cityEditText.getText().toString().isEmpty()
                        Toast.makeText(context,"Please fill the blank fields",Toast.LENGTH_SHORT).show();
                    }else {

                        progressDialog = new ProgressDialog(OrderReciptActivity.this);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Please Wait...");
                        progressDialog.setCancelable(false);
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        String username=usernameEditText.getText().toString();
                        String email=emailEditText.getText().toString();
                        String mobile=mobileEditText.getText().toString();
                        String address1=address;
                        String address2=address;
                        String city=cityEditText.getText().toString();
                        int userId=sharedPreferences.getInt(Constants.USER_ID,0);
                        orderViewModel.placeOrder(username,email,mobile,address1,address2,city,userId).observe(OrderReciptActivity.this, new Observer<PlaceOrderResponse>() {
                            @Override
                            public void onChanged(PlaceOrderResponse placeOrderResponse) {

                                progressDialog.dismiss();
                                if(placeOrderResponse!=null){
                                    progressDialog.dismiss();
                                    cartViewModel.deleteCartTable();
                                  orderid =   placeOrderResponse.getOrderId();
                                    showResponseDialog("Success", placeOrderResponse.getData());
                                }else {
                                    progressDialog.dismiss();

                                    showResponseDialogFailed("Error", "Try Again");
                                }

                            }
                        });
                    }
                }
            }
        });



    }




    private void showResponseDialog(String title, String msg) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(OrderReciptActivity.this, OrderDetailActivity.class);
                        intent.putExtra("ORDER_ID",orderid);
                        startActivity(intent);
                        finish();
                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }

    private void showResponseDialogFailed(String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(OrderReciptActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}