package com.semicolons.masco.pk.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.dataModels.DataItem;
import com.semicolons.masco.pk.uiActivities.SubCategoryActivity;

import java.util.List;
import java.util.Random;

public class MainCategoriesAdapter extends RecyclerView.Adapter<MainCategoriesAdapter.SingleItemRowHolder> {


    private Context context;
    private List<DataItem> topSellingList;

    public MainCategoriesAdapter(Context context, List<DataItem> topSellingList) {
        this.topSellingList = topSellingList;
        this.context = context;
    }






    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.temp_main_categories_item, viewGroup, false);
        SingleItemRowHolder categoriesListVH = new SingleItemRowHolder(view);
        return categoriesListVH;
    }


    @Override
    public void onBindViewHolder(@NonNull SingleItemRowHolder holder, final int position) {

        SingleItemRowHolder viewHolder = (SingleItemRowHolder) holder;

        DataItem imagesListDM = topSellingList.get(position);
        String s = imagesListDM.getCategoryName();
        String strNew = s.replace("&amp;", "And ");

        viewHolder.tv_title.setText(strNew);
        viewHolder.tv_no_of_items.setText(imagesListDM.getNo_of_products()+"");

        viewHolder.rel_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, SubCategoryActivity.class);
                intent.putExtra(Constants.CATEGORY_OBJECT, imagesListDM);
                context.startActivity(intent);

            }
        });

        RequestOptions options = new RequestOptions()
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .priority(Priority.HIGH);
        Glide
                .with(context)
                .load(imagesListDM.getThumbnail()).error(R.drawable.ic_masco_cart).placeholder(R.drawable.ic_masco_cart)
                .apply(options)
                .into(viewHolder.img_category);


    }

    @Override
    public int getItemCount() {
        return (null != topSellingList ? topSellingList.size() : 0);
    }

    public static class SingleItemRowHolder extends RecyclerView.ViewHolder {

        ImageView img_category;
        TextView tv_title;
        TextView tv_no_of_items;
        CardView rel_deal;
        CardView cardView;

        public SingleItemRowHolder(View view) {
            super(view);

            img_category = view.findViewById(R.id.img_category);
            tv_title = view.findViewById(R.id.tv_title);
            tv_no_of_items = view.findViewById(R.id.tv_no_of_items);
            rel_deal = view.findViewById(R.id.rel_deal);
            cardView = view.findViewById(R.id.cardViewCategory);

        }

    }
}
