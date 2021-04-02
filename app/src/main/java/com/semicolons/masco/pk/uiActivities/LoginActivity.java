package com.semicolons.masco.pk.uiActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.AppClass;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.dataModels.DataItem;
import com.semicolons.masco.pk.dataModels.Product;
import com.semicolons.masco.pk.dataModels.SignUpResponse;
import com.semicolons.masco.pk.viewModels.LoginActivityViewModel;

public class LoginActivity extends AppCompatActivity {

    LinearLayout tv_register;
    private String activityName;


    LoginActivityViewModel loginActivityViewModel;


    EditText etEmail;
    EditText etPassword;
    LinearLayout btnLogIn;

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_register = findViewById(R.id.tv_register);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        initListeners();
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {

        loginActivityViewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);
        Intent intent=getIntent();
        activityName=intent.getStringExtra("ACTIVITY_NAME");

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogIn = findViewById(R.id.btn_sign_in);

        progressDialog = new ProgressDialog(this, R.style.exitDialogTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void initListeners() {

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppClass.hideKeyboard(LoginActivity.this, v);

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() || password.isEmpty()) {

                    if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        etEmail.setError("Invalid");
                    } else {
                        etEmail.setError(null);
                    }

                    if (password.isEmpty()) {
                        etPassword.setError("Password cannot be empty");
                    } else {
                        etPassword.setError(null);
                    }

                    return;
                }

                if (AppClass.isOnline(LoginActivity.this)) {

                    progressDialog.show();

                    loginActivityViewModel.loginUser(email, password).observe(LoginActivity.this, new Observer<SignUpResponse>() {
                        @Override
                        public void onChanged(SignUpResponse apiAuthResponse) {

                            progressDialog.dismiss();

                            if (apiAuthResponse.getStatus() == "1") {
                                Toast.makeText(LoginActivity.this, "" + apiAuthResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                if(activityName!=null){
                                    if(activityName.equals("ProductDetailActivity")){
                                        Intent intent1=new Intent(LoginActivity.this,ProductDetailActivity.class);
                                        Intent intent=getIntent();
                                        assert intent1 != null;
                                        Product product = (Product) intent.getSerializableExtra(Constants.PRODUCT_OBJECT);
                                        intent1.putExtra(Constants.PRODUCT_OBJECT,product);
                                        startActivity(intent1);
                                        finish();
                                    }else if(activityName.equals("ProductListActivity")){
                                        Intent intent1=new Intent(LoginActivity.this,ProductListActivity.class);
                                        Intent intent=getIntent();
                                        assert intent1 != null;
                                        DataItem dataItem = (DataItem) intent.getSerializableExtra(Constants.SUB_CATEGORY_OBJECT);
                                        intent1.putExtra(Constants.SUB_CATEGORY_OBJECT,dataItem);
                                        startActivity(intent1);
                                        finish();
                                    }
                                }else {
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }

                            } else {
                                Toast.makeText(LoginActivity.this, "Invalid user name and password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    AppClass.offline(LoginActivity.this);
                }
            }
        });


    }
}
