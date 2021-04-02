package com.semicolons.masco.pk.uiActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.semicolons.masco.pk.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountActivity extends AppCompatActivity {

    private LinearLayout line_profile;
    private LinearLayout line_my_club;
    private LinearLayout lin_orders;

    public AccountActivity() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_account);
        getSupportActionBar().setTitle("Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();

    }

    private void initViews() {
        line_profile = findViewById(R.id.line_profile);
        line_my_club = findViewById(R.id.line_my_club);
        lin_orders = findViewById(R.id.lin_orders);

        line_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, ProfileActivity.class));
            }
        });

        lin_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, OrderActivity.class));

            }
        });

        line_my_club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, PointsActivity.class));

            }
        });
    }
}
