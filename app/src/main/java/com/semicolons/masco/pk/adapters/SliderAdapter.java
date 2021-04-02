package com.semicolons.masco.pk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.dataModels.ImageSliderModel;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderViewHolder> {
    Context context;
    List<ImageSliderModel> imageSliderModelList;

    public SliderAdapter(Context context2, List<ImageSliderModel> imageSliderModelList2) {
        this.context = context2;
        this.imageSliderModelList = imageSliderModelList2;
    }

    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_layout, (ViewGroup) null));
    }

    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        viewHolder.imageViewBackground.setImageResource(this.imageSliderModelList.get(position).getImg());
    }

    public int getCount() {
        return this.imageSliderModelList.size();
    }

    class SliderViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView imageViewBackground;

        public SliderViewHolder(View itemView) {
            super(itemView);
            this.imageViewBackground = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
