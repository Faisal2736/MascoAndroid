package com.semicolons.masco.pk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.dataModels.ImagesListDM;

public class LoungeDealAdapter extends RecyclerView.Adapter<LoungeDealAdapter.SingleItemRowHolder> {

    private ArrayList<ImagesListDM> topSellingList = new ArrayList<>();

    private Context context;

    public LoungeDealAdapter(Context context, ArrayList<ImagesListDM> topSellingList) {
        this.topSellingList = topSellingList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull SingleItemRowHolder holder, final int position) {

        SingleItemRowHolder viewHolder = (SingleItemRowHolder) holder;

        ImagesListDM imagesListDM = topSellingList.get(position);
        holder.img_lounge.setImageResource(imagesListDM.getImg());
        String s = imagesListDM.getNameImg();
        holder.tv_deals.setText(s);


    }

    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lounge_deal_item, viewGroup, false);
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

            img_lounge = view.findViewById(R.id.img_lounge);
            tv_deals = view.findViewById(R.id.tv_deals);
            rel_deal = view.findViewById(R.id.rel_deal);

        }

    }
}
