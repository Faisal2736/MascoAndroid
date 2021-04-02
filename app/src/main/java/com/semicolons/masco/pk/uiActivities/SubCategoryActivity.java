package com.semicolons.masco.pk.uiActivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.adapters.SubCategoriesAdapter;
import com.semicolons.masco.pk.dataModels.CategoryDM;
import com.semicolons.masco.pk.dataModels.DataItem;
import com.semicolons.masco.pk.itemDecorator.GridSpacingItemDecoration;
import com.semicolons.masco.pk.viewModels.CartViewModel;
import com.semicolons.masco.pk.viewModels.HomeFragmentViewModel;
import com.semicolons.masco.pk.viewModels.SubCategoryFragmentViewModel;

public class SubCategoryActivity extends AppCompatActivity {


    DataItem dataItem;
    private RecyclerView recy_categories;
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

    EditText et_search3;
    private TabLayout tabLayout;
    ImageView img_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubCategoryActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
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
        recy_categories = findViewById(R.id.recy_categories);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        homeFragmentViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);

        img_search = findViewById(R.id.img_search);


        Intent intent = getIntent();
        dataItem = (DataItem) intent.getSerializableExtra(Constants.CATEGORY_OBJECT);
        categoryList(dataItem.getCategoryID());
        Log.d("subCategoryID", String.valueOf(dataItem.getCategoryID()));//1680,

        isLogin = sharedPreferences.getBoolean(Constants.USER_IS_LOGIN, false);
        if (isLogin) {
            userId = sharedPreferences.getInt(Constants.USER_ID, 0);
        }
    }

    private void categoryList(int catId) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching sub...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        categoryFragmentViewModel.getAllSubCategories(catId).observe(this, new Observer<CategoryDM>() {
            @Override
            public void onChanged(CategoryDM allBookingListResponseDM) {
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

            }
        });

    }

    private void prepareListData(List<DataItem> resultItems) {

        layoutManager = new GridLayoutManager(this, 2);
        recy_categories.setLayoutManager(layoutManager);
        recy_categories.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        taskListAdap = new SubCategoriesAdapter(this, resultItems);
        recy_categories.setAdapter(taskListAdap);
        taskListAdap.notifyDataSetChanged();
    }
}
