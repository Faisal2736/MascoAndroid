package com.semicolons.masco.pk.uiActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.adapters.SliderAdapter;
import com.semicolons.masco.pk.dataModels.ImageSliderModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class SliderActivity extends AppCompatActivity {
    List<ImageSliderModel> imageSliderModelList;
    private LinearLayout loc_btn;
    private TextView login;
    SliderView sliderView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.slider_activity);
        this.sliderView = (SliderView) findViewById(R.id.imageSlider);
        this.login = (TextView) findViewById(R.id.login_link);
        this.loc_btn =  findViewById(R.id.location_btn);
        ArrayList arrayList = new ArrayList();
        this.imageSliderModelList = arrayList;
        this.imageSliderModelList.add(new ImageSliderModel(R.drawable.slider_1));
        this.imageSliderModelList.add(new ImageSliderModel(R.drawable.slider_2));
        this.imageSliderModelList.add(new ImageSliderModel(R.drawable.slider_3));
        this.sliderView.setSliderAdapter(new SliderAdapter(this, imageSliderModelList));
        this.sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        this.sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        this.sliderView.setAutoCycleDirection(2);
        this.sliderView.setIndicatorSelectedColor(-1);
        this.sliderView.setIndicatorUnselectedColor(-7829368);
        this.sliderView.setScrollTimeInSec(4);
        this.sliderView.startAutoCycle();
        this.login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SliderActivity.this.startActivity(new Intent(SliderActivity.this, LoginActivity.class));
            }
        });
        this.loc_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SliderActivity.this.getApplicationContext(), HomeActivity.class);
                intent.putExtra("SliderActivity", "slider");
                SliderActivity.this.startActivity(intent);
            }
        });
    }
}
