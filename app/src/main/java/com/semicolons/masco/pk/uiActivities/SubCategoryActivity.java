package com.semicolons.masco.pk.uiActivities;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.AppClass;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.adapters.LatestSellingProductAdapter;
import com.semicolons.masco.pk.adapters.SliderAdapterExample;
import com.semicolons.masco.pk.adapters.SubCategoriesAdapter;
import com.semicolons.masco.pk.dataModels.CartDataTable;
import com.semicolons.masco.pk.dataModels.CategoryDM;
import com.semicolons.masco.pk.dataModels.DataItem;
import com.semicolons.masco.pk.dataModels.SliderImagesResponse;
import com.semicolons.masco.pk.dataModels.TopSellingResponse;
import com.semicolons.masco.pk.databinding.ActivitySubCategoryBinding;
import com.semicolons.masco.pk.itemDecorator.GridSpacingItemDecoration;
import com.semicolons.masco.pk.viewModels.CartViewModel;
import com.semicolons.masco.pk.viewModels.HomeFragmentViewModel;
import com.semicolons.masco.pk.viewModels.SubCategoryFragmentViewModel;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class SubCategoryActivity extends AppCompatActivity {

    private ActivitySubCategoryBinding binding;
    DataItem dataItem;
    private SubCategoriesAdapter taskListAdap;
    private RecyclerView.LayoutManager layoutManager;
    private SubCategoryFragmentViewModel categoryFragmentViewModel;
    private ProgressDialog progressDialog;
    private HomeFragmentViewModel homeFragmentViewModel;
    private CartViewModel cartViewModel;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private boolean isLogin;
    private int cartCounter = 0;
    private int userId;
    LatestSellingProductAdapter latestSellingProductAdapter;
    private List<CartDataTable> cartDataTableList = new ArrayList<>();

    EditText et_search3;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        getLatestProducts(1);

        getSupportActionBar().setTitle(dataItem.getCategoryName());

        binding.imgSearch.setOnClickListener(view -> {
            Intent intent = new Intent(SubCategoryActivity.this, SearchActivity.class);
            startActivity(intent);
        });
    }

    private void getSliderImages() {

        if (AppClass.isOnline(this)) {

            homeFragmentViewModel.getSliderImages().observe(this, new Observer<SliderImagesResponse>() {

                @Override
                public void onChanged(SliderImagesResponse sliderImagesResponse) {

                    if (sliderImagesResponse.getStatus() == 1) {

                        SliderAdapterExample adapter = new SliderAdapterExample(SubCategoryActivity.this, sliderImagesResponse.getData());

                        binding.imageSlider1.setSliderAdapter(adapter);

//                        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                        binding.imageSlider1.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                        binding.imageSlider1.setAutoCycleDirection(binding.imageSlider1.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                        binding.imageSlider1.setIndicatorSelectedColor(Color.WHITE);
                        binding.imageSlider1.setIndicatorUnselectedColor(Color.GRAY);
                        binding.imageSlider1.setScrollTimeInSec(4); //set scroll delay in seconds :
                        binding.imageSlider1.startAutoCycle();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SubCategoryActivity.this, "" + sliderImagesResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            AppClass.offline(SubCategoryActivity.this);
        }
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
                        binding.rvBundleOffers.setLayoutManager(new LinearLayoutManager(SubCategoryActivity.this,RecyclerView.HORIZONTAL,false));
                        latestSellingProductAdapter = new LatestSellingProductAdapter(topSellingResponse.getData(), cartDataTableList, SubCategoryActivity.this);
                        //   timeDealAdapter = new TopSellingAdapter(topSellingResponse.getData(), cartProductList, getActivity());
                        binding.rvBundleOffers.setAdapter(latestSellingProductAdapter);

                        binding.rvMostPopular.setLayoutManager(new LinearLayoutManager(SubCategoryActivity.this,RecyclerView.HORIZONTAL,false));
                        binding.rvMostPopular.setAdapter(latestSellingProductAdapter);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SubCategoryActivity.this, "" + topSellingResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            AppClass.offline(this);
        }
    }

    @Override
    protected void onResume() {

        isLogin=sharedPreferences.getBoolean(Constants.USER_IS_LOGIN,false);
        if(isLogin){
            cartViewModel.getCartCount(userId).observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if(integer!=null){
                        cartCounter=integer;
                    }else {
                        cartCounter=0;
                    }
                    invalidateOptionsMenu();
                }
            });
        }

        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //do whatever
                onBackPressed();
                return true;
            case R.id.action_cart:
                Intent intent = new Intent(SubCategoryActivity.this, CartActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    public void prepareMenu(Menu menu){
        getMenuInflater().inflate(R.menu.home, menu);

        menu.findItem(R.id.action_search).setVisible(false);
        MenuItem menuItem = menu.findItem(R.id.action_cart);

        if(cartCounter!=0){
            menuItem.setIcon(homeFragmentViewModel.getDrawableForCounter(Integer.valueOf(cartCounter),  R.drawable.ic_baseline_shopping_cart_24));
        }else {
            menuItem.setIcon(homeFragmentViewModel.getDrawableForCounter(0,R.drawable.ic_baseline_shopping_cart_24));
        }
    }

    private void initViews() {
        sharedPreferences = getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);
        categoryFragmentViewModel = new ViewModelProvider(this).get(SubCategoryFragmentViewModel.class);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        homeFragmentViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);


        Intent intent = getIntent();
        dataItem = (DataItem) intent.getSerializableExtra(Constants.CATEGORY_OBJECT);
        categoryList(dataItem.getCategoryID());
        Log.d("subCategoryID", String.valueOf(dataItem.getCategoryID()));//1680,

        isLogin = sharedPreferences.getBoolean(Constants.USER_IS_LOGIN, false);
        if (isLogin) {
            userId = sharedPreferences.getInt(Constants.USER_ID, 0);
        }
        getSliderImages();
    }

    private void categoryList(int catId) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching sub...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        categoryFragmentViewModel.getAllSubCategories(catId).observe(this, allBookingListResponseDM -> {
            progressDialog.dismiss();
            if (allBookingListResponseDM != null) {
                progressDialog.dismiss();
                if (allBookingListResponseDM.getData() != null) {
                    List<DataItem> resultItems = allBookingListResponseDM.getData();
                    progressDialog.dismiss();
                    if (resultItems.size() > 0) {
                        progressDialog.dismiss();

                        prepareListData(resultItems);
                    } else {
                        Intent intent = new Intent(SubCategoryActivity.this, ProductListActivity.class);
                        intent.putExtra(Constants.SUB_CATEGORY_OBJECT, dataItem);
                        startActivity(intent);
                        finish();

                        progressDialog.dismiss();

                    }

                }
                if (allBookingListResponseDM.getMessage().equalsIgnoreCase("No data found"))
                {
                    Intent intent = new Intent(SubCategoryActivity.this, ProductListActivity.class);
                    intent.putExtra(Constants.SUB_CATEGORY_OBJECT, dataItem);
                    startActivity(intent);
                    finish();

                }
            } else {

                progressDialog.dismiss();
            }

        });

    }

    private void prepareListData(List<DataItem> resultItems) {

        layoutManager = new GridLayoutManager(this, 3);
        binding.recyCategories.setLayoutManager(layoutManager);
        binding.recyCategories.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        taskListAdap = new SubCategoriesAdapter(this, resultItems);
        binding.recyCategories.setAdapter(taskListAdap);

       /* binding.rvBundleOffers.setLayoutManager(layoutManager);
        binding.rvBundleOffers.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        binding.rvBundleOffers.setAdapter(taskListAdap);*/

        taskListAdap.notifyDataSetChanged();
    }
}
