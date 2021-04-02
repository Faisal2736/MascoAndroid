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

import java.util.List;
import java.util.Random;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.dataModels.DataItem;
import com.semicolons.masco.pk.uiActivities.SubCategoryActivity;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.SingleItemRowHolder> {


    private Context context;
    private List<DataItem> topSellingList;

    public CategoriesAdapter(Context context, List<DataItem> topSellingList) {
        this.topSellingList = topSellingList;
        this.context = context;
    }







    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.temp_category_item, viewGroup, false);
        SingleItemRowHolder categoriesListVH = new SingleItemRowHolder(view);
        return categoriesListVH;
    }
    @Override
    public void onBindViewHolder(@NonNull SingleItemRowHolder holder, final int position) {

        SingleItemRowHolder viewHolder = (SingleItemRowHolder) holder;

        DataItem imagesListDM = topSellingList.get(position);
        String s = imagesListDM.getCategoryName();
        String strNew = s.replace("&amp;", "And ");
        holder.tv_deals.setText(strNew);

        holder.rel_deal.setOnClickListener(new View.OnClickListener() {
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
                .into(viewHolder.image);



    }
    @Override
    public int getItemCount() {
        return (null != topSellingList ? topSellingList.size() : 0);
    }

    public static class SingleItemRowHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView tv_deals;
        // RelativeLayout rel_deal;
        LinearLayout rel_deal;
        CardView cardView;

        public SingleItemRowHolder(View view) {
            super(view);

            image = view.findViewById(R.id.image);
            tv_deals = view.findViewById(R.id.tv_deals);
            rel_deal = view.findViewById(R.id.rel_deal);
            cardView = view.findViewById(R.id.cardViewCategory);

        }

    }
}
