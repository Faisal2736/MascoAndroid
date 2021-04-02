package com.semicolons.masco.pk.uiActivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
     /*   Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        Element versionElement = new Element();
        versionElement.setTitle("Version 1.1");
        Element adsElement = new Element();
        adsElement.setTitle("Advertise with us");
        adsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + Constants.CALL_CENTER_NUMBER));
                startActivity(callIntent);
            }
        });
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .enableDarkMode(false)
//                .setCustomFont(String) // or Typeface
                .setImage(R.drawable.ic_masco_cart_about_us)
                .addItem(versionElement)
                .addItem(adsElement)
                .setDescription("Masco.pk is an e-commerce initiative of Masco.pk Supermarket\nOur motive is\n THE YOUNGER , THE FASTER")
                .addGroup("Connect with us")
                .addEmail("masco@gmail.com")
                .addWebsite("https://dealmall.org/masco/")
                .addFacebook("MASCO.pk")
                .addPlayStore("com.semicolons.masco.pk")
                .addInstagram("mascopk")
                .create();
        setContentView(aboutPage);


    }
}