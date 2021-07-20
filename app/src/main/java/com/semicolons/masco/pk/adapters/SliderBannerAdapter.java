package com.semicolons.masco.pk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.dataModels.SliderImages;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class SliderBannerAdapter extends SliderViewAdapter<SliderBannerAdapter.BannerHolder> {

    private Context context;
    private ArrayList<SliderImages> mSliderItems;

    public SliderBannerAdapter(Context context, ArrayList<SliderImages> mSliderItems) {
        this.context = context;
        this.mSliderItems = mSliderItems;
    }

    public void renewItems(ArrayList<SliderImages> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderImages sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public BannerHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new BannerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(BannerHolder viewHolder, final int position) {

        SliderImages sliderItem = mSliderItems.get(position);

        int resId = context.getResources().getIdentifier(sliderItem.getImage_name(),"drawable",context.getPackageName());

        viewHolder.imageViewBackground.setImageResource(resId);

        viewHolder.itemView.setOnClickListener(v -> Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class BannerHolder extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public BannerHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
//            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }

}
