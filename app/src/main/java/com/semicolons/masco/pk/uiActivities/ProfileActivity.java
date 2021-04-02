package com.semicolons.masco.pk.uiActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.dataModels.Profile;
import com.semicolons.masco.pk.dataModels.ProfileUpdateResponse;
import com.semicolons.masco.pk.viewModels.ProfileViewModel;

public class ProfileActivity extends AppCompatActivity {

    private ProfileViewModel profileViewModel;
    private EditText nameEditText;
    private EditText mobileNoEditText;
    private EditText addressEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private Button saveButton;
    private ProgressDialog progressDialog;
    private Context context;
    private boolean isLogin;
    private int userId;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
    }

    private void initViews() {
        context=this;
        saveButton=findViewById(R.id.profileUpdateButton);
        nameEditText=findViewById(R.id.profileNameEditText);
        mobileNoEditText=findViewById(R.id.profileMobileEditText);
        addressEditText=findViewById(R.id.profileAddressEditText);
        passwordEditText=findViewById(R.id.profilePasswordEditText);
        emailEditText=findViewById(R.id.profileEmailEditText);
        sharedPreferences=getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);

        isLogin=sharedPreferences.getBoolean(Constants.USER_IS_LOGIN,false);
        if(isLogin){
            userId=sharedPreferences.getInt(Constants.USER_ID,0);
            nameEditText.setText(sharedPreferences.getString(Constants.USER_NAME,""));
            emailEditText.setText(sharedPreferences.getString(Constants.USER_EMAIL,""));
            mobileNoEditText.setText(sharedPreferences.getString(Constants.USER_MOBILE_NUMBER,""));
            addressEditText.setText(sharedPreferences.getString(Constants.USER_ADDRESS,""));
        }

        profileViewModel=new ViewModelProvider(this).get(ProfileViewModel.class);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!nameEditText.getText().toString().isEmpty() &&
                        !emailEditText.getText().toString().isEmpty() &&
                        !mobileNoEditText.getText().toString().isEmpty() &&
                        !addressEditText.getText().toString().isEmpty() &&
                        !passwordEditText.getText().toString().isEmpty() ){

                    progressDialog = new ProgressDialog(context);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    profileViewModel.getUserUpdateProfile(userId,nameEditText.getText().toString(),
                            mobileNoEditText.getText().toString(),addressEditText.getText().toString(),
                            passwordEditText.getText().toString()).observe((LifecycleOwner) context, new Observer<ProfileUpdateResponse>() {
                        @Override
                        public void onChanged(ProfileUpdateResponse profileUpdateResponse) {
                            progressDialog.dismiss();
                            if(profileUpdateResponse!=null){
                                progressDialog.dismiss();
                                if(profileUpdateResponse.getData()!=null){
                                    Profile profile=profileUpdateResponse.getData();
                                    progressDialog.dismiss();
                                    Toast.makeText(context,profileUpdateResponse.getSuccess(),Toast.LENGTH_SHORT).show();
                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(context,"Something went Wrong",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(context,"Something went Wrong",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(ProfileActivity.this,"Please fill the blank fields",Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
}