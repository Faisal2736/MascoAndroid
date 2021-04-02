package com.semicolons.masco.pk.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.AppClass;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.Utils.Utilities;
import com.semicolons.masco.pk.adapters.DealsSliderAdapter;
import com.semicolons.masco.pk.adapters.DevicesParentRecyclerAdapter;
import com.semicolons.masco.pk.adapters.SliderAdapterExample;
import com.semicolons.masco.pk.adapters.TopSellingAdapter;
import com.semicolons.masco.pk.dataModels.CartDataTable;
import com.semicolons.masco.pk.dataModels.CartResponse;
import com.semicolons.masco.pk.dataModels.HomeDataModel;
import com.semicolons.masco.pk.dataModels.ImagesListDM;
import com.semicolons.masco.pk.dataModels.Points;
import com.semicolons.masco.pk.dataModels.Product;
import com.semicolons.masco.pk.dataModels.SliderImagesResponse;
import com.semicolons.masco.pk.dataModels.TopSellingResponse;
import com.semicolons.masco.pk.dataModels.UpdateCartResponse;
import com.semicolons.masco.pk.viewModels.CartViewModel;
import com.semicolons.masco.pk.viewModels.HomeFragmentViewModel;
import com.semicolons.masco.pk.viewModels.PointsViewModel;

public class HomeFragment extends Fragment {

    HomeFragmentViewModel homeFragmentViewModel;

    View view;
    SliderView sliderView;
    SliderView imageSliderDeals;

    RecyclerView recyclerView;
    DevicesParentRecyclerAdapter homeParentRecyclerAdapter;
    boolean topSellingComplete = false;
    boolean latestComplete = false;
    boolean allproductsComplete = false;
    HomeDataModel homeDataModel = new HomeDataModel();
    CartViewModel cartViewModel;
    ProgressDialog progressDialog;
    RecyclerView recylerTimeDeal;
    TopSellingAdapter timeDealAdapter;
    int[] imgResLounge = {R.drawable.ic_get_free, R.drawable.ic_seasonal_deals, R.drawable.ic_grocery_deals,
            R.drawable.ic_stationary_deals};
    //CategoryNames
    String[] imgNamesLounge = {"Men\nLounge", "Women\nLounge", "Cosmetic\nLounge", "Garments\nLounge"};
    //List student ERP OBJECT
    ArrayList<ImagesListDM> loungeList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    private ArrayList<String> listTitleRecycler = new ArrayList<>();
    private TextView totalPointsTextView;
    private TextView totalPrice;
    private PointsViewModel pointsViewModel;


    private View viewParent;
    private boolean isLogin;
    private int userId;

    private List<CartDataTable> cartDataTableList = new ArrayList<>();
    // private List<CartProduct> cartProductList = new ArrayList<>();

    private int pos;
    private int pQun;
    private int pId;
    private ImageView imgSuperStore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews();
        initListeners();
        isLogin = sharedPreferences.getBoolean(Constants.USER_IS_LOGIN, false);
        userId = sharedPreferences.getInt(Constants.USER_ID, 0);

        Log.d("USER_ID", String.valueOf(userId));

        getSliderImages();
        //getCartItem(); //api
        getCartItemDb();

        if (Constants.topSellingProduct != null) {
            if (!Constants.topSellingProduct.isEmpty()) {
                recylerTimeDeal.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                //  timeDealAdapter = new TopSellingAdapter(Constants.topSellingProduct, cartProductList, getActivity());
                timeDealAdapter = new TopSellingAdapter(Constants.topSellingProduct, cartDataTableList, getActivity());
                recylerTimeDeal.setAdapter(timeDealAdapter);

            }
        }
        else {
            getTopSellingProducts(1);
        }

        if (Constants.latestProduct != null) {
            if (!Constants.latestProduct.isEmpty()) {
                homeDataModel.setLatestList(Constants.latestProduct);
                homeParentRecyclerAdapter = new DevicesParentRecyclerAdapter(getActivity(), homeDataModel);
                recyclerView.setAdapter(homeParentRecyclerAdapter);
            }
        } else {
            getLatestProducts(1);
        }
        if (Constants.allProducts != null) {
            if (!Constants.allProducts.isEmpty()) {
                homeDataModel.setAllProductList(Constants.allProducts);
                homeParentRecyclerAdapter = new DevicesParentRecyclerAdapter(getActivity(), homeDataModel);
                recyclerView.setAdapter(homeParentRecyclerAdapter);
            }
        } else {
            getAllProducts(1);
        }


