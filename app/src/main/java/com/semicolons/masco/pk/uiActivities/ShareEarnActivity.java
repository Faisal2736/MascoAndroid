package com.semicolons.masco.pk.uiActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.semicolons.masco.pk.R;

public class ShareEarnActivity extends AppCompatActivity {
    private Button btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_earn);
        getSupportActionBar().setTitle("Share & Earn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShareEarnActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

    }
}