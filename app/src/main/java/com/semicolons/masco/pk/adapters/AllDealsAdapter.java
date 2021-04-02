package com.semicolons.masco.pk.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.dataModels.DataItem;
import com.semicolons.masco.pk.uiActivities.ProductListActivity;

public class AllDealsAdapter extends RecyclerView.Adapter<AllDealsAdapter.SingleItemRowHolder> {

    private List<DataItem> topSellingList = new ArrayList<>();

    private Context context;

    public AllDealsAdapter(Context context, List<DataItem> topSellingList) {
        this.topSellingList = topSellingList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull SingleItemRowHolder holder, final int position) {

        SingleItemRowHolder viewHolder = (SingleItemRowHolder) holder;

        DataItem imagesListDM = topSellingList.get(position);
        String s = imagesListDM.getCategoryName();
        int id = imagesListDM.getCategoryID();
        Toast.makeText(context, "subcatadap" + id, Toast.LENGTH_SHORT).show();
        holder.tv_deals.setText(s);

        holder.rel_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra(Constants.SUB_CATEGORY_OBJECT, imagesListDM);
                context.startActivity(intent);
            }
        });


    }

    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.deals_layout_item, viewGroup, false);
        SingleItemRowHolder categoriesListVH = new SingleItemRowHolder(view);
        return categoriesListVH;
    }

    @Override
    public int getItemCount() {
        return (null != topSellingList ? topSellingList.size() : 0);
    }

    public static class SingleItemRowHolder extends RecyclerView.ViewHolder {

        ImageView img_lounge;
        TextView tv_deals;
        RelativeLayout rel_deal;

        public SingleItemRowHolder(View view) {
            super(view);

            // img_lounge = view.findViewById(R.id.img_lounge);
            tv_deals = view.findViewById(R.id.tv_deals_item);
            rel_deal = view.findViewById(R.id.dealView);

        }

    }
}
