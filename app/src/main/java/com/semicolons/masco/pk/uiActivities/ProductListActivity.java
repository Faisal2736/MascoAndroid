package com.semicolons.masco.pk.uiActivities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.semicolons.masco.pk.Utils.AppClass;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.Utils.Utilities;
import com.semicolons.masco.pk.adapters.ProductsListAdapter;
import com.semicolons.masco.pk.adapters.SliderAdapterExample;
import com.semicolons.masco.pk.dataModels.AddFavResponse;
import com.semicolons.masco.pk.dataModels.CartDataTable;
import com.semicolons.masco.pk.dataModels.CartResponse;
import com.semicolons.masco.pk.dataModels.DataItem;
import com.semicolons.masco.pk.dataModels.DeleteFavResponse;
import com.semicolons.masco.pk.dataModels.FavDataTable;
import com.semicolons.masco.pk.dataModels.Product;
import com.semicolons.masco.pk.dataModels.SliderImagesResponse;
import com.semicolons.masco.pk.dataModels.TopSellingResponse;
import com.semicolons.masco.pk.dataModels.UpdateCartResponse;
import com.semicolons.masco.pk.itemDecorator.GridSpacingItemDecoration;
import com.semicolons.masco.pk.listeners.PaginationScrollListener;
import com.semicolons.masco.pk.retrofit.ApiClient;
import com.semicolons.masco.pk.retrofit.ApiInterface;
import com.semicolons.masco.pk.viewModels.CartViewModel;
import com.semicolons.masco.pk.viewModels.HomeFragmentViewModel;
import com.semicolons.masco.pk.viewModels.ProductListFragmentViewModel;
import com.semicolons.masco.pk.viewModels.WishViewModel;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity implements ProductsListAdapter.onItemClickListenerAdd {

    private RecyclerView recy_categories;
    private ProductsListAdapter taskListAdap;
    private GridLayoutManager layoutManager;
    private ProductListFragmentViewModel categoryFragmentViewModel;
    private ProgressDialog progressDialog;
    private TextView tv_no_booking;
    private HomeFragmentViewModel homeFragmentViewModel;
    private CartViewModel cartViewModel;
    private Context context;
    EditText et_search1;
    private Product product;
    private SharedPreferences sharedPreferences;
    private boolean isLogin;
    private DataItem dataItem;
    private View viewParent;
    private ArrayList<FavDataTable> favDataTableArrayList;
    private WishViewModel wishViewModel;
    private int cartCounter = 0;
    private int userId;

    private int pos;
    private List<CartDataTable> cartDataTableList = new ArrayList<>();
    private int pQun;
    private int pId;
    private int pos2;
    private TabLayout tabLayout;

    ArrayList<Product> resultItems = new ArrayList<>();
    private SliderView sliderView;

    int currentPage = 1;
    boolean isLastPage = false;
    boolean isLoading = false;

    ImageView img_search;

    private boolean listSize = true;
    private boolean listLoaded = false;

    private ApiInterface apiInterface;
    private Call<TopSellingResponse> arrayListCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Products");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        getSliderImages();


        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductListActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //do whatever
                onBackPressed();
                return true;
            case R.id.action_cart:
                Intent intent = new Intent(ProductListActivity.this, CartActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        invalidateOptionsMenu();
        if (taskListAdap != null) {
            categoryList(dataItem.getCategoryID());
        }

        isLogin = sharedPreferences.getBoolean(Constants.USER_IS_LOGIN, false);
        if (isLogin) {
            userId=sharedPreferences.getInt(Constants.USER_ID, 0);
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

            getCartItemDb();
//            cartViewModel.getCartDataTableLiveData(userId).observe(this, new Observer<List<CartDataTable>>() {
//                @Override
//                public void onChanged(List<CartDataTable> cartDataTables) {
//                    if (cartDataTables != null) {
//                        cartDataTableList = cartDataTables;
//                    }
//                }
//            });

            categoryFragmentViewModel.getAllFavourites(userId).observe(this, new Observer<List<FavDataTable>>() {
                @Override
                public void onChanged(List<FavDataTable> favDataTableList) {
                    if (favDataTableList != null) {
                        favDataTableArrayList = (ArrayList<FavDataTable>) favDataTableList;
                    }
                }
            });

        }

        recy_categories.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                if (!isLastPage()) {
                    categoryListNextCall(dataItem.getCategoryID());
                }else {

                    taskListAdap.removeLoadingFooter();
                }
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        super.onResume();
    }

    private void getCartItemDb() {

        cartViewModel.getCartDataTableLiveData(userId).observe(ProductListActivity.this, new Observer<List<CartDataTable>>() {
            @Override
            public void onChanged(List<CartDataTable> cartDataTables) {
                if (cartDataTables != null) {
                    cartDataTableList = cartDataTables;


                    for (int i = 0; i < cartDataTableList.size(); i++) {
                        Utilities.p[i] = cartDataTables.get(i).getProductId();
                        Utilities.q[i] = cartDataTables.get(i).getProductQuantity();
                    }

                    for (int i = 0; i < cartDataTableList.size(); i++) {

                        Log.d("CartData", cartDataTableList.get(i).getCartId() + ": " +
                                cartDataTableList.get(i).getProductId() + ": " + cartDataTableList.get(i).getProductQuantity() + ": " +
                                cartDataTableList.get(i).getUserId());
                    }


                } else {
                    Log.d("CartData", "NULL");
                    Toast.makeText(getApplicationContext(), "CartData Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

    private void getSliderImages() {

        if (AppClass.isOnline(this)) {

            homeFragmentViewModel.getSliderImages().observe(this, new Observer<SliderImagesResponse>() {

                @Override
                public void onChanged(SliderImagesResponse sliderImagesResponse) {

                    if (sliderImagesResponse.getStatus() == 1) {

                        SliderAdapterExample adapter = new SliderAdapterExample(ProductListActivity.this, sliderImagesResponse.getData());

                        sliderView.setSliderAdapter(adapter);

//                        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                        sliderView.setAutoCycleDirection(sliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                        sliderView.setIndicatorSelectedColor(Color.WHITE);
                        sliderView.setIndicatorUnselectedColor(Color.GRAY);
                        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
                        sliderView.startAutoCycle();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(ProductListActivity.this, "" + sliderImagesResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            AppClass.offline(ProductListActivity.this);
        }
    }

    private void initViews() {
        context = this;
        Intent intent = getIntent();
        dataItem = (DataItem) intent.getSerializableExtra(Constants.SUB_CATEGORY_OBJECT);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);


        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        categoryFragmentViewModel = new ViewModelProvider(this).get(ProductListFragmentViewModel.class);
        recy_categories = findViewById(R.id.recy_categories);
        tv_no_booking = findViewById(R.id.tv_no_booking);
        sliderView = findViewById(R.id.productListingimageSlider1);
        sharedPreferences = getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean(Constants.USER_IS_LOGIN, false);
        homeFragmentViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        wishViewModel = new ViewModelProvider(this).get(WishViewModel.class);


        layoutManager = new GridLayoutManager(this, 2);
        recy_categories.setLayoutManager(layoutManager);
        recy_categories.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        taskListAdap = new ProductsListAdapter(this, resultItems, this, cartDataTableList, favDataTableArrayList,String.valueOf(dataItem.getCategoryID()));
        recy_categories.setAdapter(taskListAdap);

        img_search = findViewById(R.id.img_search);


        if (isLogin) {
            userId = sharedPreferences.getInt(Constants.USER_ID, 0);
        }


//        categoryList(dataItem.getCategoryID());
        Log.d("getCategoryID", String.valueOf(dataItem.getCategoryID()));//1697

    }

    private void categoryList(int catId) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching products...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        arrayListCall = apiInterface.getProductsList(currentPage,catId);
        arrayListCall.enqueue(new Callback<TopSellingResponse>() {
            @Override
            public void onResponse(Call<TopSellingResponse> call, Response<TopSellingResponse> response) {
                if (response.isSuccessful()) {
                    TopSellingResponse adsDM = response.body();
                    List<Product> jobsItemList = adsDM.getData();


                    if (jobsItemList == null || jobsItemList.size() == 0) {

                        Toast.makeText(ProductListActivity.this, "No Record Found", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    } else {
                        listLoaded = true;
                        if (jobsItemList.size() < 20) {
                            listSize = false;
                        }


                        taskListAdap.addAll(jobsItemList,cartDataTableList,favDataTableArrayList);
                        taskListAdap.addLoadingFooter();
                        progressDialog.dismiss();
                    }


                } else {

                    Toast.makeText(ProductListActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TopSellingResponse> call, Throwable t) {

            }
        });

/*
        categoryFragmentViewModel.getProductsList(currentPage,catId).observe(this, new Observer<TopSellingResponse>() {
            @Override
            public void onChanged(TopSellingResponse allBookingListResponseDM) {
                progressDialog.dismiss();
                if (allBookingListResponseDM != null) {
                    progressDialog.dismiss();
                    if (allBookingListResponseDM.getData() != null) {
                        List<Product> resultItems = allBookingListResponseDM.getData();
                        progressDialog.dismiss();
                        if (resultItems == null || resultItems.size() == 0) {

                            taskListAdap.removeLoadingFooter();
                            isLastPage = true;

                        }
                        else {
                            listLoaded = true;

                            if (resultItems.size() < 20) {
                                listSize = false;
                            }
                            progressDialog.dismiss();

                            taskListAdap.addAll(resultItems);
                            taskListAdap.addLoadingFooter();
                            progressDialog.dismiss();
                            Log.d("getCategoryID", String.valueOf(catId));
                        }
//                            prepareListData(resultItems,String.valueOf(catId));
                        */
/*else {
                            progressDialog.dismiss();
                            tv_no_booking.setText("Not available");
                            progressDialog.dismiss();
                            tv_no_booking.setVisibility(View.VISIBLE);

                        }*//*

                    }
                } else {
                    progressDialog.dismiss();
                }

            }
        });
*/

    }
    private void categoryListNextCall(int catId) {



        arrayListCall = apiInterface.getProductsList(currentPage,catId);
        arrayListCall.enqueue(new Callback<TopSellingResponse>() {
            @Override
            public void onResponse(Call<TopSellingResponse> call, Response<TopSellingResponse> response) {
                if (response.isSuccessful()) {
                    TopSellingResponse adsDM = response.body();
                    List<Product> jobsItemList = adsDM.getData();

                    if (jobsItemList == null || jobsItemList.size() == 0) {

                        taskListAdap.removeLoadingFooter();
                        isLastPage = true;

                    } else {

                        listLoaded = true;
                        taskListAdap.removeLoadingFooter();
                        taskListAdap.addAll(jobsItemList,cartDataTableList,favDataTableArrayList);
                        isLoading = false;
                        taskListAdap.addLoadingFooter();


                    }
                } else {

                    Toast.makeText(ProductListActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TopSellingResponse> call, Throwable t) {

            }
        });

/*
        categoryFragmentViewModel.getProductsList(currentPage,catId).observe(this, new Observer<TopSellingResponse>() {
            @Override
            public void onChanged(TopSellingResponse allBookingListResponseDM) {
                progressDialog.dismiss();
                if (allBookingListResponseDM != null) {
                    progressDialog.dismiss();
                    if (allBookingListResponseDM.getData() != null) {
                        List<Product> resultItems = allBookingListResponseDM.getData();
                        progressDialog.dismiss();
                        if (resultItems == null || resultItems.size() == 0) {

                            taskListAdap.removeLoadingFooter();
                            isLastPage = true;

                        }
                        else {
                            listLoaded = true;

                            if (resultItems.size() < 20) {
                                listSize = false;
                            }
                            progressDialog.dismiss();

                            taskListAdap.addAll(resultItems);
                            taskListAdap.addLoadingFooter();
                            progressDialog.dismiss();
                            Log.d("getCategoryID", String.valueOf(catId));
                        }
//                            prepareListData(resultItems,String.valueOf(catId));
                        */
/*else {
                            progressDialog.dismiss();
                            tv_no_booking.setText("Not available");
                            progressDialog.dismiss();
                            tv_no_booking.setVisibility(View.VISIBLE);

                        }*//*

                    }
                } else {
                    progressDialog.dismiss();
                }

            }
        });
*/

    }

    private void prepareListData(List<Product> resultItems,String catid) {

        layoutManager = new GridLayoutManager(this, 2);
        recy_categories.setLayoutManager(layoutManager);
        recy_categories.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        taskListAdap = new ProductsListAdapter(this, resultItems, this, cartDataTableList, favDataTableArrayList,catid);
        recy_categories.setAdapter(taskListAdap);
        taskListAdap.notifyDataSetChanged();

    }


    @Override
    public void onClick(View view, Product product1) {

        product = product1;

        int id = product.getProduct_id();

        Log.d("id", String.valueOf(id));

        CartDataTable cartDataTable = new CartDataTable();
        cartDataTable.setProductId(id);
        cartDataTable.setProductQuantity(1);
        cartDataTable.setUserId(userId);

        cartViewModel.insertCart(cartDataTable);

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
                            Log.d("id2", String.valueOf(id));

//                            Toast.makeText(getApplicationContext(), "Data added in API", Toast.LENGTH_SHORT).show();

                            getCartItemDb();

                            if (cartDataTableList.size() > 0) {
                                taskListAdap.setList(cartDataTableList);
                            } else {
                                Toast.makeText(getApplicationContext(), "cartDataTableList empty", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                            invalidateOptionsMenu();

                        } else {
                            Toast.makeText(context, cartResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });


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
                            showResponseDialog("Added", addFavResponse.getMessage());
                            Toast.makeText(context, addFavResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            FavDataTable favDataTable = new FavDataTable();
                            favDataTable.setProductId(product.getProduct_id());
                            favDataTable.setUserId(userId);
                            categoryFragmentViewModel.insertFavToDB(favDataTable);

                        } else {
                            progressDialog.dismiss();
                            showResponseDialogFailed("Error", "Try Again");

                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }


        }

    }

    @Override
    public void updateCart(int productId, int quantity, int position, View view) {

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

        cartViewModel.updateCart(pId, pQun, userId).observe(ProductListActivity.this, new Observer<UpdateCartResponse>() {
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
                                            showResponseDialog("Deleted", deleteFavResponse.getData());
                                            Toast.makeText(context, deleteFavResponse.getData(), Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        showResponseDialogFailed("Error", "Try Again");

                                        progressDialog.dismiss();
                                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    showResponseDialogFailed("Error", "Try Again");

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
    protected void onPause() {
        super.onPause();
        arrayListCall.cancel();
    }
}
