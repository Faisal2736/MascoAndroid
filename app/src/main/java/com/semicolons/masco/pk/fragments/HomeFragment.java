package com.semicolons.masco.pk.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.AppClass;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.Utils.Utilities;
import com.semicolons.masco.pk.adapters.ExpandableListAdapter;
import com.semicolons.masco.pk.adapters.LatestSellingProductAdapter;
import com.semicolons.masco.pk.adapters.MainCategoriesAdapter;
import com.semicolons.masco.pk.adapters.SliderAdapterExample;
import com.semicolons.masco.pk.adapters.TopSellingAdapter;
import com.semicolons.masco.pk.dataModels.CartDataTable;
import com.semicolons.masco.pk.dataModels.CartResponse;
import com.semicolons.masco.pk.dataModels.CategoryDM;
import com.semicolons.masco.pk.dataModels.DataItem;
import com.semicolons.masco.pk.dataModels.ImagesListDM;
import com.semicolons.masco.pk.dataModels.Product;
import com.semicolons.masco.pk.dataModels.SliderImages;
import com.semicolons.masco.pk.dataModels.SliderImagesResponse;
import com.semicolons.masco.pk.dataModels.TopSellingResponse;
import com.semicolons.masco.pk.dataModels.UpdateCartResponse;
import com.semicolons.masco.pk.databinding.FragmentHome2Binding;
import com.semicolons.masco.pk.databinding.FragmentHomeBinding;
import com.semicolons.masco.pk.uiActivities.AllSubCategoriesActivities;
import com.semicolons.masco.pk.uiActivities.BrandIslandActivity;
import com.semicolons.masco.pk.uiActivities.HomeActivity;
import com.semicolons.masco.pk.uiActivities.LoginActivity;
import com.semicolons.masco.pk.uiActivities.RegisterActivity;
import com.semicolons.masco.pk.viewModels.CartViewModel;
import com.semicolons.masco.pk.viewModels.HomeFragmentViewModel;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private boolean isFirstTimeActivityStarted = false;
    private int cartCounter = 0;

    private FragmentHome2Binding binding;

    RelativeLayout btn_login_or_signUp;

    private HomeFragmentViewModel homeFragmentViewModel;
    private CartViewModel cartViewModel;

    private Boolean exit = false;
    private int userId;

    boolean topSellingComplete = false;
    ProgressDialog progressDialog;
    private ExpandableListView expListView;
    private ExpandableListAdapter expandableListAdapter;
    TopSellingAdapter timeDealAdapter;
    LatestSellingProductAdapter latestSellingProductAdapter;

    //List student ERP OBJECT
    ArrayList<ImagesListDM> loungeList = new ArrayList<>();


    private List<CartDataTable> cartDataTableList = new ArrayList<>();
    // private List<CartProduct> cartProductList = new ArrayList<>();

    private int pos;
    private int pQun;
    private int pId;
    private ImageView imgSuperStore;
    MainCategoriesAdapter mainCategoriesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHome2Binding.inflate(inflater, container, false);

        init();
        initListeners();

        getCartItemDb();

        getAllProducts(1);
        getLatestProducts(1);
        getMainCategories();

        return binding.getRoot();
    }

    private void init() {

        getActivity().findViewById(R.id.rlSearch).setVisibility(View.VISIBLE);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        homeFragmentViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);

        showGif(binding.imgSuperStore);
        getSliderImages();

    }

    public void showGif(ImageView view) {
        Glide.with(this).load(R.drawable.ic_super_store_gif).into(view);
    }

    private void initListeners() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait for amazing offers...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        binding.allTv.setOnClickListener((View.OnClickListener) view -> {
            Intent intent = new Intent(getActivity(), AllSubCategoriesActivities.class);
            startActivity(intent);
        });
        binding.tvFeatureAll.setOnClickListener((View.OnClickListener) view -> {
            Intent intent = new Intent(getActivity(), AllSubCategoriesActivities.class);
            startActivity(intent);
        });

        binding.brandIslandLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BrandIslandActivity.class));
            }
        });
    }

    private ArrayList<SliderImages> getSliderData(){

        SliderImages sliderImages1 = new SliderImages();
        sliderImages1.setImage_name("home_slider_1");

        SliderImages sliderImages2 = new SliderImages();
        sliderImages2.setImage_name("home_slider_2");

        SliderImages sliderImages3 = new SliderImages();
        sliderImages3.setImage_name("home_slider_3");

        SliderImages sliderImages4 = new SliderImages();
        sliderImages4.setImage_name("home_slider_4");

        SliderImages sliderImages5 = new SliderImages();
        sliderImages5.setImage_name("home_slider_5");

        SliderImages sliderImages6 = new SliderImages();
        sliderImages6.setImage_name("home_slider_6");

        SliderImages sliderImages7 = new SliderImages();
        sliderImages7.setImage_name("home_slider_7");

        SliderImages sliderImages8 = new SliderImages();
        sliderImages8.setImage_name("home_slider_8");

        ArrayList<SliderImages> list = new ArrayList<>();
        list.add(sliderImages1);
        list.add(sliderImages2);
        list.add(sliderImages3);
        list.add(sliderImages4);
        list.add(sliderImages5);
        list.add(sliderImages6);
        list.add(sliderImages7);
        list.add(sliderImages8);

        return list;

    }

    private ArrayList<SliderImages> getPenPencilData(){

        SliderImages sliderImages1 = new SliderImages();
        sliderImages1.setImage_name("pen_slider_1");

        SliderImages sliderImages2 = new SliderImages();
        sliderImages2.setImage_name("pen_slider_2");

        SliderImages sliderImages3 = new SliderImages();
        sliderImages3.setImage_name("pen_slider_3");

        SliderImages sliderImages4 = new SliderImages();
        sliderImages4.setImage_name("pen_slider_4");

        SliderImages sliderImages5 = new SliderImages();
        sliderImages5.setImage_name("pen_slider_5");

        SliderImages sliderImages6 = new SliderImages();
        sliderImages6.setImage_name("pen_slider_6");

        ArrayList<SliderImages> list = new ArrayList<>();
        list.add(sliderImages1);
        list.add(sliderImages2);
        list.add(sliderImages3);
        list.add(sliderImages4);
        list.add(sliderImages5);
        list.add(sliderImages6);

        return list;

    }

    private ArrayList<SliderImages> getCosmeticBannerData(){

        SliderImages sliderImages1 = new SliderImages();
        sliderImages1.setImage_name("masco_cosmetics_slider_1");

        SliderImages sliderImages2 = new SliderImages();
        sliderImages2.setImage_name("masco_cosmetics_slider_2");

        SliderImages sliderImages3 = new SliderImages();
        sliderImages3.setImage_name("masco_cosmetics_slider_3");

        ArrayList<SliderImages> list = new ArrayList<>();
        list.add(sliderImages1);
        list.add(sliderImages2);
        list.add(sliderImages3);

        return list;

    }

    private void getSliderImages() {

        SliderAdapterExample adapter2 = new SliderAdapterExample(getActivity(), getSliderData());

        binding.imageSlider2.setSliderAdapter(adapter2);

//      sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.imageSlider2.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider2.setAutoCycleDirection(binding.imageSlider2.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        binding.imageSlider2.setIndicatorSelectedColor(Color.WHITE);
        binding.imageSlider2.setIndicatorUnselectedColor(Color.GRAY);
        binding.imageSlider2.setScrollTimeInSec(4); //set scroll delay in seconds :
        binding.imageSlider2.startAutoCycle();

        SliderAdapterExample adapter1 = new SliderAdapterExample(getActivity(), getPenPencilData());

        binding.imageSlider1.setSliderAdapter(adapter1);

//      sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.imageSlider1.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider1.setAutoCycleDirection(binding.imageSlider1.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        binding.imageSlider1.setIndicatorSelectedColor(Color.WHITE);
        binding.imageSlider1.setIndicatorUnselectedColor(Color.GRAY);
        binding.imageSlider1.setScrollTimeInSec(4); //set scroll delay in seconds :
        binding.imageSlider1.startAutoCycle();

        SliderAdapterExample adapter3 = new SliderAdapterExample(getActivity(), getCosmeticBannerData());

        binding.imageSlider3.setSliderAdapter(adapter3);

//                        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.imageSlider3.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider3.setAutoCycleDirection(binding.imageSlider3.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        binding.imageSlider3.setIndicatorSelectedColor(Color.WHITE);
        binding.imageSlider3.setIndicatorUnselectedColor(Color.GRAY);
        binding.imageSlider3.setScrollTimeInSec(4); //set scroll delay in seconds :
        binding.imageSlider3.startAutoCycle();

        if (AppClass.isOnline(getActivity())) {

            homeFragmentViewModel.getSliderImages().observe(getActivity(), new Observer<SliderImagesResponse>() {

                @Override
                public void onChanged(SliderImagesResponse sliderImagesResponse) {
                    progressDialog.dismiss();


                    if (sliderImagesResponse.getStatus() == 1) {

                        SliderAdapterExample adapter = new SliderAdapterExample(getActivity(), sliderImagesResponse.getData());

                        binding.imageSlider.setSliderAdapter(adapter);

//                        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                        binding.imageSlider.setAutoCycleDirection(binding.imageSlider.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                        binding.imageSlider.setIndicatorSelectedColor(Color.WHITE);
                        binding.imageSlider.setIndicatorUnselectedColor(Color.GRAY);
                        binding.imageSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
                        binding.imageSlider.startAutoCycle();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "" + sliderImagesResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            AppClass.offline(getActivity());
        }
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

    private void getAllProducts(int page) {

        if (AppClass.isOnline(getActivity())) {

            progressDialog.show();

            homeFragmentViewModel.getAllProducts(page).observe(getActivity(), new Observer<TopSellingResponse>() {

                @Override
                public void onChanged(TopSellingResponse topSellingResponse) {

                    if (topSellingResponse.getStatus() == 1) {

                        progressDialog.dismiss();

                        topSellingComplete = true;

                        Constants.topSellingProduct = topSellingResponse.getData();

                       /* binding.recrecyclerFeaturedProducts.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        timeDealAdapter = new TopSellingAdapter(topSellingResponse.getData(), cartDataTableList, getActivity());
                        //   timeDealAdapter = new TopSellingAdapter(topSellingResponse.getData(), cartProductList, getActivity());
                        recyclerFeaturedProducts.setAdapter(timeDealAdapter);*/

                        clickListner();


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

    private void getLatestProducts(int page) {

        if (AppClass.isOnline(getActivity())) {

            progressDialog.show();

            homeFragmentViewModel.getLatestProducts(page).observe(getActivity(), new Observer<TopSellingResponse>() {

                @Override
                public void onChanged(TopSellingResponse topSellingResponse) {

                    if (topSellingResponse.getStatus() == 1) {

                        progressDialog.dismiss();


                        Constants.latestProduct = topSellingResponse.getData();
                        binding.recyclerLatestProducts.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
                        latestSellingProductAdapter = new LatestSellingProductAdapter(topSellingResponse.getData(), cartDataTableList, getActivity());
                        //   timeDealAdapter = new TopSellingAdapter(topSellingResponse.getData(), cartProductList, getActivity());
                        binding.recyclerLatestProducts.setAdapter(latestSellingProductAdapter);

                        LatestSellingProductAdapter latestSellingProductAdapter1 = new LatestSellingProductAdapter(topSellingResponse.getData(), cartDataTableList, getActivity());

                        binding.rvPickOftheDay.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
                        binding.rvPickOftheDay.setAdapter(latestSellingProductAdapter1);

                        LatestSellingProductAdapter latestSellingProductAdapter2 = new LatestSellingProductAdapter(topSellingResponse.getData(), cartDataTableList, getActivity());

                        binding.rvWeeklyDeals.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
                        binding.rvWeeklyDeals.setAdapter(latestSellingProductAdapter2);

                        LatestSellingProductAdapter latestSellingProductAdapter3 = new LatestSellingProductAdapter(topSellingResponse.getData(), cartDataTableList, getActivity());

                        binding.rvMonthlyDeals.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
                        binding.rvMonthlyDeals.setAdapter(latestSellingProductAdapter3);

                        LatestSellingProductAdapter latestSellingProductAdapter4 = new LatestSellingProductAdapter(topSellingResponse.getData(), cartDataTableList, getActivity());

                        binding.rvBundleOffers.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
                        binding.rvBundleOffers.setAdapter(latestSellingProductAdapter4);

                        LatestSellingProductAdapter latestSellingProductAdapter5 = new LatestSellingProductAdapter(topSellingResponse.getData(), cartDataTableList, getActivity());

                        binding.rvMostPopular.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
                        binding.rvMostPopular.setAdapter(latestSellingProductAdapter5);


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


    private void clickListner() {

        if (timeDealAdapter != null) {

            timeDealAdapter.setOnItemClickListener(new TopSellingAdapter.OnItemClickListener() {
                @Override
                public void onAddtoCartClick(View view, Product product) {


                    int id = product.getProduct_id();

                    Log.d("id", String.valueOf(id));

                    CartDataTable cartDataTable = new CartDataTable();
                    cartDataTable.setProductId(id);
                    cartDataTable.setProductQuantity(1);
                    cartDataTable.setUserId(userId);

                    cartViewModel.insertCart(cartDataTable);
//                Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_SHORT).show();

                    int quantity = 1;
                    cartViewModel.addProductToCart(userId, id, quantity).observe(getActivity(), new Observer<CartResponse>() {
                        @Override
                        public void onChanged(CartResponse cartResponse) {

                            progressDialog.show();
                            if (cartResponse != null) {
                                progressDialog.dismiss();
                                if (cartResponse.getMessage().equals("Product added successfully")) {

                                    Log.d("id2", String.valueOf(id));

//                                    Toast.makeText(getActivity(), "Data added in API", Toast.LENGTH_SHORT).show();

                                    getCartItemDb();
                                    if (cartDataTableList.size() > 0) {
                                        timeDealAdapter.setList(cartDataTableList);
                                    } else {
                                        Toast.makeText(getActivity(), "cartDataTableList empty", Toast.LENGTH_SHORT).show();
                                    }
                                    progressDialog.dismiss();
                                } else {

                                    CartDataTable cartDataTable1 = cartViewModel.getCart2(id, userId);

                                    if (cartDataTable1 != null) {
                                        cartViewModel.deleteCart(cartDataTable1);
                                    }
                                    progressDialog.dismiss();

                                    Toast.makeText(getActivity(), cartResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                    progressDialog.dismiss();

                }

                @Override
                public void updateCartData(int productId, int quantity, int position, View view) {

                    Log.d("cartPos", String.valueOf(position));
                    pId = productId;
                    pQun = quantity;
                    pos = position;

                    progressDialog.show();

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
                            // cartViewModel.CartforUpdate(pQun,userId,pId);

                            Toast.makeText(getActivity(), "updated!", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        progressDialog.show();
                        Toast.makeText(getActivity(), "cartDataTable1 is NULL", Toast.LENGTH_SHORT).show();
                    }


                    cartViewModel.updateCart(pId, pQun, userId).observe(getActivity(), new Observer<UpdateCartResponse>() {
                        @Override
                        public void onChanged(UpdateCartResponse updateCartResponse) {
                            progressDialog.dismiss();
                            if (updateCartResponse != null) {
                                progressDialog.dismiss();
                                if (updateCartResponse.getData() != null) {
                                    progressDialog.dismiss();

                                    if (updateCartResponse.getData().size() > 0) {
                                        Toast.makeText(getActivity(), "API updated", Toast.LENGTH_SHORT).show();
                                    } else {

                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "getData().size() empty", Toast.LENGTH_SHORT).show();

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
                                    Toast.makeText(getActivity(), "getData().size() null", Toast.LENGTH_SHORT).show();

                                    CartDataTable cartDataTable1 = cartViewModel.getCart2(pId, userId);

                                    if (cartDataTable1 != null) {

                                        CartDataTable cartDataTable = new CartDataTable();
                                        cartDataTable.setProductQuantity(pQun);
                                        cartDataTable.setProductId(pId);
                                        cartDataTable.setCartId(cartDataTable1.getCartId());
                                        cartDataTable.setUserId(userId);
                                        cartViewModel.deleteCart(cartDataTable);

                                    } else {
                                        Toast.makeText(getActivity(), "cartdata Null", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            });
        }
        if (latestSellingProductAdapter != null) {
            latestSellingProductAdapter.setOnItemClickListener(new TopSellingAdapter.OnItemClickListener() {
                @Override
                public void onAddtoCartClick(View view, Product product) {


                    int id = product.getProduct_id();

                    Log.d("id", String.valueOf(id));

                    CartDataTable cartDataTable = new CartDataTable();
                    cartDataTable.setProductId(id);
                    cartDataTable.setProductQuantity(1);
                    cartDataTable.setUserId(userId);

                    cartViewModel.insertCart(cartDataTable);
//                Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_SHORT).show();

                    int quantity = 1;
                    cartViewModel.addProductToCart(userId, id, quantity).observe(getActivity(), new Observer<CartResponse>() {
                        @Override
                        public void onChanged(CartResponse cartResponse) {

                            progressDialog.show();
                            if (cartResponse != null) {
                                progressDialog.dismiss();
                                if (cartResponse.getMessage().equals("Product added successfully")) {

                                    Log.d("id2", String.valueOf(id));

//                                    Toast.makeText(getActivity(), "Data added in API", Toast.LENGTH_SHORT).show();

                                    getCartItemDb();
                                    if (cartDataTableList.size() > 0) {
                                        timeDealAdapter.setList(cartDataTableList);
                                    } else {
                                        Toast.makeText(getActivity(), "cartDataTableList empty", Toast.LENGTH_SHORT).show();
                                    }
                                    progressDialog.dismiss();
                                } else {

                                    CartDataTable cartDataTable1 = cartViewModel.getCart2(id, userId);

                                    if (cartDataTable1 != null) {
                                        cartViewModel.deleteCart(cartDataTable1);
                                    }
                                    progressDialog.dismiss();

                                    Toast.makeText(getActivity(), cartResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                    progressDialog.dismiss();

                }

                @Override
                public void updateCartData(int productId, int quantity, int position, View view) {

                    Log.d("cartPos", String.valueOf(position));
                    pId = productId;
                    pQun = quantity;
                    pos = position;

                    progressDialog.show();

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
                            // cartViewModel.CartforUpdate(pQun,userId,pId);

                            Toast.makeText(getActivity(), "updated!", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        progressDialog.show();
                        Toast.makeText(getActivity(), "cartDataTable1 is NULL", Toast.LENGTH_SHORT).show();
                    }


                    cartViewModel.updateCart(pId, pQun, userId).observe(getActivity(), new Observer<UpdateCartResponse>() {
                        @Override
                        public void onChanged(UpdateCartResponse updateCartResponse) {
                            progressDialog.dismiss();
                            if (updateCartResponse != null) {
                                progressDialog.dismiss();
                                if (updateCartResponse.getData() != null) {
                                    progressDialog.dismiss();

                                    if (updateCartResponse.getData().size() > 0) {
                                        Toast.makeText(getActivity(), "API updated", Toast.LENGTH_SHORT).show();
                                    } else {

                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "getData().size() empty", Toast.LENGTH_SHORT).show();

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
                                    Toast.makeText(getActivity(), "getData().size() null", Toast.LENGTH_SHORT).show();

                                    CartDataTable cartDataTable1 = cartViewModel.getCart2(pId, userId);

                                    if (cartDataTable1 != null) {

                                        CartDataTable cartDataTable = new CartDataTable();
                                        cartDataTable.setProductQuantity(pQun);
                                        cartDataTable.setProductId(pId);
                                        cartDataTable.setCartId(cartDataTable1.getCartId());
                                        cartDataTable.setUserId(userId);
                                        cartViewModel.deleteCart(cartDataTable);

                                    } else {
                                        Toast.makeText(getActivity(), "cartdata Null", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            });
        }
    }
}