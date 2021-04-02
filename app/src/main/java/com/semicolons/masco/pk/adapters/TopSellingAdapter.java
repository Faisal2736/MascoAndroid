package com.semicolons.masco.pk.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.Utils.Utilities;
import com.semicolons.masco.pk.dataModels.CartDataTable;
import com.semicolons.masco.pk.dataModels.Product;
import com.semicolons.masco.pk.uiActivities.LoginActivity;
import com.semicolons.masco.pk.uiActivities.ProductDetailActivity;

public class TopSellingAdapter extends RecyclerView.Adapter<TopSellingAdapter.SingleItemRowHolder> {

    private static OnItemClickListener mlistener;
    private ArrayList<Product> topSellingList;
    private Context context;
    Product product;
    SharedPreferences sharedPreferences;
    //  private List<CartProduct> cartDataTables;
    private List<CartDataTable> cartDataTables;
    private boolean isLogin;
    private int userId;


    public TopSellingAdapter(ArrayList<Product> topSellingList, List<CartDataTable> cartDataTableList, Context context) {
        this.topSellingList = topSellingList;
        this.cartDataTables = cartDataTableList;
        this.context = context;
    }
    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.temp_top_selling_products, viewGroup, false);
        SingleItemRowHolder categoriesListVH = new SingleItemRowHolder(view);
        return categoriesListVH;
    }
    @Override
    public void onBindViewHolder(@NonNull SingleItemRowHolder holder, int position) {

        SingleItemRowHolder viewHolder = (SingleItemRowHolder) holder;
        product = topSellingList.get(position);


        RequestOptions options = new RequestOptions()
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .priority(Priority.HIGH);
        Glide
                .with(context)
                .load(topSellingList.get(position).getImage_name())
                .apply(options)
                .into(viewHolder.image);

        viewHolder.name.setText(topSellingList.get(position).getProduct_title());
        viewHolder.price.setText(topSellingList.get(position).getPrice());

        viewHolder.linear_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra(Constants.PRODUCT_OBJECT, product);
                intent.putExtra(Constants.SUB_CATEGORY_ID, "60");
                context.startActivity(intent);

            }
        });

        Log.d("bind", "call");

        if (cartDataTables != null && cartDataTables.size() > 0) {
            for (int i = 0; i < cartDataTables.size(); i++) {
                if (cartDataTables.get(i).getProductId() == product.getProduct_id()) {

                    viewHolder.addtocart.setVisibility(View.GONE);
                    viewHolder.cartLayout.setVisibility(View.VISIBLE);
                    // viewHolder.quanTv.setText(String.valueOf(cartDataTables.get(i).getQuantity()));
                    viewHolder.quanTv.setText(String.valueOf(cartDataTables.get(i).getProductQuantity()));

                }
            }
        }


    }





    @Override
    public int getItemCount() {
        return (null != topSellingList ? topSellingList.size() : 0);
    }

    public void setList(List<CartDataTable> cartDataTables) {

        this.cartDataTables = cartDataTables;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mlistener = listener;
    }

    public interface OnItemClickListener {
        void onAddtoCartClick(View view, Product product);
        void updateCartData(int productId, int quantity, int position, View view);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView price;
        LinearLayout linear_label;

        Button addtocart;
        Button addProdButton;
        Button minusProdButton;
        TextView quanTv;
        LinearLayout cartLayout;

        public SingleItemRowHolder(View view) {
            super(view);

            image = view.findViewById(R.id.image);
            name = view.findViewById(R.id.name);
            price = view.findViewById(R.id.price);
            linear_label = view.findViewById(R.id.linear_label);

            addtocart = view.findViewById(R.id.addToCartButtonHome);
            cartLayout = view.findViewById(R.id.rel_prodCart_home);
            addProdButton = view.findViewById(R.id.prodAddButtonHome);
            quanTv = view.findViewById(R.id.productQuanTextViewHome);
            minusProdButton = view.findViewById(R.id.prodMinusButtonHome);

            sharedPreferences = context.getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);
            isLogin = sharedPreferences.getBoolean(Constants.USER_IS_LOGIN, false);
            userId = sharedPreferences.getInt(Constants.USER_ID, 0);


            addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isLogin) {
                        int postion = getAdapterPosition();
                        if (mlistener != null && postion != RecyclerView.NO_POSITION) {
                            addtocart.setVisibility(View.GONE);
                            cartLayout.setVisibility(View.VISIBLE);
                            quanTv.setText("1");
                            mlistener.onAddtoCartClick(v, topSellingList.get(postion));
                        }

                    } else {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                }
            });


            addProdButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pID2;

                    for (int i = 0; i < cartDataTables.size(); i++) {

                        pID2 = topSellingList.get(getAdapterPosition()).getProduct_id();

                        if (Utilities.p[i] == pID2) {
                            Utilities.q[i]++;
                            quanTv.setText(String.valueOf(Utilities.q[i]));
                            mlistener.updateCartData(Utilities.p[i], Utilities.q[i], getAdapterPosition(), v);
                        } else {
                            Toast.makeText(context, "no", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            });


            minusProdButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pID2;

                    for (int i = 0; i < cartDataTables.size(); i++) {

                        pID2 = topSellingList.get(getAdapterPosition()).getProduct_id();

                        if (Utilities.p[i] == pID2) {

                            if (Utilities.q[i] < 1) {
                                cartLayout.setVisibility(View.GONE);
                                addtocart.setVisibility(View.VISIBLE);
                            } else {
                                Utilities.q[i]--;
                                if (Utilities.q[i] < 1) {
                                    cartLayout.setVisibility(View.GONE);
                                    addtocart.setVisibility(View.VISIBLE);
                                }
                                quanTv.setText(String.valueOf(Utilities.q[i]));
                            }
                            mlistener.updateCartData(Utilities.p[i], Utilities.q[i], getAdapterPosition(), v);
                        } else {
                            Toast.makeText(context, "no", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            });


        }

    }

}
