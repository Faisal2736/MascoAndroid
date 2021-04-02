package com.semicolons.masco.pk.uiActivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.adapters.OrderDetailProductsAdapter;
import com.semicolons.masco.pk.dataModels.OrderDetailItem;
import com.semicolons.masco.pk.dataModels.OrderDetailResponse;
import com.semicolons.masco.pk.dataModels.Product;
import com.semicolons.masco.pk.viewModels.OrderViewModel;

public class OrderDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderDetailProductsAdapter orderDetailProductsAdapter;
    private ArrayList<Product> productArrayList;
    private OrderViewModel orderViewModel;
    private SharedPreferences sharedPreferences;
    private int userId;
    private TextView orderNumTextView;
    private TextView orderTotalTextView;
    private TextView orderDateTextView;
    private TextView orderDetailMyClubTextView;
    private TextView tv_discount;
    private TextView orderSubTotalTextView;
    private TextView orderDeliveryFeeTextView;
    private TextView orderGrandTotalTextView;
    private TextView textView22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
    }

    private void initViews() {

        Intent intent = getIntent();
        int orderId = intent.getIntExtra("ORDER_ID", 4853);
        String orderDate = intent.getStringExtra("DATE");
        int orderNumber = intent.getIntExtra("ORDER_NUMBER", 0);

        orderNumTextView = findViewById(R.id.orderDetailOrderNumTextView);
        orderTotalTextView = findViewById(R.id.orderDetailTotalTextView);
        orderDateTextView = findViewById(R.id.orderDetailDateTextView);
        orderDetailMyClubTextView = findViewById(R.id.orderDetailMyClubTextView);

        tv_discount = findViewById(R.id.tv_discount);
        textView22 = findViewById(R.id.textView22);

        orderSubTotalTextView = findViewById(R.id.orderSubTotalTextView);

        orderGrandTotalTextView = findViewById(R.id.orderGrandTotalTextView);

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        sharedPreferences = getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(Constants.USER_ID, 0);

        orderViewModel.getOrderDetail(orderId).observe(this, new Observer<OrderDetailResponse>() {
            @Override
            public void onChanged(OrderDetailResponse orderDetailResponse) {
                if (orderDetailResponse != null) {
                    if (orderDetailResponse.getOrderDetail() != null) {
                        if (orderDetailResponse.getOrderDetail().size() > 0) {

                            orderNumTextView.setText(orderNumber + "");
                            orderTotalTextView.setText(orderDetailResponse.getOrderTotal() + "");
                            orderDateTextView.setText(orderDate + "");
//                            orderSubTotalTextView.setText(orderDetailResponse.getOrderTotal() + "");
                            String a = orderDetailResponse.getOrderTotal();
                            a = a.replace("₨", "");

                            String b = orderDetailResponse.getDevelory();
                            b = b.replace("₨", "");

                            int beforeDeliveryPrice = Integer.parseInt(a) - Integer.parseInt(b);
                            orderSubTotalTextView.setText(String.valueOf(beforeDeliveryPrice));
//                            tv_discount.setText(orderDetailResponse.getDiscount());
                            textView22.setText(orderDetailResponse.getDevelory());
//
//                            orderDetailMyClubTextView.setText(String.valueOf(orderDetailResponse.getPoint()));
                            orderGrandTotalTextView.setText(a);


                            prepareProductsList(orderDetailResponse.getOrderDetail());

                        }
                    }
                }
            }
        });

    }

    private void prepareProductsList(List<OrderDetailItem> orderDetailItems) {
        recyclerView = findViewById(R.id.rvOrderDetailProducts);
        orderDetailProductsAdapter = new OrderDetailProductsAdapter(this, orderDetailItems);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(orderDetailProductsAdapter);
    }


}
