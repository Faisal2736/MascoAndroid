package com.semicolons.masco.pk.uiActivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.adapters.OrdersAdapter;
import com.semicolons.masco.pk.dataModels.Order;
import com.semicolons.masco.pk.dataModels.OrdersResponse;
import com.semicolons.masco.pk.viewModels.OrderViewModel;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrdersAdapter ordersAdapter;
    private OrderViewModel orderViewModel;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog progressDialog;
    private TextView no_order_textView;
    private SharedPreferences sharedPreferences;
    private boolean isLogin;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
    }

    private void initViews() {
        orderViewModel=new ViewModelProvider(this).get(OrderViewModel.class);
        recyclerView=findViewById(R.id.rvOrdersList);
        no_order_textView=findViewById(R.id.noOrderTextView);
        sharedPreferences = getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);
        isLogin=sharedPreferences.getBoolean(Constants.USER_IS_LOGIN,false);

        if(isLogin){
            userId=sharedPreferences.getInt(Constants.USER_ID,0);
            OrderList(userId);
        }else {
            no_order_textView.setText("Not available");
            no_order_textView.setVisibility(View.VISIBLE);
        }

    }

    public void OrderList(int user_id){

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching Orders...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        try {
            orderViewModel.getAllOrders(user_id).observe(this, new Observer<OrdersResponse>() {
                @Override
                public void onChanged(OrdersResponse ordersResponse) {
                    progressDialog.dismiss();
                    if (ordersResponse != null) {
                        progressDialog.dismiss();
                        if (ordersResponse.getOrderDetail() != null) {
                            List<Order> resultItems = ordersResponse.getOrderDetail();
                            progressDialog.dismiss();
                            if (resultItems.size() > 0) {
                                progressDialog.dismiss();

                                fillOrdersList(resultItems);
                            } else {
                                no_order_textView.setText("Not available");
                                progressDialog.dismiss();
                                no_order_textView.setVisibility(View.VISIBLE);

                            }
                        }
                    } else {
                        progressDialog.dismiss();
                    }
                }
            });
        }catch (Exception ex){
            String s=ex.getMessage();
        }


    }

    private void fillOrdersList(List<Order> orderList) {

        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ordersAdapter=new OrdersAdapter(this,orderList);
        recyclerView.setAdapter(ordersAdapter);
        ordersAdapter.notifyDataSetChanged();
    }
}
