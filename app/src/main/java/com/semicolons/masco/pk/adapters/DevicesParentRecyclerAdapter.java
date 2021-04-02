package com.semicolons.masco.pk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.dataModels.HomeDataModel;
import com.semicolons.masco.pk.viewHolders.ItemRowHolder;


public class DevicesParentRecyclerAdapter extends RecyclerView.Adapter<ItemRowHolder> {

    HomeDataModel homeDataModel;
    private Context context;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public DevicesParentRecyclerAdapter(Context context, HomeDataModel homeDataModel) {
        this.homeDataModel = homeDataModel;
        this.context = context;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.parent_recy_list_items, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder itemRowHolder, int position) {

        if (position == 0) {

            if (homeDataModel.getTopSellingList().size() > 0) {
                itemRowHolder.tv_titleRecycler.setVisibility(View.VISIBLE);

                itemRowHolder.tv_titleRecycler.setText(homeDataModel.getTitleList().get(position));
            } else {
                itemRowHolder.tv_titleRecycler.setVisibility(View.GONE);

            }
            TopSellingAdapter topSellingAdapter = new TopSellingAdapter(homeDataModel.getTopSellingList(), null, context);
            itemRowHolder.recycler_view_list.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            ((LinearLayoutManager) layoutManager).setInitialPrefetchItemCount(1);
            itemRowHolder.recycler_view_list.setLayoutManager(layoutManager);
            itemRowHolder.recycler_view_list.setAdapter(topSellingAdapter);

            itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);
            topSellingAdapter.notifyDataSetChanged();
        }
        else if (position == 1) {

            if (homeDataModel.getLatestList().size() > 0) {
                itemRowHolder.tv_titleRecycler.setVisibility(View.VISIBLE);

                itemRowHolder.tv_titleRecycler.setText(homeDataModel.getTitleList().get(position));
            } else {
                itemRowHolder.tv_titleRecycler.setVisibility(View.GONE);
            }


            LatestProductsAdapter latestProductsAdapter = new LatestProductsAdapter(context, homeDataModel.getLatestList());
            itemRowHolder.recycler_view_list.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            ((LinearLayoutManager) layoutManager).setInitialPrefetchItemCount(1);
            itemRowHolder.recycler_view_list.setLayoutManager(layoutManager);
            itemRowHolder.recycler_view_list.setAdapter(latestProductsAdapter);

            itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);
            latestProductsAdapter.notifyDataSetChanged();
        }
        else if (position == 2) {

            if (homeDataModel.getAllProductList().size() > 0) {
                itemRowHolder.tv_titleRecycler.setVisibility(View.VISIBLE);

                itemRowHolder.tv_titleRecycler.setText(homeDataModel.getTitleList().get(position));
            } else {
                itemRowHolder.tv_titleRecycler.setVisibility(View.GONE);
            }


            AllProductsAdapter allProductsAdapter = new AllProductsAdapter(context, homeDataModel.getAllProductList());
            itemRowHolder.recycler_view_list.setHasFixedSize(true);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false);

            itemRowHolder.recycler_view_list.setLayoutManager(gridLayoutManager);
            itemRowHolder.recycler_view_list.setAdapter(allProductsAdapter);

            itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);
            allProductsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {

        return (null != homeDataModel.getTitleList() ? homeDataModel.getTitleList().size() : 0);
    }
}