package com.semicolons.masco.pk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.dataModels.DataItem;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.CategoryVH> {


    private Context context;
    private int lastPosition = 0;
    private List<DataItem> topSellingList;
    private OnItemClickListener mlistener;


    public MainCategoryAdapter(Context context, List<DataItem> topSellingList) {
        this.context = context;
        this.topSellingList = topSellingList;
    }


    @NonNull
    @Override
    public CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Called by the layoutManger to attach the view to adapter
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.temp_main_cat_list, parent, false);
        CategoryVH latestJobVH = new CategoryVH(view);
        return latestJobVH;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVH holder, int position) {

        DataItem imagesListDM = topSellingList.get(position);
        String s = imagesListDM.getCategoryName();
        int id = imagesListDM.getCategoryID();
        holder.tv_date.setText(s);


        holder.linear_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lastPosition = position;
                notifyDataSetChanged();
                mlistener.selectedCategory(s, position, id);
            }
        });

        if (lastPosition == position) {
            holder.linear_date.setBackgroundResource(R.drawable.bg_rectangle_border);
            mlistener.selectedCategory(s, position, id);

        } else {

            holder.linear_date.setBackgroundResource(R.drawable.bg_rectangle_white);
        }

    }

    @Override
    public int getItemCount() {
        return topSellingList.size();
    }
//    public void LoadNewData(List<String> stringsYear)
//    {
//        this.dateList = stringsYear;
//        notifyDataSetChanged();
//    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mlistener = listener;
    }

    public interface OnItemClickListener {
        void selectedCategory(String cat, int position, int id);

    }

    public static class CategoryVH extends RecyclerView.ViewHolder {

        LinearLayout linear_date;
        TextView tv_date;

        public CategoryVH(View view) {
            super(view);

            linear_date = view.findViewById(R.id.linear_date);
            tv_date = view.findViewById(R.id.tv_date);


        }

    }

}
