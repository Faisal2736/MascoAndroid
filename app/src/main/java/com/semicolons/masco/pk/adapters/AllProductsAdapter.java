package com.semicolons.masco.pk.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.dataModels.Product;
import com.semicolons.masco.pk.uiActivities.ProductDetailActivity;


public class AllProductsAdapter extends RecyclerView.Adapter<AllProductsAdapter.SingleItemRowHolder> {
    private ArrayList<Product> allProductsList = new ArrayList<>();

    private Context context;

    public AllProductsAdapter(Context context, ArrayList<Product> allProductsList) {
        this.allProductsList = allProductsList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull SingleItemRowHolder holder, final int position) {

        SingleItemRowHolder viewHolder = (SingleItemRowHolder) holder;
        Product product = allProductsList.get(position);

        RequestOptions options = new RequestOptions()
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .priority(Priority.HIGH);
        Glide
                .with(context)
                .load(allProductsList.get(position).getImage_name())
                .apply(options)
                .into(viewHolder.image);

        viewHolder.name.setText(allProductsList.get(position).getProduct_title());
        viewHolder.price.setText(allProductsList.get(position).getPrice());

        viewHolder.linear_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra(Constants.PRODUCT_OBJECT, product);
                context.startActivity(intent);

            }
        });
    }

    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_all_products, viewGroup, false);
        SingleItemRowHolder categoriesListVH = new SingleItemRowHolder(view);
        return categoriesListVH;
    }

    @Override
    public int getItemCount() {
        return (null != allProductsList ? allProductsList.size() : 0);
    }

    public static class SingleItemRowHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView price;
        LinearLayout linear_label;

        public SingleItemRowHolder(View view) {
            super(view);

            image = view.findViewById(R.id.image);
            name = view.findViewById(R.id.name);
            price = view.findViewById(R.id.price);
            linear_label = view.findViewById(R.id.linear_label);

        }

    }
}


