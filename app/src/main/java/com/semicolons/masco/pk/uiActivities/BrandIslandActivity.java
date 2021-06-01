package com.semicolons.masco.pk.uiActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.AppClass;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.Utils.Utilities;
import com.semicolons.masco.pk.adapters.LatestSellingProductAdapter;
import com.semicolons.masco.pk.adapters.SliderAdapterExample;
import com.semicolons.masco.pk.dataModels.CartDataTable;
import com.semicolons.masco.pk.dataModels.SliderImagesResponse;
import com.semicolons.masco.pk.dataModels.TopSellingResponse;
import com.semicolons.masco.pk.databinding.ActivityBrandIslandBinding;
import com.semicolons.masco.pk.viewModels.CartViewModel;
import com.semicolons.masco.pk.viewModels.HomeFragmentViewModel;
import com.smarteist.autoimageslider.SliderAnimations;

import java.util.ArrayList;
import java.util.List;

public class BrandIslandActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private boolean isLogin;
    private int cartCounter = 0;
    private CartViewModel cartViewModel;
    private int userId;
    private HomeFragmentViewModel homeFragmentViewModel;
    private ActivityBrandIslandBinding binding;
    ProgressDialog progressDialog;
    LatestSellingProductAdapter latestSellingProductAdapter;
    private List<CartDataTable> cartDataTableList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBrandIslandBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Brand Island");

        Intent intent = getIntent();
        boolean isCartFragment = intent.getBooleanExtra("IS_CART_FRAGMENT", false);

        init();
        getCartItemDb();
        getLatestProducts(1);
        getSliderImages();

    }

    private void init() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        sharedPreferences = getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        homeFragmentViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);


        isLogin = sharedPreferences.getBoolean(Constants.USER_IS_LOGIN, false);

        if (isLogin) {
            userId = sharedPreferences.getInt(Constants.USER_ID, 0);
        }

    }

    private void getCartItemDb() {

        cartViewModel.getCartDataTableLiveData(userId).observe(this, new Observer<List<CartDataTable>>() {
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

                    // timeDealAdapter = new TopSellingAdapter(Constants.topSellingProduct, cartDataTableList, getActivity());
                    //recyclerFeaturedProducts.setAdapter(timeDealAdapter);
                    //timeDealAdapter.notifyDataSetChanged();

                } else {
                    Log.d("CartData", "NULL");
                }
            }
        });

    }

    private void getLatestProducts(int page) {

        if (AppClass.isOnline(this)) {

            progressDialog.show();

            homeFragmentViewModel.getLatestProducts(page).observe(this, new Observer<TopSellingResponse>() {

                @Override
                public void onChanged(TopSellingResponse topSellingResponse) {

                    if (topSellingResponse.getStatus() == 1) {

                        progressDialog.dismiss();


                        Constants.latestProduct = topSellingResponse.getData();
                        binding.rvBrandIsland.setLayoutManager(new GridLayoutManager(BrandIslandActivity.this,2));
                        latestSellingProductAdapter = new LatestSellingProductAdapter(topSellingResponse.getData(), cartDataTableList, BrandIslandActivity.this);
                        //   timeDealAdapter = new TopSellingAdapter(topSellingResponse.getData(), cartProductList, getActivity());
                        binding.rvBrandIsland.setAdapter(latestSellingProductAdapter);


                        binding.rvBundleOffers.setLayoutManager(new GridLayoutManager(BrandIslandActivity.this,2));
                        binding.rvBundleOffers.setAdapter(latestSellingProductAdapter);


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(BrandIslandActivity.this, "" + topSellingResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            AppClass.offline(BrandIslandActivity.this);
        }
    }

    private void getSliderImages() {

        if (AppClass.isOnline(this)) {

            homeFragmentViewModel.getSliderImages().observe(this, new Observer<SliderImagesResponse>() {

                @Override
                public void onChanged(SliderImagesResponse sliderImagesResponse) {

                    if (sliderImagesResponse.getStatus() == 1) {

                        SliderAdapterExample adapter = new SliderAdapterExample(BrandIslandActivity.this, sliderImagesResponse.getData());

                        binding.imageSlider2.setSliderAdapter(adapter);

//                        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                        binding.imageSlider2.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                        binding.imageSlider2.setAutoCycleDirection(binding.imageSlider2.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                        binding.imageSlider2.setIndicatorSelectedColor(Color.WHITE);
                        binding.imageSlider2.setIndicatorUnselectedColor(Color.GRAY);
                        binding.imageSlider2.setScrollTimeInSec(4); //set scroll delay in seconds :
                        binding.imageSlider2.startAutoCycle();


                    } else {
                        Toast.makeText(BrandIslandActivity.this, "" + sliderImagesResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            AppClass.offline(this);
        }
    }

    @Override
    protected void onResume() {

        isLogin = sharedPreferences.getBoolean(Constants.USER_IS_LOGIN, false);
        if (isLogin) {
            userId = sharedPreferences.getInt(Constants.USER_ID, 0);
            cartViewModel.getCartCount(userId).observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if (integer != null) {
                        cartCounter = integer;
                        Log.d("cartCounter", String.valueOf(cartCounter));
                    } else {
                        cartCounter = 0;
                    }
                    invalidateOptionsMenu();
                }
            });
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        prepareMenu(menu);
        return true;
    }

    public void prepareMenu(Menu menu) {
        menu.clear();

        getMenuInflater().inflate(R.menu.home, menu);

        MenuItem menuItem = menu.findItem(R.id.action_cart);
        if (isLogin) {

            if (cartCounter != 0) {
                menuItem.setIcon(homeFragmentViewModel.getDrawableForCounter(cartCounter, R.drawable.ic_baseline_shopping_cart_24));
            } else {
                menuItem.setIcon(homeFragmentViewModel.getDrawableForCounter(0, R.drawable.ic_baseline_shopping_cart_24));
            }
        } else {
            menuItem.setIcon(homeFragmentViewModel.getDrawableForCounter(0, R.drawable.ic_baseline_shopping_cart_24));
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        prepareMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.simpleFrameLayout,new CartFragment()).commit();

            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}