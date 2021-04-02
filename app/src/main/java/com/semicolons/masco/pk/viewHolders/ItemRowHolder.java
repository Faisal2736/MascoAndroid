package com.semicolons.masco.pk.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.semicolons.masco.pk.R;


public class ItemRowHolder extends RecyclerView.ViewHolder {

    public TextView tv_titleRecycler;

    public RecyclerView recycler_view_list;


    public ItemRowHolder(View view) {
        super(view);


        recycler_view_list = view.findViewById(R.id.recycler_view_list);
        tv_titleRecycler = view.findViewById(R.id.tv_titleRecy);


    }

}

