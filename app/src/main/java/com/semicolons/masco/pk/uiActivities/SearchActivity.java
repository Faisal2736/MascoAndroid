package com.semicolons.masco.pk.uiActivities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.AppClass;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.adapters.ProductsListAdapter;
import com.semicolons.masco.pk.dataModels.AddFavResponse;
import com.semicolons.masco.pk.dataModels.CartDataTable;
import com.semicolons.masco.pk.dataModels.CartResponse;
import com.semicolons.masco.pk.dataModels.DataItem;
import com.semicolons.masco.pk.dataModels.DeleteFavResponse;
import com.semicolons.masco.pk.dataModels.FavDataTable;
import com.semicolons.masco.pk.dataModels.Product;
import com.semicolons.masco.pk.dataModels.TopSellingResponse;
import com.semicolons.masco.pk.dataModels.UpdateCartResponse;
import com.semicolons.masco.pk.itemDecorator.GridSpacingItemDecoration;
import com.semicolons.masco.pk.retrofit.ApiClient;
import com.semicolons.masco.pk.retrofit.ApiInterface;
import com.semicolons.masco.pk.viewModels.CartViewModel;
import com.semicolons.masco.pk.viewModels.HomeFragmentViewModel;
import com.semicolons.masco.pk.viewModels.ProductListFragmentViewModel;
import com.semicolons.masco.pk.viewModels.WishViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements ProductsListAdapter.onItemClickListenerAdd {

    EditText etSearch;
    ProgressBar progressBar;
    Call<TopSellingResponse> searchCall;
    private RecyclerView recy_categories;
    private ProductsListAdapter taskListAdap;
    private RecyclerView.LayoutManager layoutManager;
    private ProductListFragmentViewModel categoryFragmentViewModel;
    private TextView tv_no_booking;
    private HomeFragmentViewModel homeFragmentViewModel;
    private CartViewModel cartViewModel;
    private Context context;
    private View viewParent;
    private Product product;
    private SharedPreferences sharedPreferences;
    private boolean isLogin;
    private DataItem dataItem;
    private List<CartDataTable> cartDataTableList;
    private ArrayList<FavDataTable> favDataTableArrayList;
    private WishViewModel wishViewModel;
    private int cartCounter = 0;
    private int userId;
    private int pos;
    private int pQun;
    private int pId;
    private int pos2;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Products");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //do whatever
                onBackPressed();
                return true;
            case R.id.action_cart:
                Intent intent = new Intent(this, CartActivity.class);
                intent.putExtra("IS_CART_FRAGMENT", true);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        invalidateOptionsMenu();


        isLogin = sharedPreferences.getBoolean(Constants.USER_IS_LOGIN, false);
        if (isLogin) {
            cartViewModel.getCartCount(userId).observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if (integer != null) {
                        cartCounter = integer;
                    } else {
                        cartCounter = 0;
                    }
                    invalidateOptionsMenu();
                }
            });

            cartViewModel.getCartDataTableLiveData(userId).observe(this, new Observer<List<CartDataTable>>() {
                @Override
                public void onChanged(List<CartDataTable> cartDataTables) {
                    if (cartDataTables != null) {
                        cartDataTableList = cartDataTables;
                    }
                }
            });

            categoryFragmentViewModel.getAllFavourites(userId).observe(this, new Observer<List<FavDataTable>>() {
                @Override
                public void onChanged(List<FavDataTable> favDataTableList) {
                    if (favDataTableList != null) {
                        favDataTableArrayList = (ArrayList<FavDataTable>) favDataTableList;
                    }
                }
            });
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        prepareMenu(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.clear();

        prepareMenu(menu);

        return super.onPrepareOptionsMenu(menu);
    }


    public void prepareMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);


        MenuItem menuItem = menu.findItem(R.id.action_cart);
        menu.findItem(R.id.action_search).setVisible(false);

        if (cartCounter != 0) {
            menuItem.setIcon(homeFragmentViewModel.getDrawableForCounter(Integer.valueOf(cartCounter), R.drawable.ic_baseline_shopping_cart_24));
        } else {
            menuItem.setIcon(homeFragmentViewModel.getDrawableForCounter(0, R.drawable.ic_baseline_shopping_cart_24));
        }
    }

    private void initViews() {
        context = this;
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        categoryFragmentViewModel = new ViewModelProvider(this).get(ProductListFragmentViewModel.class);
        recy_categories = findViewById(R.id.recy_categories);
        tv_no_booking = findViewById(R.id.tv_no_booking);
        sharedPreferences = getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean(Constants.USER_IS_LOGIN, false);
        homeFragmentViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        wishViewModel = new ViewModelProvider(this).get(WishViewModel.class);

        etSearch = findViewById(R.id.et_search);
        progressBar = findViewById(R.id.progressBar);


        if (isLogin) {
            userId = sharedPreferences.getInt(Constants.USER_ID, 0);
        }

        Intent intent = getIntent();
        dataItem = (DataItem) intent.getSerializableExtra(Constants.SUB_CATEGORY_OBJECT);


        categoryList();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                categoryList();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                AppClass.hideKeyboard(SearchActivity.this, v);
                return true;
            }
        });

    }

    private void categoryList() {

        if (searchCall != null) {
            searchCall.cancel();
        }
        String searchQuery = etSearch.getText().toString();
        progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        searchCall = apiInterface.getSearchList(searchQuery);

        searchCall.enqueue(new Callback<TopSellingResponse>() {
            @Override
            public void onResponse(Call<TopSellingResponse> call, Response<TopSellingResponse> response) {
                progressBar.setVisibility(View.GONE);
                TopSellingResponse allBookingListResponseDM = response.body();

                if (allBookingListResponseDM != null) {
                    if (allBookingListResponseDM.getData() != null) {
                        List<Product> resultItems = allBookingListResponseDM.getData();
                        if (resultItems.size() > 0) {

                            prepareListData(resultItems);
                        } else {
                            tv_no_booking.setText("Not available");
                            tv_no_booking.setVisibility(View.VISIBLE);

                        }
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TopSellingResponse> call, Throwable t) {

                progressBar.setVisibility(View.GONE);


            }
        });


    }


    private void prepareListData(List<Product> resultItems) {

        layoutManager = new GridLayoutManager(this, 2);
        recy_categories.setLayoutManager(layoutManager);
        recy_categories.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        taskListAdap = new ProductsListAdapter(this, resultItems, this, cartDataTableList, favDataTableArrayList);
        recy_categories.setAdapter(taskListAdap);
        taskListAdap.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view, Product product1) {

        if (isLogin) {
            int userId = sharedPreferences.getInt(Constants.USER_ID, 0);
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            viewParent = (View) view.getParent();
            product = product1;

    /*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                viewParent.setForeground(context.getDrawable(R.drawable.ic_cart_item));
            } else {
                viewParent.setAlpha(0.5f);
            }*/

            progressDialog.dismiss();
            int quantity = 1;
            cartViewModel.addProductToCart(userId, product.getProduct_id(), quantity).observe(this, new Observer<CartResponse>() {
                @Override
                public void onChanged(CartResponse cartResponse) {
                    progressDialog.dismiss();
                    if (cartResponse != null) {
                        progressDialog.dismiss();
                        if (cartResponse.getMessage().equals("Product added successfully")) {
                            progressDialog.dismiss();

                            CartDataTable cartDataTable = new CartDataTable();
                            cartDataTable.setProductId(product.getProduct_id());
                            cartDataTable.setProductQuantity(1);
                            cartDataTable.setUserId(userId);

                            cartViewModel.insertCart(cartDataTable);
                            invalidateOptionsMenu();

                        } else {
                            Toast.makeText(context, cartResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Intent intent1 = new Intent(context, LoginActivity.class);
            intent1.putExtra("ACTIVITY_NAME", SearchActivity.class.getSimpleName());
            intent1.putExtra(Constants.SUB_CATEGORY_OBJECT, dataItem);
            startActivity(intent1);
            finish();
        }

    }

    @Override
    public void onFavClick(View view, Product productFav, int position) {

        if (isLogin) {
            int userId = sharedPreferences.getInt(Constants.USER_ID, 0);

            FavDataTable favDataTable = wishViewModel.getFavourite(productFav.getProduct_id(), userId);

            if (favDataTable != null) {
                deleteDialog(view, productFav, position);
            } else {
                progressDialog = new ProgressDialog(this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                product = productFav;
                ImageView imageView = (ImageView) view;
                //int resourceId=getResources().getIdentifier("com.budget.budgetmanager:drawable/ic_baseline_star_filled_24","drawable",getPackageName());
                imageView.setImageResource(R.drawable.ic_baseline_star_filled_24);

                categoryFragmentViewModel.addToFavourite(String.valueOf(product.getProduct_id()), userId).observe(this, new Observer<AddFavResponse>() {
                    @Override
                    public void onChanged(AddFavResponse addFavResponse) {
                        progressDialog.dismiss();
                        if (addFavResponse != null) {
                            progressDialog.dismiss();
                            Toast.makeText(context, addFavResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            FavDataTable favDataTable = new FavDataTable();
                            favDataTable.setProductId(product.getProduct_id());
                            favDataTable.setUserId(userId);
                            categoryFragmentViewModel.insertFavToDB(favDataTable);

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }


        }

    }

//    @Override
//    public void updateCart(int productId, int quantity, int position) {
//
//    }

    @Override
    public void updateCart(int productId, int quantity, int position, View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        pId = productId;
        pQun = quantity;
        pos2 = position;


        CartDataTable cartDataTable1 = cartViewModel.getCart2(pId, userId);

        if (cartDataTable1 != null) {

            if (pQun == 0) {
                cartViewModel.deleteCart(cartDataTable1);
            } else {

                CartDataTable cartDataTable = new CartDataTable();
                cartDataTable.setProductQuantity(pQun);
                cartDataTable.setProductId(pId);
                cartDataTable.setCartId(cartDataTable1.getCartId());
                cartDataTable.setUserId(userId);
                cartViewModel.updateCart(cartDataTable);

                progressDialog.show();

                Toast.makeText(getApplicationContext(), "Added!", Toast.LENGTH_SHORT).show();

            }
        } else {
            progressDialog.show();
            Toast.makeText(getApplicationContext(), "cartDataTable1 is NULL", Toast.LENGTH_SHORT).show();
        }

        cartViewModel.updateCart(pId, pQun, userId).observe(SearchActivity.this, new Observer<UpdateCartResponse>() {
            @Override
            public void onChanged(UpdateCartResponse updateCartResponse) {
                progressDialog.dismiss();
                if (updateCartResponse != null) {
                    progressDialog.dismiss();
                    if (updateCartResponse.getData() != null) {
                        progressDialog.dismiss();

                        if (updateCartResponse.getData().size() > 0) {
//                            Toast.makeText(getApplicationContext(), "API updated", Toast.LENGTH_SHORT).show();
                        } else {

                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "getData().size() empty", Toast.LENGTH_SHORT).show();

                            CartDataTable cartDataTable1 = cartViewModel.getCart2(pId, userId);

                            CartDataTable cartDataTable = new CartDataTable();
                            cartDataTable.setProductQuantity(pQun);
                            cartDataTable.setProductId(pId);
                            cartDataTable.setCartId(cartDataTable1.getCartId());
                            cartDataTable.setUserId(userId);
                            cartViewModel.deleteCart(cartDataTable);

                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "getData().size() null", Toast.LENGTH_SHORT).show();

                        CartDataTable cartDataTable1 = cartViewModel.getCart2(pId, userId);

                        if (cartDataTable1 != null) {

                            CartDataTable cartDataTable = new CartDataTable();
                            cartDataTable.setProductQuantity(pQun);
                            cartDataTable.setProductId(pId);
                            cartDataTable.setCartId(cartDataTable1.getCartId());
                            cartDataTable.setUserId(userId);
                            cartViewModel.deleteCart(cartDataTable);

                        } else {
                            Toast.makeText(getApplicationContext(), "cartdata Null", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void deleteDialog(View view, Product productFav, int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete")
                .setMessage("Are you sure you want to remove item from favourite?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int userId = sharedPreferences.getInt(Constants.USER_ID, 0);
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Please Wait...");
                        progressDialog.setCancelable(false);
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        product = productFav;
                        pos = position;
                        ImageView imageView = (ImageView) view;
                        //int resourceId=getResources().getIdentifier("com.budget.budgetmanager:drawable/ic_baseline_star_filled_24","drawable",getPackageName());
                        imageView.setImageResource(R.drawable.ic_star_border_black);

                        wishViewModel.deleteFavourite(product.getProduct_id(), userId).observe((LifecycleOwner) context, new Observer<DeleteFavResponse>() {
                            @Override
                            public void onChanged(DeleteFavResponse deleteFavResponse) {
                                progressDialog.dismiss();
                                if (deleteFavResponse != null) {
                                    progressDialog.dismiss();
                                    if (deleteFavResponse.getData() != null) {
                                        progressDialog.dismiss();
                                        if (deleteFavResponse.getData().equals("Remove from favorite successfully")) {
                                            progressDialog.dismiss();
                                            Toast.makeText(context, deleteFavResponse.getData(), Toast.LENGTH_SHORT).show();

                                            FavDataTable favDataTable = wishViewModel.getFavourite(product.getProduct_id(), userId);

                                            wishViewModel.deleteFavouriteFromDB(favDataTable);

                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(context, deleteFavResponse.getData(), Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
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

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