        return view;
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
                    //recylerTimeDeal.setAdapter(timeDealAdapter);
                    //timeDealAdapter.notifyDataSetChanged();

                } else {
                    Log.d("CartData", "NULL");
                    Toast.makeText(getActivity(), "CartData Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


//    private void getCartItem() {
//
//        cartViewModel.getCartList(userId).observe(getActivity(), new Observer<CartProductResponse>() {
//            @Override
//            public void onChanged(CartProductResponse cartProductResponse) {
//
//                if (cartProductResponse != null) {
//                    if (cartProductResponse.getData() != null) {
//                        cartProductList = cartProductResponse.getData();
//
//                        timeDealAdapter = new TopSellingAdapter(Constants.topSellingProduct, cartProductList, getActivity());
//                     //   timeDealAdapter = new TopSellingAdapter(Constants.topSellingProduct, cartDataTableList, getActivity());
//                        recylerTimeDeal.setAdapter(timeDealAdapter);
//
//                        // prepareCartProductList(cartProductResponse.getData());
//
//                        Toast.makeText(getActivity(), "insidegetcart ", Toast.LENGTH_SHORT).show();
//                        for (int i = 0; i < cartProductList.size(); i++) {
//
//                            Log.d("cartProductList", cartProductList.get(i).getQuantity() + ": " +
//                                    cartProductList.get(i).getProductId());
//                        }
//
//
//                    } else {
//
//                        ArrayList<CartProduct> list = new ArrayList<>();
//                        //prepareCartProductList(list);
//                        cartProductList = list;
//                        Log.d("cartProductList", "empty");
//
//
//                        for (int i = 0; i < cartProductList.size(); i++) {
//                            Log.d("listdatat", cartProductList.get(i).getQuantity() + ": " +
//                                    cartProductList.get(i).getProductId());
//                        }
//
//                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Server not responding", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    @Override
    public void onResume() {
        // getCartItem();
        getCartItemDb();
        getTopSellingProducts(1);

        super.onResume();
    }


    private void clickListner() {

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
                cartViewModel.addProductToCart(userId, id, quantity).observe(HomeFragment.this, new Observer<CartResponse>() {
                    @Override
                    public void onChanged(CartResponse cartResponse) {

                        progressDialog.show();
                        if (cartResponse != null) {
                            progressDialog.dismiss();
                            if (cartResponse.getMessage().equals("Product added successfully")) {

                                Log.d("id2", String.valueOf(id));

//                                Toast.makeText(getActivity(), "Data added in API", Toast.LENGTH_SHORT).show();

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


                cartViewModel.updateCart(pId, pQun, userId).observe(HomeFragment.this, new Observer<UpdateCartResponse>() {
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


    private void getSliderImages() {

        if (AppClass.isOnline(getActivity())) {

            homeFragmentViewModel.getSliderImages().observe(getActivity(), new Observer<SliderImagesResponse>() {

                @Override
                public void onChanged(SliderImagesResponse sliderImagesResponse) {

                    if (sliderImagesResponse.getStatus() == 1) {

                        SliderAdapterExample adapter = new SliderAdapterExample(getContext(), sliderImagesResponse.getData());

                        sliderView.setSliderAdapter(adapter);

//                        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                        sliderView.setIndicatorSelectedColor(Color.WHITE);
                        sliderView.setIndicatorUnselectedColor(Color.GRAY);
                        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
                        sliderView.startAutoCycle();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "" + sliderImagesResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            AppClass.offline(getActivity());
        }
    }

    private void getTopSellingProducts(int page) {

        if (AppClass.isOnline(getActivity())) {

            progressDialog.show();

            homeFragmentViewModel.getTopSellingProducts(page).observe(getActivity(), new Observer<TopSellingResponse>() {

                @Override
                public void onChanged(TopSellingResponse topSellingResponse) {

                    if (topSellingResponse.getStatus() == 1) {

                        progressDialog.dismiss();

                        topSellingComplete = true;

                        Constants.topSellingProduct = topSellingResponse.getData();

                        recylerTimeDeal.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        timeDealAdapter = new TopSellingAdapter(topSellingResponse.getData(), cartDataTableList, getActivity());
                        //   timeDealAdapter = new TopSellingAdapter(topSellingResponse.getData(), cartProductList, getActivity());
                        recylerTimeDeal.setAdapter(timeDealAdapter);

                        clickListner();


                        if (latestComplete || allproductsComplete) {
                            homeParentRecyclerAdapter.notifyDataSetChanged();
                        } else {
                            homeParentRecyclerAdapter = new DevicesParentRecyclerAdapter(getActivity(), homeDataModel);
                            recyclerView.setAdapter(homeParentRecyclerAdapter);
                        }

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "" + topSellingResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

                        latestComplete = true;

                        Constants.latestProduct = topSellingResponse.getData();
                        homeDataModel.setLatestList(topSellingResponse.getData());

                        if (topSellingComplete || allproductsComplete) {
                            homeParentRecyclerAdapter.notifyDataSetChanged();
                        } else {
                            homeParentRecyclerAdapter = new DevicesParentRecyclerAdapter(getActivity(), homeDataModel);
                            recyclerView.setAdapter(homeParentRecyclerAdapter);
                        }

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "" + topSellingResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            AppClass.offline(getActivity());
        }
    }

    private void getAllProducts(int page) {

        if (AppClass.isOnline(getActivity())) {

            progressDialog.show();

            homeFragmentViewModel.getAllProducts(page).observe(getActivity(), new Observer<TopSellingResponse>() {

                @Override
                public void onChanged(TopSellingResponse topSellingResponse) {

                    if (topSellingResponse.getStatus() == 1) {

                        progressDialog.dismiss();

                        allproductsComplete = true;
                        Constants.allProducts = topSellingResponse.getData();
                        homeDataModel.setAllProductList(topSellingResponse.getData());

                        if (topSellingComplete || latestComplete) {
                            homeParentRecyclerAdapter.notifyDataSetChanged();
                        } else {
                            homeParentRecyclerAdapter = new DevicesParentRecyclerAdapter(getActivity(), homeDataModel);
                            recyclerView.setAdapter(homeParentRecyclerAdapter);
                        }

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "" + topSellingResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            AppClass.offline(getActivity());
        }
    }

    private void getPoints(int userId) {
        pointsViewModel.getUserPointsById(userId).observe(getActivity(), new Observer<Points>() {
            @Override
            public void onChanged(Points points) {
                progressDialog.dismiss();
                if (points.getStatus() == 1) {
                    progressDialog.dismiss();
                    if (points.getTotalPoints() != null && points.getTotalDiscount() != null) {
                        totalPointsTextView.setText(points.getTotalPoints() + "");
                        totalPrice.setText(points.getTotalDiscount() + "");
                    } else {
                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), points.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews() {

        homeFragmentViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        sliderView = view.findViewById(R.id.imageSlider);
        imageSliderDeals = view.findViewById(R.id.imageSliderDeals);
        recyclerView = view.findViewById(R.id.recyclerView);
        recylerTimeDeal = view.findViewById(R.id.recy_timeDeal);
        totalPointsTextView = view.findViewById(R.id.pointsTextView);
        totalPrice = view.findViewById(R.id.totalPrice);
        imgSuperStore = view.findViewById(R.id.imgSuperStore);

        pointsViewModel = new ViewModelProvider(this).get(PointsViewModel.class);
        sharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt(Constants.USER_ID, 0);

        showGif(imgSuperStore);

        listTitleRecycler.add(0, "Top Selling");
        listTitleRecycler.add(1, "Latest Products");
        listTitleRecycler.add(2, "All Products");
        homeDataModel.setTitleList(listTitleRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        progressDialog = new ProgressDialog(this.getContext(), R.style.exitDialogTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        getPoints(userId);
    }

    private void initListeners() {

        addLoungeToList();
        DealsSliderAdapter adapter = new DealsSliderAdapter(getContext(), loungeList);
        imageSliderDeals.setSliderAdapter(adapter);
        imageSliderDeals.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSliderDeals.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSliderDeals.setScrollTimeInSec(4); //set scroll delay in seconds :
        imageSliderDeals.startAutoCycle();


      /*  recylerTimeDeal.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        timeDealAdapter = new TimeDealAdapter(getActivity(), timeDealList);
        recylerTimeDeal.setAdapter(timeDealAdapter);

        recylerLounge.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        loungeAdapter = new LoungeDealAdapter(getActivity(), loungeList);
        recylerLounge.setAdapter(loungeAdapter);

        recylerStore.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        loungeAdapter = new LoungeDealAdapter(getActivity(), storeList);
        recylerStore.setAdapter(loungeAdapter);*/
    }

    public void addLoungeToList() {

        int i = 0;

        for (String name : imgNamesLounge) {
            ImagesListDM imagesListDM = new ImagesListDM();
            imagesListDM.setImg(imgResLounge[i]);
            imagesListDM.setNameImg(name);
            loungeList.add(imagesListDM);
            i++;

        }
    }

    public void showGif(ImageView view) {
        Glide.with(this).load(R.drawable.ic_super_store_gif).into(view);
    }


}
