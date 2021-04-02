package com.semicolons.masco.pk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.dataModels.OrderDetailItem;

public class OrderDetailProductsAdapter extends RecyclerView.Adapter<OrderDetailProductsAdapter.Holder> {

    private Context context;
    private List<OrderDetailItem> orderDetailItems;

    public OrderDetailProductsAdapter(Context context, List<OrderDetailItem> orderDetailItems) {
        this.context = context;
        this.orderDetailItems = orderDetailItems;
    }

    @NonNull
    @Override
    public OrderDetailProductsAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.order_products_items,parent,false);
        return  new OrderDetailProductsAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailProductsAdapter.Holder holder, int position) {
        OrderDetailItem orderDetailItem=orderDetailItems.get(position);
        holder.orderDetailProdName.setText(orderDetailItem.getProductTitle());
        holder.orderDetailQuantity.setText(String.valueOf(orderDetailItem.getQuantity()));
        holder.orderDetailPriceMultiplyNumbers.setText(orderDetailItem.getQuantity() + " x Price");
        holder.orderDetailProductPrice.setText(orderDetailItem.getTotal());

    }

    @Override
    public int getItemCount() {
        return orderDetailItems.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        TextView orderDetailProdName;
        TextView orderDetailQuantity;
        TextView orderDetailPriceMultiplyNumbers;
        TextView orderDetailProductPrice;

        public Holder(@NonNull View itemView) {
            super(itemView);

            orderDetailProdName=itemView.findViewById(R.id.orderDetailProductNameTextView);
            orderDetailQuantity=itemView.findViewById(R.id.orderDetailProductQuantityTextView);
            orderDetailPriceMultiplyNumbers=itemView.findViewById(R.id.orderDetailProductMultiplyTextView);
            orderDetailProductPrice=itemView.findViewById(R.id.orderDetailProductPriceTextView);
        }


    }
}
