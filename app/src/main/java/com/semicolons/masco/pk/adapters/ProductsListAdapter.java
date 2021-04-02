package com.semicolons.masco.pk.adapters;

import android.app.ProgressDialog;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
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
import com.semicolons.masco.pk.dataModels.FavDataTable;
import com.semicolons.masco.pk.dataModels.Product;
import com.semicolons.masco.pk.uiActivities.LoginActivity;
import com.semicolons.masco.pk.uiActivities.ProductDetailActivity;
import com.semicolons.masco.pk.viewHolders.LoadingVH;
import com.semicolons.masco.pk.viewModels.CartViewModel;
import com.semicolons.masco.pk.viewModels.HomeFragmentViewModel;


public class ProductsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static List<Product> allProductsList = new ArrayList<>();
    private CartViewModel cartViewModel;
    private ProgressDialog progressDialog;
    private HomeFragmentViewModel homeFragmentViewModel;
    private static onItemClickListenerAdd onItemClickListenerAdd;
    private List<CartDataTable> cartDataTables;
    private List<FavDataTable> favDataTableList;

    SharedPreferences sharedPreferences;
    private boolean isLogin;
    private int userId;
    String catId;
    private Context context;

    private static final int item = 0;
    private static final int loading = 1;
    private boolean isLoadingAdded = false;


    public ProductsListAdapter(Context context, List<Product> allProductsList, onItemClickListenerAdd onItemClickListenerAdd, List<CartDataTable> cartDataTables, List<FavDataTable> favDataTableList) {
        this.allProductsList = allProductsList;
        this.context = context;
        this.onItemClickListenerAdd = onItemClickListenerAdd;
        cartViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(CartViewModel.class);
        homeFragmentViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(HomeFragmentViewModel.class);
        this.cartDataTables = cartDataTables;
        this.favDataTableList=favDataTableList;
    }

    public ProductsListAdapter(Context context, List<Product> allProductsList, onItemClickListenerAdd onItemClickListenerAdd, List<CartDataTable> cartDataTables, List<FavDataTable> favDataTableList,String catid) {
        this.allProductsList = allProductsList;
        this.context = context;
        this.onItemClickListenerAdd = onItemClickListenerAdd;
        cartViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(CartViewModel.class);
        homeFragmentViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(HomeFragmentViewModel.class);
        this.cartDataTables = cartDataTables;
        this.favDataTableList=favDataTableList;
        this.catId = catid;
    }

