package com.semicolons.masco.pk.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.AppClass;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.Utils.Utilities;
import com.semicolons.masco.pk.adapters.LatestSellingProductAdapter;
import com.semicolons.masco.pk.adapters.MainCategoriesAdapter;
import com.semicolons.masco.pk.adapters.SliderAdapterExample;
import com.semicolons.masco.pk.dataModels.CartDataTable;
import com.semicolons.masco.pk.dataModels.CategoryDM;
import com.semicolons.masco.pk.dataModels.DataItem;
import com.semicolons.masco.pk.dataModels.SliderImages;
import com.semicolons.masco.pk.dataModels.SliderImagesResponse;
import com.semicolons.masco.pk.dataModels.TopSellingResponse;
import com.semicolons.masco.pk.databinding.FragmentCategoryBinding;
import com.semicolons.masco.pk.viewModels.CartViewModel;
import com.semicolons.masco.pk.viewModels.HomeFragmentViewModel;
import com.smarteist.autoimageslider.SliderAnimations;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;

    ProgressDialog progressDialog;
    int grocery_id = 60, personal_care_id = 87, stationery_id = 90, snacks_id = 94, sports_id = 87, clean_id = 165, cosmetics_id = 91, baby_care_id = 88, food_id = 93,
            fragrances_id = 98, garments_id = 92, undergarments_id = 96, tailoring_material_id = 97, jewellery_id = 95, uncategorized_id = 15;
    private HomeFragmentViewModel homeFragmentViewModel;
    MainCategoriesAdapter mainCategoriesAdapter;
    LatestSellingProductAdapter latestSellingProductAdapter;
    private List<CartDataTable> cartDataTableList = new ArrayList<>();
    private CartViewModel cartViewModel;
    private int userId;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater, container, false);

        getActivity().findViewById(R.id.rlSearch).setVisibility(View.VISIBLE);

        initViews();
        Toast.makeText(getActivity(), "Please Wait....", Toast.LENGTH_LONG).show();

        getMainCategories();
        getLatestProducts(1);
        getSliderImages();
        
        return binding.getRoot();
    }

    private void initViews() {
        // categoryFragmentViewModel = new ViewModelProvider(this).get(CategoryFragmentViewModel.class);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait for amazing offers...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        homeFragmentViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);

    }

    private ArrayList<SliderImages> getData(){

        SliderImages sliderImages1 = new SliderImages();
        sliderImages1.setImage_name("baby_care_slider");

        SliderImages sliderImages2 = new SliderImages();
        sliderImages2.setImage_name("bakery_and_frozen_food_slider");

        SliderImages sliderImages3 = new SliderImages();
        sliderImages3.setImage_name("clean_shain_slider");

        SliderImages sliderImages4 = new SliderImages();
        sliderImages4.setImage_name("grocery_slider");

        SliderImages sliderImages5 = new SliderImages();
        sliderImages5.setImage_name("personal_care_slider");

        SliderImages sliderImages6 = new SliderImages();
        sliderImages6.setImage_name("snakcs_and_beverages_slider");

        SliderImages sliderImages7 = new SliderImages();
        sliderImages7.setImage_name("stationery_slider");

        ArrayList<SliderImages> list = new ArrayList<>();
        list.add(sliderImages1);
        list.add(sliderImages2);
        list.add(sliderImages3);
        list.add(sliderImages4);
        list.add(sliderImages5);
        list.add(sliderImages6);
        list.add(sliderImages7);

        return list;

    }

    private void getSliderImages() {

        SliderAdapterExample adapter = new SliderAdapterExample(getActivity(), getData());
//      sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.imageSlider1.setSliderAdapter(adapter);
        binding.imageSlider1.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider1.setAutoCycleDirection(binding.imageSlider1.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        binding.imageSlider1.setIndicatorSelectedColor(Color.WHITE);
        binding.imageSlider1.setIndicatorUnselectedColor(Color.GRAY);
        binding.imageSlider1.setScrollTimeInSec(4); //set scroll delay in seconds :
        binding.imageSlider1.startAutoCycle();

//      sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.imageSlider2.setSliderAdapter(adapter);
        binding.imageSlider2.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider2.setAutoCycleDirection(binding.imageSlider1.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        binding.imageSlider2.setIndicatorSelectedColor(Color.WHITE);
        binding.imageSlider2.setIndicatorUnselectedColor(Color.GRAY);
        binding.imageSlider2.setScrollTimeInSec(4); //set scroll delay in seconds :
        binding.imageSlider2.startAutoCycle();
    }

    private void getMainCategories() {


        homeFragmentViewModel.getAllCategories().observe(getActivity(), new Observer<CategoryDM>() {
            @Override
            public void onChanged(CategoryDM allBookingListResponseDM) {
                if (allBookingListResponseDM != null) {
                    progressDialog.dismiss();
                    if (allBookingListResponseDM.getData() != null) {
                        List<DataItem> resultItems = allBookingListResponseDM.getData();
                        if (resultItems.size() > 0) {
                            progressDialog.dismiss();

                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
                            binding.recyMainCategories.setLayoutManager(layoutManager);
                            mainCategoriesAdapter = new MainCategoriesAdapter(getActivity(), resultItems);
                            binding.recyMainCategories.setAdapter(mainCategoriesAdapter);
                        } else {
//                            Intent intent = new Intent(SubCategoryActivity.this, ProductListActivity.class);
//                            intent.putExtra(Constants.SUB_CATEGORY_OBJECT, dataItem);
//                            startActivity(intent);
//                            finish();


                        }
                    }
                } else {
                }

            }
        });

    }

    private void getLatestProducts(int page) {

        if (AppClass.isOnline(getActivity())) {

            progressDialog.show();

            homeFragmentViewModel.getLatestProducts(page).observe(getActivity(), new Observer<TopSellingResponse>() {

                @Override
                public void onChanged(TopSellingResponse topSellingResponse) {

                    if (topSellingResponse.getStatus() == 1) {

                        progressDialog.dismiss();

                        Constants.latestProduct = topSellingResponse.getData();
                        binding.rvBundleOffers.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
                        latestSellingProductAdapter = new LatestSellingProductAdapter(topSellingResponse.getData(), cartDataTableList, getActivity());
                        //   timeDealAdapter = new TopSellingAdapter(topSellingResponse.getData(), cartProductList, getActivity());
                        binding.rvBundleOffers.setAdapter(latestSellingProductAdapter);

                        binding.rvMostPopular.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
                        binding.rvMostPopular.setAdapter(latestSellingProductAdapter);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "" + topSellingResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            AppClass.offline(getActivity());
        }
    }

    private void getCartItemDb() {

        cartViewModel.getCartDataTableLiveData(userId).observe(getActivity(), new Observer<List<CartDataTable>>() {
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
}