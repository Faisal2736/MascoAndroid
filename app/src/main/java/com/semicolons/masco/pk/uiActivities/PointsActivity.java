package com.semicolons.masco.pk.uiActivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.dataModels.Points;
import com.semicolons.masco.pk.viewModels.PointsViewModel;

public class PointsActivity extends AppCompatActivity {

    private PointsViewModel pointsViewModel;
    private TextView totalPointsTextView;
    private TextView totalPrice;
    private ProgressDialog progressDialog;
    private Context context;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
        getSupportActionBar().setTitle("My Wallet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context=this;
        initViews();
    }

    private void initViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        totalPointsTextView=findViewById(R.id.pointsTextView);
        totalPrice=findViewById(R.id.pointsPriceTextView);
        pointsViewModel=new ViewModelProvider(this).get(PointsViewModel.class);
        sharedPreferences = getApplicationContext().getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);

        int userId = sharedPreferences.getInt(Constants.USER_ID, 0);

        pointsViewModel.getUserPointsById(userId).observe(this, points -> {
            progressDialog.dismiss();
            if(points.getStatus()==1){
                progressDialog.dismiss();
                if(points.getTotalPoints()!=null && points.getTotalDiscount()!=null){
                    totalPointsTextView.setText(points.getTotalPoints());
                    totalPrice.setText(points.getTotalDiscount());
                }else {
                    Toast.makeText(context,"No Data Found",Toast.LENGTH_SHORT).show();
                }
            }
            else {
                progressDialog.dismiss();
                Toast.makeText(context,points.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}