/*    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.temp_product_item, viewGroup, false);
        SingleItemRowHolder categoriesListVH = new SingleItemRowHolder(view);
        return categoriesListVH;
    }*/

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {

            case item:
                holder = getViewHolder(parent, inflater);
                break;

            case loading:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                holder = new LoadingVH(v2);
        }
        return holder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {

        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.temp_product_item, parent, false);
        viewHolder = new ProductsListAdapter.SingleItemRowHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case item:
                SingleItemRowHolder viewHolder = (SingleItemRowHolder) holder;

                Product product = allProductsList.get(position);


                RequestOptions options = new RequestOptions()
                        .centerInside()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .skipMemoryCache(true)
                        .priority(Priority.HIGH);
                Glide
                        .with(context)
                        .load(allProductsList.get(position).getImage_name())
                        .apply(options)
                        .into(viewHolder.image);

                viewHolder.name.setText(product.getProduct_title());
                viewHolder.price.setText(product.getPrice());


                //  viewHolder.quanTv.setText(String.valueOf(cart.getProductQuantity()));

                Log.e("OnBind()", "called");


                viewHolder.linear_label.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, ProductDetailActivity.class);
                        intent.putExtra(Constants.PRODUCT_OBJECT, product);
                        intent.putExtra(Constants.SUB_CATEGORY_ID, catId);
                        context.startActivity(intent);

                    }
                });

                if (cartDataTables != null && cartDataTables.size() > 0) {
                    for (int i = 0; i < cartDataTables.size(); i++) {
                        if (cartDataTables.get(i).getProductId() == product.getProduct_id()) {

                            viewHolder.addCartButton.setVisibility(View.GONE);
                            viewHolder.cartLayout.setVisibility(View.VISIBLE);
                            viewHolder.quanTv.setText(String.valueOf(cartDataTables.get(i).getProductQuantity()));

                        }
                    }
                }

                if (favDataTableList != null && favDataTableList.size() > 0) {
                    for (int i = 0; i < favDataTableList.size(); i++) {
                        if (favDataTableList.get(i).getProductId() == product.getProduct_id()) {
                            viewHolder.favImageView.setImageResource(R.drawable.ic_baseline_star_filled_24);
                        }
                    }
                }

                break;
            case loading:
        }


    }

    @Override
    public int getItemViewType(int position) {
        return (position == allProductsList.size() - 1 && isLoadingAdded) ? loading : item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private Product getMRObject(int position) {
        return allProductsList.get(position);
    }

    @Override
    public int getItemCount() {
        return (null != allProductsList ? allProductsList.size() : 0);
    }
    
    
      /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Product mc) {
        allProductsList.add(mc);
        notifyItemInserted(allProductsList.size() - 1);
    }

    public void addAll(List<Product> mcList,List<CartDataTable> cartDataTables,List<FavDataTable> favDataTableList) {
        for (Product mc : mcList) {
            add(mc);
        }
        this.cartDataTables = cartDataTables;
        this.favDataTableList=favDataTableList;
    }

    private void remove(Product city) {
        int position = allProductsList.indexOf(city);
        if (position > -1) {
            allProductsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getMRObject(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Product());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = allProductsList.size() - 1;
        Product item = getMRObject(position);

        if (item != null) {
            allProductsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void setList(List<CartDataTable> cartDataTables) {

        this.cartDataTables = cartDataTables;
    }

    public interface onItemClickListenerAdd {
        void onClick(View view, Product product);

        void onFavClick(View view, Product product, int position);

        void updateCart(int productId, int quantity, int position, View view);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView price;
        LinearLayout linear_label;
        Button addCartButton;
        ImageView favImageView;

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
            addCartButton = view.findViewById(R.id.addToCartButton);
            favImageView = view.findViewById(R.id.favImageView);
//            favImageView.setOnClickListener(this);
//            addCartButton.setOnClickListener(this);

            cartLayout = view.findViewById(R.id.rel_prodCart_home);

            addProdButton = view.findViewById(R.id.prodAddButtonHome);
            minusProdButton = view.findViewById(R.id.prodMinusButtonHome);
            quanTv = view.findViewById(R.id.productQuanTextViewHome);


            sharedPreferences = context.getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);
            isLogin = sharedPreferences.getBoolean(Constants.USER_IS_LOGIN, false);
            userId = sharedPreferences.getInt(Constants.USER_ID, 0);

            favImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isLogin) {
                        int postion = getAdapterPosition();
                        if (onItemClickListenerAdd != null && postion != RecyclerView.NO_POSITION) {

                            onItemClickListenerAdd.onFavClick(v, ProductsListAdapter.allProductsList.get(getAdapterPosition()), getAdapterPosition());
                        }

                    } else {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                }
            });

            addCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isLogin) {
                        int postion = getAdapterPosition();
                        if (onItemClickListenerAdd != null && postion != RecyclerView.NO_POSITION) {
                            addCartButton.setVisibility(View.GONE);
                            cartLayout.setVisibility(View.VISIBLE);
                            quanTv.setText("1");
                            onItemClickListenerAdd.onClick(v, allProductsList.get(postion));
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

                        pID2 = allProductsList.get(getAdapterPosition()).getProduct_id();

                        if (Utilities.p[i] == pID2) {
                            Utilities.q[i]++;
                            quanTv.setText(String.valueOf(Utilities.q[i]));
                            onItemClickListenerAdd.updateCart(Utilities.p[i], Utilities.q[i], getAdapterPosition(), v);
                        } else {
                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            });


            minusProdButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pID2;

                    for (int i = 0; i < cartDataTables.size(); i++) {

                        pID2 = allProductsList.get(getAdapterPosition()).getProduct_id();

                        if (Utilities.p[i] == pID2) {

                            if (Utilities.q[i] < 1) {
                                cartLayout.setVisibility(View.GONE);
                                addCartButton.setVisibility(View.VISIBLE);
                            } else {
                                Utilities.q[i]--;
                                if (Utilities.q[i] < 1) {
                                    cartLayout.setVisibility(View.GONE);
                                    addCartButton.setVisibility(View.VISIBLE);
                                }
                                quanTv.setText(String.valueOf(Utilities.q[i]));
                            }
                            onItemClickListenerAdd.updateCart(Utilities.p[i], Utilities.q[i], getAdapterPosition(), v);
                        } else {
                            Toast.makeText(context, "no", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            });


        }

//        @Override
//        public void onClick(View v) {
//
//            if (v.getId() == R.id.addToCartButton) {
//
//                int position = getAdapterPosition();
//                if (onItemClickListenerAdd != null && position != RecyclerView.NO_POSITION) {
//
//                    addCartButton.setVisibility(View.GONE);
//                    cartLayout.setVisibility(View.VISIBLE);
//
//                    //   colon.semi.com.dealMall.adapters.ProductsListAdapter.
//                    onItemClickListenerAdd.onClick(v, allProductsList.get(position));
//                }
//
//            } else if (v.getId() == R.id.favImageView) {
//                onItemClickListenerAdd.onFavClick(v, ProductsListAdapter.allProductsList.get(this.getAdapterPosition()), this.getAdapterPosition());
//            }
//
//
//        }


    }


}


