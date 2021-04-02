package com.semicolons.masco.pk.uiActivities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.adapters.ProductsListAdapter;
import com.semicolons.masco.pk.dataModels.CartDataTable;
import com.semicolons.masco.pk.dataModels.CartResponse;
import com.semicolons.masco.pk.dataModels.DeleteFavResponse;
import com.semicolons.masco.pk.dataModels.FavDataTable;
import com.semicolons.masco.pk.dataModels.Product;
import com.semicolons.masco.pk.dataModels.TopSellingResponse;
import com.semicolons.masco.pk.itemDecorator.GridSpacingItemDecoration;
import com.semicolons.masco.pk.viewModels.CartViewModel;
import com.semicolons.masco.pk.viewModels.ProductListFragmentViewModel;
import com.semicolons.masco.pk.viewModels.WishViewModel;

public class WishListActivity extends AppCompatActivity implements ProductsListAdapter.onItemClickListenerAdd {

    private RecyclerView recyclerView;
    private ProductsListAdapter productsListAdapter;
    private ProductListFragmentViewModel categoryFragmentViewModel;
    private WishViewModel wishViewModel;
    private SharedPreferences sharedPreferences;
    private CartViewModel cartViewModel;
    private boolean isLogin;
    private int userId;
    private List<CartDataTable> cartDataTableList;
    private ArrayList<FavDataTable> favDataTableArrayList;
    private ArrayList<Product> productArrayList;
    private ProgressDialog progressDialog;
    private Context context;
    private View viewParent;
    private Product product;
    private int pos;


    ImageView img_search;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Wish List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();


        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WishListActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {

        if(isLogin){
            userId=sharedPreferences.getInt(Constants.USER_ID,0);

            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Fetching Favourites...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            cartViewModel.getCartDataTableLiveData(userId).observe(this, new Observer<List<CartDataTable>>() {
                @Override
                public void onChanged(List<CartDataTable> cartDataTables) {
                    if(cartDataTables!=null){
                        cartDataTableList=cartDataTables;
                    }
                }
            });

            categoryFragmentViewModel.getAllFavourites(userId).observe(this, new Observer<List<FavDataTable>>() {
                @Override
                public void onChanged(List<FavDataTable> favDataTableList) {
                    if(favDataTableList!=null){
                        favDataTableArrayList = (ArrayList<FavDataTable>) favDataTableList;
                    }
                }
            });

            wishViewModel.getFavouritesProductListByUserId(userId).observe(this, new Observer<TopSellingResponse>() {
                @Override
                public void onChanged(TopSellingResponse topSellingResponse) {
                    progressDialog.dismiss();
                    if(topSellingResponse!=null){
                        progressDialog.dismiss();
                        if(topSellingResponse.getData()!=null){
                            productArrayList=topSellingResponse.getData();
                            progressDialog.dismiss();
                            if(topSellingResponse.getData().size()>0){
                                progressDialog.dismiss();

                                prepareListData(productArrayList);
                            } else {
                                showResponseDialogFailed("Not Found", "No items added to favourite");
                            }
                        } else {
                            showResponseDialogFailed("Not Found", "No items added to favourite");
                        }
                    }

                }
            });

        }

        super.onResume();
    }

    private void initViews() {

        context = this;
        recyclerView = findViewById(R.id.rvWishList);
        img_search = findViewById(R.id.img_search);

        sharedPreferences = getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean(Constants.USER_IS_LOGIN, false);
        categoryFragmentViewModel = new ViewModelProvider(this).get(ProductListFragmentViewModel.class);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        wishViewModel = new ViewModelProvider(this).get(WishViewModel.class);

        //   tabLayout = findViewById(R.id.tabLayout);


    }

    private void prepareListData(List<Product> resultItems) {

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        productsListAdapter = new ProductsListAdapter(this, resultItems,this,cartDataTableList,favDataTableArrayList);
        recyclerView.setAdapter(productsListAdapter);
        productsListAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view,Product product1) {

        if(isLogin){
            int userId=sharedPreferences.getInt(Constants.USER_ID,0);
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            viewParent= (View) view.getParent();
            product=product1;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                viewParent.setForeground(context.getDrawable(R.drawable.ic_cart_item));
            }else {
                viewParent.setAlpha(0.5f);
            }

            progressDialog.dismiss();
            int quantity=1;
            cartViewModel.addProductToCart(userId,product.getProduct_id(),quantity).observe(this, new Observer<CartResponse>() {
                @Override
                public void onChanged(CartResponse cartResponse) {
                    progressDialog.dismiss();
                    if(cartResponse!=null){
                        progressDialog.dismiss();
                        if(cartResponse.getMessage().equals("Product added successfully")){
                            progressDialog.dismiss();

                            CartDataTable cartDataTable=new CartDataTable();
                            cartDataTable.setProductId(product.getProduct_id());
                            cartDataTable.setProductQuantity(1);
                            cartDataTable.setUserId(userId);

                            cartViewModel.insertCart(cartDataTable);
                            invalidateOptionsMenu();

                        }else {
                            Toast.makeText(context,cartResponse.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    public void onFavClick(View view, Product productFav, int position) {

        if (isLogin) {
            deleteDialog(view, productFav, position);
        }

    }

//    @Override
//    public void updateCart(int productId, int quantity, int position) {
//
//    }

    @Override
    public void updateCart(int productId, int quantity, int position, View view) {

    }


    public void deleteDialog(View view, Product productFav, int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete")
                .setMessage("Are you sure you want to remove item from favourite?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int userId=sharedPreferences.getInt(Constants.USER_ID,0);
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Please Wait...");
                        progressDialog.setCancelable(false);
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        product=productFav;
                        pos=position;
                        ImageView imageView= (ImageView) view;
                        //int resourceId=getResources().getIdentifier("com.budget.budgetmanager:drawable/ic_baseline_star_filled_24","drawable",getPackageName());
                        imageView.setImageResource(R.drawable.ic_star_border_black);

                        wishViewModel.deleteFavourite(product.getProduct_id(),userId).observe((LifecycleOwner) context, new Observer<DeleteFavResponse>() {
                            @Override
                            public void onChanged(DeleteFavResponse deleteFavResponse) {
                                progressDialog.dismiss();
                                if(deleteFavResponse!=null){
                                    progressDialog.dismiss();
                                    if(deleteFavResponse.getData()!=null){
                                        progressDialog.dismiss();
                                        if(deleteFavResponse.getData().equals("Remove from favorite successfully")){
                                            progressDialog.dismiss();
                                            Toast.makeText(context,deleteFavResponse.getData(),Toast.LENGTH_SHORT).show();

                                            FavDataTable favDataTable=wishViewModel.getFavourite(product.getProduct_id(),userId);

                                            wishViewModel.deleteFavouriteFromDB(favDataTable);

                                            productArrayList.remove(pos);
                                            productsListAdapter.notifyItemRemoved(pos);
                                        }else {
                                            progressDialog.dismiss();
                                            Toast.makeText(context,deleteFavResponse.getData(),Toast.LENGTH_SHORT).show();
                                        }

                                    }else {
                                        progressDialog.dismiss();
                                        Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show();
                                    }

                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();

    }

    private void showResponseDialog(String title, String msg) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showResponseDialogFailed(String title, String msg) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //do whatever
                onBackPressed();
                return true;
            case R.id.action_cart:
                Intent intent = new Intent(WishListActivity.this, CartActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}