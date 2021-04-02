package com.semicolons.masco.pk.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.dataModels.Order;
import com.semicolons.masco.pk.uiActivities.OrderDetailActivity;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.Holder> {

    private Context context;
    private List<Order> orderList;

    public OrdersAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.order_list_items,parent,false);
        return  new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Order order=orderList.get(position);
        holder.orderTotalAmountTextView.setText("PKR "+order.getOrderTotal());
   /*     holder.orderDateTextView.setText(order.getOrderDate().trim().split(" ")[0]);
        holder.orderTimeTextView.setText(order.getOrderDate().trim().split(" ")[1]);*/
        holder.orderNumberTextView.setText(String.valueOf(order.getOrderId()));

        holder.viewOrderDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OrderDetailActivity.class);
                intent.putExtra("ORDER_ID",order.getOrderId());
                intent.putExtra("DATE",order.getOrderDate());
                intent.putExtra("ORDER_NUMBER",order.getOrderId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        TextView orderTotalAmountTextView;
        TextView orderDateTextView;
        TextView orderTimeTextView;
        TextView orderNumberTextView;
        Button viewOrderDetailButton;

        public Holder(@NonNull View itemView) {
            super(itemView);

            orderTotalAmountTextView=itemView.findViewById(R.id.orderTotalAmount);
            orderDateTextView=itemView.findViewById(R.id.orderDateTextView);
            orderTimeTextView=itemView.findViewById(R.id.orderTimeTextView);
            orderNumberTextView=itemView.findViewById(R.id.orderNumberTextView);
            viewOrderDetailButton=itemView.findViewById(R.id.viewOrderDetailButton);
        }


    }
}
