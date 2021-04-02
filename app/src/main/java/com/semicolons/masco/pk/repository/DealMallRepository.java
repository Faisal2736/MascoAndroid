package com.semicolons.masco.pk.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.Utils.SingleLiveEvent;
import com.semicolons.masco.pk.dataModels.AddFavResponse;
import com.semicolons.masco.pk.dataModels.CartDao;
import com.semicolons.masco.pk.dataModels.CartDataTable;
import com.semicolons.masco.pk.dataModels.CartDatabase;
import com.semicolons.masco.pk.dataModels.CartProductResponse;
import com.semicolons.masco.pk.dataModels.CartResponse;
import com.semicolons.masco.pk.dataModels.CategoryDM;
import com.semicolons.masco.pk.dataModels.DeleteFavResponse;
import com.semicolons.masco.pk.dataModels.FavDao;
import com.semicolons.masco.pk.dataModels.FavDataTable;
import com.semicolons.masco.pk.dataModels.OrderDetailResponse;
import com.semicolons.masco.pk.dataModels.OrdersResponse;
import com.semicolons.masco.pk.dataModels.PlaceOrderResponse;
import com.semicolons.masco.pk.dataModels.Points;
import com.semicolons.masco.pk.dataModels.ProfileUpdateResponse;
import com.semicolons.masco.pk.dataModels.SignUpResponse;
import com.semicolons.masco.pk.dataModels.SliderImagesResponse;
import com.semicolons.masco.pk.dataModels.TopSellingResponse;
import com.semicolons.masco.pk.dataModels.UpdateCartResponse;
import com.semicolons.masco.pk.retrofit.ApiClient;
import com.semicolons.masco.pk.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealMallRepository {

    ApiInterface apiInterface;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferencesCart;
    SharedPreferences.Editor editorCart;
    private Application application;

    private SingleLiveEvent<TopSellingResponse> topSellingMutableLiveData = new SingleLiveEvent<>();
    private SingleLiveEvent<TopSellingResponse> latestMutableLiveData = new SingleLiveEvent<>();
    private SingleLiveEvent<TopSellingResponse> allProductsMutableLiveData = new SingleLiveEvent<>();
    private SingleLiveEvent<TopSellingResponse> allProductsListMutableLiveData = new SingleLiveEvent<>();
    private SingleLiveEvent<CategoryDM> categoryDMSingleLiveEvent = new SingleLiveEvent<>();

    private SingleLiveEvent<CategoryDM> subCategoryDMSingleLiveEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<CategoryDM> subCategoryDMSingleLiveEvent1 = new SingleLiveEvent<>();
    private SingleLiveEvent<CategoryDM> subCategoryDMSingleLiveEvent2 = new SingleLiveEvent<>();
    private SingleLiveEvent<CategoryDM> subCategoryDMSingleLiveEvent3 = new SingleLiveEvent<>();
    private SingleLiveEvent<CategoryDM> subCategoryDMSingleLiveEvent4 = new SingleLiveEvent<>();
    private SingleLiveEvent<CategoryDM> subCategoryDMSingleLiveEvent5 = new SingleLiveEvent<>();
    private SingleLiveEvent<CategoryDM> subCategoryDMSingleLiveEvent6 = new SingleLiveEvent<>();
    private SingleLiveEvent<CategoryDM> subCategoryDMSingleLiveEvent7 = new SingleLiveEvent<>();
    private SingleLiveEvent<CategoryDM> subCategoryDMSingleLiveEvent8 = new SingleLiveEvent<>();

    private SingleLiveEvent<SliderImagesResponse> sliderImagesMutableLiveData = new SingleLiveEvent<>();
    private SingleLiveEvent<OrdersResponse> orderSingleLiveEvent = new SingleLiveEvent<>();
    private MutableLiveData<Points> pointsMutableLiveData = new MutableLiveData<>();
    private SingleLiveEvent<ProfileUpdateResponse> profileUpdateResponseSingleLiveEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<CartResponse> cartResponseSingleLiveEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<CartProductResponse> cartProductResponseSingleLiveEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<SignUpResponse> loginEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<UpdateCartResponse> updateCartResponseSingleLiveEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<PlaceOrderResponse> placeOrderResponseSingleLiveEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<AddFavResponse> addFavResponseSingleLiveEvent=new SingleLiveEvent<>();
    private SingleLiveEvent<TopSellingResponse> favouritesSingleLiveEvent=new SingleLiveEvent<>();
    private SingleLiveEvent<DeleteFavResponse> deleteFavResponseSingleLiveEvent=new SingleLiveEvent<>();
    private SingleLiveEvent<OrderDetailResponse> orderDetailResponseSingleLiveEvent=new SingleLiveEvent<>();

    private CartDao cartDao;
    private FavDao favDao;
    private LiveData<List<Integer>> getProductIds;
    private LiveData<List<Integer>> getCartCount;


    public DealMallRepository(Application application) {

        this.application = application;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        sharedPreferences = application.getApplicationContext().getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sharedPreferencesCart = application.getApplicationContext().getSharedPreferences(Constants.CART_PREFERENCE, Context.MODE_PRIVATE);
        editorCart = sharedPreferencesCart.edit();
        CartDatabase cartDatabase=CartDatabase.getInstance(application);
        cartDao=cartDatabase.cartDao();
        favDao=cartDatabase.favDao();
    }

    public SingleLiveEvent<TopSellingResponse> getTopSellingProducts(int page) {

        Call<TopSellingResponse> yearsCall = apiInterface.getTopSellingProducts(page);

        yearsCall.enqueue(new Callback<TopSellingResponse>() {
            @Override
            public void onResponse(Call<TopSellingResponse> call, Response<TopSellingResponse> response) {

                TopSellingResponse yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                topSellingMutableLiveData.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                topSellingMutableLiveData.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            topSellingMutableLiveData.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new TopSellingResponse();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        topSellingMutableLiveData.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new TopSellingResponse();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    topSellingMutableLiveData.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<TopSellingResponse> call, Throwable t) {
                TopSellingResponse yearsResponse = new TopSellingResponse();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                topSellingMutableLiveData.setValue(yearsResponse);
            }
        });

        return topSellingMutableLiveData;
    }

    public SingleLiveEvent<TopSellingResponse> getLatestProducts(int page) {

        Call<TopSellingResponse> yearsCall = apiInterface.getLatestProducts(page);

        yearsCall.enqueue(new Callback<TopSellingResponse>() {
            @Override
            public void onResponse(Call<TopSellingResponse> call, Response<TopSellingResponse> response) {

                TopSellingResponse yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                latestMutableLiveData.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                latestMutableLiveData.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            latestMutableLiveData.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new TopSellingResponse();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        latestMutableLiveData.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new TopSellingResponse();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    latestMutableLiveData.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<TopSellingResponse> call, Throwable t) {
                TopSellingResponse yearsResponse = new TopSellingResponse();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                latestMutableLiveData.setValue(yearsResponse);
            }
        });

        return latestMutableLiveData;
    }

    public SingleLiveEvent<TopSellingResponse> getAllProducts(int page) {

        Call<TopSellingResponse> yearsCall = apiInterface.getAllProducts(page);

        yearsCall.enqueue(new Callback<TopSellingResponse>() {
            @Override
            public void onResponse(Call<TopSellingResponse> call, Response<TopSellingResponse> response) {

                TopSellingResponse yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                allProductsMutableLiveData.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                allProductsMutableLiveData.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            allProductsMutableLiveData.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new TopSellingResponse();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        allProductsMutableLiveData.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new TopSellingResponse();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    allProductsMutableLiveData.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<TopSellingResponse> call, Throwable t) {
                TopSellingResponse yearsResponse = new TopSellingResponse();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                allProductsMutableLiveData.setValue(yearsResponse);
            }
        });

        return allProductsMutableLiveData;
    }



    public SingleLiveEvent<SliderImagesResponse> getSliderImages() {

        Call<SliderImagesResponse> yearsCall = apiInterface.getSliderImages();

        yearsCall.enqueue(new Callback<SliderImagesResponse>() {
            @Override
            public void onResponse(Call<SliderImagesResponse> call, Response<SliderImagesResponse> response) {

                SliderImagesResponse yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                sliderImagesMutableLiveData.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                sliderImagesMutableLiveData.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            sliderImagesMutableLiveData.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new SliderImagesResponse();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        sliderImagesMutableLiveData.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new SliderImagesResponse();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    sliderImagesMutableLiveData.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<SliderImagesResponse> call, Throwable t) {
                SliderImagesResponse yearsResponse = new SliderImagesResponse();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                sliderImagesMutableLiveData.setValue(yearsResponse);
            }
        });

        return sliderImagesMutableLiveData;
    }

    public SingleLiveEvent<CategoryDM> getAllCategories() {

        Call<CategoryDM> yearsCall = apiInterface.getAllCategories();

        yearsCall.enqueue(new Callback<CategoryDM>() {
            @Override
            public void onResponse(Call<CategoryDM> call, Response<CategoryDM> response) {

                CategoryDM yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                categoryDMSingleLiveEvent.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                categoryDMSingleLiveEvent.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            categoryDMSingleLiveEvent.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new CategoryDM();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        categoryDMSingleLiveEvent.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new CategoryDM();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    categoryDMSingleLiveEvent.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<CategoryDM> call, Throwable t) {
                CategoryDM yearsResponse = new CategoryDM();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                categoryDMSingleLiveEvent.setValue(yearsResponse);
            }
        });

        return categoryDMSingleLiveEvent;
    }

    public SingleLiveEvent<ProfileUpdateResponse> updateUserProfile(int User_id,String first_name,String address,String mob_no,String pswd){
        Call<ProfileUpdateResponse> profileUpdateResponseCall=apiInterface.updateUserProfile(User_id, first_name, address, mob_no, pswd);

        profileUpdateResponseCall.enqueue(new Callback<ProfileUpdateResponse>() {
            @Override
            public void onResponse(Call<ProfileUpdateResponse> call, Response<ProfileUpdateResponse> response) {
                ProfileUpdateResponse profileUpdateResponse=response.body();
                if(response.isSuccessful()){
                    if(profileUpdateResponse!=null){
                        if(profileUpdateResponse.getData()!=null){
                            profileUpdateResponse.setStatus(1);
                            profileUpdateResponse.setSuccess("Profile Updated Successfully");
                            profileUpdateResponseSingleLiveEvent.setValue(profileUpdateResponse);
                        }else {
                            profileUpdateResponse.setStatus(0);
                            profileUpdateResponse.setSuccess("No Data Found");
                            profileUpdateResponseSingleLiveEvent.setValue(profileUpdateResponse);
                        }
                    }else {
                        profileUpdateResponse=new ProfileUpdateResponse();
                        profileUpdateResponse.setStatus(0);
                        profileUpdateResponse.setSuccess("Something went wrong. Pease Try again");
                        profileUpdateResponseSingleLiveEvent.setValue(profileUpdateResponse);
                    }
                }else {
                    profileUpdateResponse=new ProfileUpdateResponse();
                    profileUpdateResponse.setStatus(0);
                    profileUpdateResponse.setSuccess("Something went wrong. Pease Try again");
                    profileUpdateResponseSingleLiveEvent.setValue(profileUpdateResponse);
                }
            }

            @Override
            public void onFailure(Call<ProfileUpdateResponse> call, Throwable t) {
                ProfileUpdateResponse profileUpdateResponse=new ProfileUpdateResponse();
                profileUpdateResponse.setStatus(0);
                profileUpdateResponse.setSuccess("Server not responding");
                profileUpdateResponseSingleLiveEvent.setValue(profileUpdateResponse);
            }
        });

        return profileUpdateResponseSingleLiveEvent;
    }

    public SingleLiveEvent<CartProductResponse> getCartList(int userId){
        Call<CartProductResponse> cartProductResponseCall=apiInterface.getCartList(userId);

        cartProductResponseCall.enqueue(new Callback<CartProductResponse>() {
            @Override
            public void onResponse(Call<CartProductResponse> call, Response<CartProductResponse> response) {
                CartProductResponse cartProductResponse=response.body();

                if(response.isSuccessful()){
                    if(cartProductResponse!=null){
                        if(cartProductResponse.getData()!=null){
                            if(cartProductResponse.getData().size()>0){
                                cartProductResponseSingleLiveEvent.setValue(cartProductResponse);
                            }else {
                                //cartProductResponse.setMessage("No Data Found");
                                cartProductResponseSingleLiveEvent.setValue(cartProductResponse);
                            }
                        }else {
                            //cartProductResponse.setMessage("No Data Found");
                            cartProductResponseSingleLiveEvent.setValue(cartProductResponse);
                        }
                    }else {
                        cartProductResponse=new CartProductResponse();
                        //cartProductResponse.setMessage("No Data Found");
                        cartProductResponseSingleLiveEvent.setValue(cartProductResponse);
                    }
                }else {
                    cartProductResponse=new CartProductResponse();
                    //cartProductResponse.setMessage("No Data Found");
                    cartProductResponseSingleLiveEvent.setValue(cartProductResponse);
                }
            }

            @Override
            public void onFailure(Call<CartProductResponse> call, Throwable t) {
                CartProductResponse cartProductResponse=new CartProductResponse();
                //cartProductResponse.setMessage("Server Not Responding");
                cartProductResponseSingleLiveEvent.setValue(cartProductResponse);
            }
        });

        return cartProductResponseSingleLiveEvent;
    }

    public SingleLiveEvent<CartResponse> addProductToCart(int userId,int productId, int productQuantity){

        Call<CartResponse> cartResponseCall=apiInterface.addProductToCart(userId,productId,productQuantity);

        cartResponseCall.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                CartResponse cartResponse=response.body();

                if(response.isSuccessful()){
                    if(cartResponse!=null){
                        if(cartResponse.getMessage().equals("Product added successfully")){
                            cartResponseSingleLiveEvent.setValue(cartResponse);
                        }else {
                            cartResponseSingleLiveEvent.setValue(cartResponse);
                        }
                    }else {
                        cartResponse=new CartResponse();
                        cartResponse.setMessage("Something went wrong.Please try again");
                    }
                }else {
                    cartResponse=new CartResponse();
                    cartResponse.setMessage("Something went wrong.Please try again");
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                CartResponse cartResponse=new CartResponse();
                cartResponse.setMessage("Server not Responding");
                cartResponseSingleLiveEvent.setValue(cartResponse);
            }
        });


        return cartResponseSingleLiveEvent;
    }

    public MutableLiveData<Points> getUserPointsById(int userId){
        Call<Points> pointsCall=apiInterface.getUserPointsById(userId);

        pointsCall.enqueue(new Callback<Points>() {
            @Override
            public void onResponse(Call<Points> call, Response<Points> response) {
                Points points=response.body();

                if(response.isSuccessful()){
                    if(points!=null){
                        points.setStatus(1);
                        pointsMutableLiveData.setValue(points);
                    }else {
                        points.setStatus(1);
                        pointsMutableLiveData.setValue(points);
                    }
                }else {
                    points.setStatus(1);
                    pointsMutableLiveData.setValue(points);
                }
            }

            @Override
            public void onFailure(Call<Points> call, Throwable t) {
                Points points=new Points();
                points.setStatus(0);
                points.setMessage("Server Not Responding");
                pointsMutableLiveData.setValue(points);
            }
        });

        return pointsMutableLiveData;
    }

    public SingleLiveEvent<OrdersResponse> getAllOrders(int user_id){
        Call<OrdersResponse> orderCall=apiInterface.getAllOrders(user_id);

        orderCall.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                OrdersResponse ordersResponse=response.body();

                if(response.isSuccessful()){
                    if(ordersResponse!=null){
                        if(ordersResponse.getOrderDetail()!=null){
                            if (ordersResponse.getOrderDetail().size() > 0){
                                //ordersResponse.setStatus(1);
                                orderSingleLiveEvent.setValue(ordersResponse);
                            }else {
                                //ordersResponse.setStatus(0);
                                //ordersResponse.setMessage("No data found");
                                orderSingleLiveEvent.setValue(ordersResponse);
                            }
                        }else {
                            //ordersResponse.setStatus(0);
                            //ordersResponse.setMessage("No data found");
                            orderSingleLiveEvent.setValue(ordersResponse);
                        }
                    }else {
                        ordersResponse = new OrdersResponse();
                        //ordersResponse.setStatus(0);
                       // ordersResponse.setMessage("Something went wrong. Pease Try again");
                        orderSingleLiveEvent.setValue(ordersResponse);
                    }
                }else {
                    ordersResponse = new OrdersResponse();
                    //ordersResponse.setStatus(0);
                    //ordersResponse.setMessage("Something went wrong. Pease Try again");
                    orderSingleLiveEvent.setValue(ordersResponse);
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                OrdersResponse orderResponse = new OrdersResponse();
                //orderResponse.setStatus(0);
                //orderResponse.setMessage("Server not responding");
                orderSingleLiveEvent.setValue(orderResponse);
            }
        });

        return orderSingleLiveEvent;
    }

    public SingleLiveEvent<OrderDetailResponse> getOrderDetail(int userId) {

        Call<OrderDetailResponse> call=apiInterface.getOrderDetail(userId);

        call.enqueue(new Callback<OrderDetailResponse>() {
            @Override
            public void onResponse(Call<OrderDetailResponse> call, Response<OrderDetailResponse> response) {

                OrderDetailResponse orderDetailResponse=response.body();

                if(response.isSuccessful()){
                    if(orderDetailResponse!=null){
                        if(orderDetailResponse.getOrderDetail()!=null){
                            if(orderDetailResponse.getOrderDetail().size()>0){

                                orderDetailResponseSingleLiveEvent.setValue(orderDetailResponse);

                            }else {
                                //orderDetailResponse.setMessage("No Data Available");
                                orderDetailResponseSingleLiveEvent.setValue(orderDetailResponse);
                            }
                        }else {
                           // orderDetailResponse.setMessage("No Data Available");
                            orderDetailResponseSingleLiveEvent.setValue(orderDetailResponse);
                        }
                    }else {
                        orderDetailResponse=new OrderDetailResponse();
                        //orderDetailResponse.setMessage("Something went wrong");
                        orderDetailResponseSingleLiveEvent.setValue(orderDetailResponse);
                    }
                }else {
                    orderDetailResponse=new OrderDetailResponse();
                    //orderDetailResponse.setMessage("Something went wrong");
                    orderDetailResponseSingleLiveEvent.setValue(orderDetailResponse);
                }

            }

            @Override
            public void onFailure(Call<OrderDetailResponse> call, Throwable t) {
                OrderDetailResponse orderDetailResponse=new OrderDetailResponse();
               // orderDetailResponse.setMessage("Server Not Responding");
                orderDetailResponseSingleLiveEvent.setValue(orderDetailResponse);
            }
        });

        return orderDetailResponseSingleLiveEvent;
    }

    public SingleLiveEvent<CategoryDM> getAllSubCategories(int cat_id) {

        Call<CategoryDM> yearsCall = apiInterface.getAllSubCategories(cat_id);

        yearsCall.enqueue(new Callback<CategoryDM>() {
            @Override
            public void onResponse(Call<CategoryDM> call, Response<CategoryDM> response) {

                CategoryDM yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                subCategoryDMSingleLiveEvent.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                subCategoryDMSingleLiveEvent.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            subCategoryDMSingleLiveEvent.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new CategoryDM();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        subCategoryDMSingleLiveEvent.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new CategoryDM();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    subCategoryDMSingleLiveEvent.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<CategoryDM> call, Throwable t) {
                CategoryDM yearsResponse = new CategoryDM();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                subCategoryDMSingleLiveEvent.setValue(yearsResponse);
            }
        });

        return subCategoryDMSingleLiveEvent;
    }

    public SingleLiveEvent<CategoryDM> getAllSubCategories1(int cat_id) {

        Call<CategoryDM> yearsCall = apiInterface.getAllSubCategories(cat_id);

        yearsCall.enqueue(new Callback<CategoryDM>() {
            @Override
            public void onResponse(Call<CategoryDM> call, Response<CategoryDM> response) {

                CategoryDM yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                subCategoryDMSingleLiveEvent1.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                subCategoryDMSingleLiveEvent1.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            subCategoryDMSingleLiveEvent1.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new CategoryDM();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        subCategoryDMSingleLiveEvent1.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new CategoryDM();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    subCategoryDMSingleLiveEvent1.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<CategoryDM> call, Throwable t) {
                CategoryDM yearsResponse = new CategoryDM();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                subCategoryDMSingleLiveEvent1.setValue(yearsResponse);
            }
        });

        return subCategoryDMSingleLiveEvent1;
    }

    public SingleLiveEvent<CategoryDM> getAllSubCategories2(int cat_id) {

        Call<CategoryDM> yearsCall = apiInterface.getAllSubCategories(cat_id);

        yearsCall.enqueue(new Callback<CategoryDM>() {
            @Override
            public void onResponse(Call<CategoryDM> call, Response<CategoryDM> response) {

                CategoryDM yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                subCategoryDMSingleLiveEvent2.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                subCategoryDMSingleLiveEvent2.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            subCategoryDMSingleLiveEvent2.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new CategoryDM();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        subCategoryDMSingleLiveEvent2.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new CategoryDM();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    subCategoryDMSingleLiveEvent2.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<CategoryDM> call, Throwable t) {
                CategoryDM yearsResponse = new CategoryDM();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                subCategoryDMSingleLiveEvent2.setValue(yearsResponse);
            }
        });

        return subCategoryDMSingleLiveEvent2;
    }

    public SingleLiveEvent<CategoryDM> getAllSubCategories3(int cat_id) {

        Call<CategoryDM> yearsCall = apiInterface.getAllSubCategories(cat_id);

        yearsCall.enqueue(new Callback<CategoryDM>() {
            @Override
            public void onResponse(Call<CategoryDM> call, Response<CategoryDM> response) {

                CategoryDM yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                subCategoryDMSingleLiveEvent3.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                subCategoryDMSingleLiveEvent3.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            subCategoryDMSingleLiveEvent3.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new CategoryDM();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        subCategoryDMSingleLiveEvent3.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new CategoryDM();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    subCategoryDMSingleLiveEvent3.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<CategoryDM> call, Throwable t) {
                CategoryDM yearsResponse = new CategoryDM();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                subCategoryDMSingleLiveEvent3.setValue(yearsResponse);
            }
        });

        return subCategoryDMSingleLiveEvent3;
    }

    public SingleLiveEvent<CategoryDM> getAllSubCategories4(int cat_id) {

        Call<CategoryDM> yearsCall = apiInterface.getAllSubCategories(cat_id);

        yearsCall.enqueue(new Callback<CategoryDM>() {
            @Override
            public void onResponse(Call<CategoryDM> call, Response<CategoryDM> response) {

                CategoryDM yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                subCategoryDMSingleLiveEvent4.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                subCategoryDMSingleLiveEvent4.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            subCategoryDMSingleLiveEvent4.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new CategoryDM();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        subCategoryDMSingleLiveEvent4.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new CategoryDM();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    subCategoryDMSingleLiveEvent4.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<CategoryDM> call, Throwable t) {
                CategoryDM yearsResponse = new CategoryDM();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                subCategoryDMSingleLiveEvent4.setValue(yearsResponse);
            }
        });

        return subCategoryDMSingleLiveEvent4;
    }

    public SingleLiveEvent<CategoryDM> getAllSubCategories5(int cat_id) {

        Call<CategoryDM> yearsCall = apiInterface.getAllSubCategories(cat_id);

        yearsCall.enqueue(new Callback<CategoryDM>() {
            @Override
            public void onResponse(Call<CategoryDM> call, Response<CategoryDM> response) {

                CategoryDM yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                subCategoryDMSingleLiveEvent5.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                subCategoryDMSingleLiveEvent5.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            subCategoryDMSingleLiveEvent5.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new CategoryDM();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        subCategoryDMSingleLiveEvent5.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new CategoryDM();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    subCategoryDMSingleLiveEvent5.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<CategoryDM> call, Throwable t) {
                CategoryDM yearsResponse = new CategoryDM();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                subCategoryDMSingleLiveEvent5.setValue(yearsResponse);
            }
        });

        return subCategoryDMSingleLiveEvent5;
    }

    public SingleLiveEvent<CategoryDM> getAllSubCategories6(int cat_id) {

        Call<CategoryDM> yearsCall = apiInterface.getAllSubCategories(cat_id);

        yearsCall.enqueue(new Callback<CategoryDM>() {
            @Override
            public void onResponse(Call<CategoryDM> call, Response<CategoryDM> response) {

                CategoryDM yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                subCategoryDMSingleLiveEvent6.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                subCategoryDMSingleLiveEvent6.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            subCategoryDMSingleLiveEvent6.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new CategoryDM();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        subCategoryDMSingleLiveEvent6.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new CategoryDM();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    subCategoryDMSingleLiveEvent6.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<CategoryDM> call, Throwable t) {
                CategoryDM yearsResponse = new CategoryDM();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                subCategoryDMSingleLiveEvent6.setValue(yearsResponse);
            }
        });

        return subCategoryDMSingleLiveEvent6;
    }

    public SingleLiveEvent<CategoryDM> getAllSubCategories7(int cat_id) {

        Call<CategoryDM> yearsCall = apiInterface.getAllSubCategories(cat_id);

        yearsCall.enqueue(new Callback<CategoryDM>() {
            @Override
            public void onResponse(Call<CategoryDM> call, Response<CategoryDM> response) {

                CategoryDM yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                subCategoryDMSingleLiveEvent7.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                subCategoryDMSingleLiveEvent7.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            subCategoryDMSingleLiveEvent7.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new CategoryDM();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        subCategoryDMSingleLiveEvent7.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new CategoryDM();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    subCategoryDMSingleLiveEvent7.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<CategoryDM> call, Throwable t) {
                CategoryDM yearsResponse = new CategoryDM();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                subCategoryDMSingleLiveEvent7.setValue(yearsResponse);
            }
        });

        return subCategoryDMSingleLiveEvent7;
    }

    public SingleLiveEvent<CategoryDM> getAllSubCategories8(int cat_id) {

        Call<CategoryDM> yearsCall = apiInterface.getAllSubCategories(cat_id);

        yearsCall.enqueue(new Callback<CategoryDM>() {
            @Override
            public void onResponse(Call<CategoryDM> call, Response<CategoryDM> response) {

                CategoryDM yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new CategoryDM();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new CategoryDM();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<CategoryDM> call, Throwable t) {
                CategoryDM yearsResponse = new CategoryDM();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
            }
        });

        return subCategoryDMSingleLiveEvent8;
    }
    public SingleLiveEvent<CategoryDM> getAllSubCategories9(int cat_id) {

        Call<CategoryDM> yearsCall = apiInterface.getAllSubCategories(cat_id);

        yearsCall.enqueue(new Callback<CategoryDM>() {
            @Override
            public void onResponse(Call<CategoryDM> call, Response<CategoryDM> response) {

                CategoryDM yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new CategoryDM();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new CategoryDM();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<CategoryDM> call, Throwable t) {
                CategoryDM yearsResponse = new CategoryDM();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
            }
        });

        return subCategoryDMSingleLiveEvent8;
    }
    public SingleLiveEvent<CategoryDM> getAllSubCategories10(int cat_id) {

        Call<CategoryDM> yearsCall = apiInterface.getAllSubCategories(cat_id);

        yearsCall.enqueue(new Callback<CategoryDM>() {
            @Override
            public void onResponse(Call<CategoryDM> call, Response<CategoryDM> response) {

                CategoryDM yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new CategoryDM();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new CategoryDM();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<CategoryDM> call, Throwable t) {
                CategoryDM yearsResponse = new CategoryDM();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
            }
        });

        return subCategoryDMSingleLiveEvent8;
    }

    public SingleLiveEvent<CategoryDM> getAllSubCategories11(int cat_id) {

        Call<CategoryDM> yearsCall = apiInterface.getAllSubCategories(cat_id);

        yearsCall.enqueue(new Callback<CategoryDM>() {
            @Override
            public void onResponse(Call<CategoryDM> call, Response<CategoryDM> response) {

                CategoryDM yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new CategoryDM();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new CategoryDM();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<CategoryDM> call, Throwable t) {
                CategoryDM yearsResponse = new CategoryDM();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
            }
        });

        return subCategoryDMSingleLiveEvent8;
    }
    public SingleLiveEvent<CategoryDM> getAllSubCategories12(int cat_id) {

        Call<CategoryDM> yearsCall = apiInterface.getAllSubCategories(cat_id);

        yearsCall.enqueue(new Callback<CategoryDM>() {
            @Override
            public void onResponse(Call<CategoryDM> call, Response<CategoryDM> response) {

                CategoryDM yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new CategoryDM();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new CategoryDM();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<CategoryDM> call, Throwable t) {
                CategoryDM yearsResponse = new CategoryDM();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
            }
        });

        return subCategoryDMSingleLiveEvent8;
    }
    public SingleLiveEvent<CategoryDM> getAllSubCategories13(int cat_id) {

        Call<CategoryDM> yearsCall = apiInterface.getAllSubCategories(cat_id);

        yearsCall.enqueue(new Callback<CategoryDM>() {
            @Override
            public void onResponse(Call<CategoryDM> call, Response<CategoryDM> response) {

                CategoryDM yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new CategoryDM();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new CategoryDM();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<CategoryDM> call, Throwable t) {
                CategoryDM yearsResponse = new CategoryDM();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
            }
        });

        return subCategoryDMSingleLiveEvent8;
    }
    public SingleLiveEvent<CategoryDM> getAllSubCategories14(int cat_id) {

        Call<CategoryDM> yearsCall = apiInterface.getAllSubCategories(cat_id);

        yearsCall.enqueue(new Callback<CategoryDM>() {
            @Override
            public void onResponse(Call<CategoryDM> call, Response<CategoryDM> response) {

                CategoryDM yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new CategoryDM();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new CategoryDM();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<CategoryDM> call, Throwable t) {
                CategoryDM yearsResponse = new CategoryDM();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                subCategoryDMSingleLiveEvent8.setValue(yearsResponse);
            }
        });

        return subCategoryDMSingleLiveEvent8;
    }

    public SingleLiveEvent<TopSellingResponse> getProductsList(int sub_Id,int pageNo) {

        Call<TopSellingResponse> yearsCall = apiInterface.getProductsList(sub_Id,pageNo);

        yearsCall.enqueue(new Callback<TopSellingResponse>() {
            @Override
            public void onResponse(Call<TopSellingResponse> call, Response<TopSellingResponse> response) {

                TopSellingResponse yearsResponse = response.body();

                if (response.isSuccessful()) {
                    if (yearsResponse != null) {
                        if (yearsResponse.getData() != null) {
                            if (yearsResponse.getData().size() > 0) {
                                yearsResponse.setStatus(1);
                                allProductsListMutableLiveData.setValue(yearsResponse);
                            } else {
                                yearsResponse.setStatus(0);
                                yearsResponse.setMessage("No data found");
                                allProductsListMutableLiveData.setValue(yearsResponse);
                            }
                        } else {
                            yearsResponse.setStatus(0);
                            yearsResponse.setMessage("No data found");
                            allProductsListMutableLiveData.setValue(yearsResponse);
                        }
                    } else {
                        yearsResponse = new TopSellingResponse();
                        yearsResponse.setStatus(0);
                        yearsResponse.setMessage("Something went wrong. Pease Try again");
                        allProductsListMutableLiveData.setValue(yearsResponse);
                    }
                } else {
                    yearsResponse = new TopSellingResponse();
                    yearsResponse.setStatus(0);
                    yearsResponse.setMessage("Something went wrong. Pease Try again");
                    allProductsListMutableLiveData.setValue(yearsResponse);
                }
            }

            @Override
            public void onFailure(Call<TopSellingResponse> call, Throwable t) {
                TopSellingResponse yearsResponse = new TopSellingResponse();
                yearsResponse.setStatus(0);
                yearsResponse.setMessage("Server not responding");
                allProductsListMutableLiveData.setValue(yearsResponse);
            }
        });

        return allProductsListMutableLiveData;
    }
    public SingleLiveEvent<SignUpResponse> signupUser(String name, String email, String password, String mobile, String address) {


        Call<SignUpResponse> signupCall = apiInterface.signupUser(name, email, password, mobile, address);

        signupCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {

                SignUpResponse authResponse = response.body();

                if (response.code() == 404) {

                    authResponse.setStatus("0");
                    authResponse.setMessage("The email has already been taken");
                    loginEvent.setValue(authResponse);
                } else {
                    if (response.code() == 401) {

                        authResponse.setStatus("0");
                        authResponse.setMessage("Invalid details");
                        loginEvent.setValue(authResponse);
                    } else {
                        if (authResponse != null) {
                            if (response.isSuccessful()) {
                                if (authResponse.getStatus().equalsIgnoreCase("1")) {

                                    editor.putBoolean(Constants.USER_IS_LOGIN, true);
                                    editor.putInt(Constants.USER_ID, authResponse.getData().getUserId());
                                    editor.putString(Constants.USER_NAME, authResponse.getData().getUserName());
                                    editor.putString(Constants.USER_EMAIL, authResponse.getData().getUserEmail());
                                    editor.putString(Constants.USER_MOBILE_NUMBER, authResponse.getData().getMobileNo());
                                    editor.putString(Constants.USER_ADDRESS, authResponse.getData().getAddress());
                                    editor.commit();
                                    editor.apply();

                                    authResponse.setStatus("1");
                                    authResponse.setMessage("Registered successfully");
                                    loginEvent.setValue(authResponse);
                                } else {
                                    authResponse.setStatus("0");
                                    loginEvent.setValue(authResponse);
                                }
                            } else {
                                authResponse.setStatus("0");
                                authResponse.setMessage("Something went wrong. Pease Try again2");
                                loginEvent.setValue(authResponse);
                            }
                        } else {
                            authResponse.setStatus("0");
                            authResponse.setMessage("Something went wrong. Pease Try again3");
                            loginEvent.setValue(authResponse);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
              /*  authResponse.setStatus("0");
                authResponse.setMessage("Server not responding");
                loginEvent.setValue(authResponse);*/
            }
        });

        return loginEvent;
    }

    public SingleLiveEvent<SignUpResponse> loginUser(String email, String password) {

        final SignUpResponse apiAuthResponse = new SignUpResponse();

        Call<SignUpResponse> loginCall = apiInterface.login(email, password);

        loginCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {

                SignUpResponse authResponse = response.body();

                if (response.code() == 401) {

                    apiAuthResponse.setStatus("0");
                    apiAuthResponse.setMessage("Invalid email or password");
                    loginEvent.setValue(apiAuthResponse);
                } else {
                    if (authResponse != null) {
                        if (response.isSuccessful()) {
                            if (authResponse.getStatus().equals("1")) {

                                editor.putBoolean(Constants.USER_IS_LOGIN, true);
                                editor.putInt(Constants.USER_ID, authResponse.getData().getUserId());
                                editor.putString(Constants.USER_NAME, authResponse.getData().getUserName());
                                editor.putString(Constants.USER_EMAIL, authResponse.getData().getUserEmail());
                                editor.putString(Constants.USER_MOBILE_NUMBER, authResponse.getData().getMobileNo());
                                editor.putString(Constants.USER_ADDRESS, authResponse.getData().getAddress());
                                editor.commit();
                                editor.apply();

                                apiAuthResponse.setStatus("1");
                                apiAuthResponse.setMessage("Registered successfully");
                                loginEvent.setValue(apiAuthResponse);
                            } else {
                                apiAuthResponse.setStatus("0");
                                loginEvent.setValue(apiAuthResponse);
                            }
                        } else {
                            apiAuthResponse.setStatus("0");
                            apiAuthResponse.setMessage("Something went wrong. Pease Try again");
                            loginEvent.setValue(apiAuthResponse);
                        }
                    } else {
                        apiAuthResponse.setStatus("0");
                        apiAuthResponse.setMessage("Something went wrong. Pease Try again");
                        loginEvent.setValue(apiAuthResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                apiAuthResponse.setStatus("0");
                apiAuthResponse.setMessage("Server not responding");
                loginEvent.setValue(apiAuthResponse);
            }
        });

        return loginEvent;
    }

    public SingleLiveEvent<TopSellingResponse> getFavouriteListByUserId(int userId){

        Call<TopSellingResponse> call=apiInterface.getFavouriteList(userId);

        call.enqueue(new Callback<TopSellingResponse>() {
            @Override
            public void onResponse(Call<TopSellingResponse> call, Response<TopSellingResponse> response) {

                TopSellingResponse topSellingResponse=response.body();

                if(response.isSuccessful()){
                    if(topSellingResponse!=null){
                        if(topSellingResponse.getData()!=null){
                            if(topSellingResponse.getData().size()>0){
                                topSellingResponse.setStatus(1);
                                favouritesSingleLiveEvent.setValue(topSellingResponse);

                            }else {
                                topSellingResponse.setStatus(0);
                                topSellingResponse.setMessage("No Data Found");
                                favouritesSingleLiveEvent.setValue(topSellingResponse);
                            }
                        }else {
                            topSellingResponse.setStatus(0);
                            topSellingResponse.setMessage("No Data Found");
                            favouritesSingleLiveEvent.setValue(topSellingResponse);
                        }
                    }else {
                        topSellingResponse = new TopSellingResponse();
                        topSellingResponse.setStatus(0);
                        topSellingResponse.setMessage("Something went wrong. Pease Try again");
                        favouritesSingleLiveEvent.setValue(topSellingResponse);
                    }
                }else {
                    topSellingResponse = new TopSellingResponse();
                    topSellingResponse.setStatus(0);
                    topSellingResponse.setMessage("Something went wrong. Pease Try again");
                    favouritesSingleLiveEvent.setValue(topSellingResponse);
                }

            }

            @Override
            public void onFailure(Call<TopSellingResponse> call, Throwable t) {
                TopSellingResponse topSellingResponse = new TopSellingResponse();
                topSellingResponse.setStatus(0);
                topSellingResponse.setMessage("Server not responding");
                favouritesSingleLiveEvent.setValue(topSellingResponse);
            }
        });


        return favouritesSingleLiveEvent;
    }

    public SingleLiveEvent<AddFavResponse> addToFavourite(String productId,int userId){

        Call<AddFavResponse> addFavResponseCall=apiInterface.addToFavourite(productId,userId);

        addFavResponseCall.enqueue(new Callback<AddFavResponse>() {
            @Override
            public void onResponse(Call<AddFavResponse> call, Response<AddFavResponse> response) {
                AddFavResponse addFavResponse=response.body();

                if(response.isSuccessful()){
                    if(addFavResponse!=null){
                        addFavResponseSingleLiveEvent.setValue(addFavResponse);
                    }else {
                        addFavResponse=new AddFavResponse();
                        addFavResponse.setMessage("Something went wrong");
                        addFavResponseSingleLiveEvent.setValue(addFavResponse);
                    }
                }else {
                    addFavResponse=new AddFavResponse();
                    addFavResponse.setMessage("Something went wrong");
                    addFavResponseSingleLiveEvent.setValue(addFavResponse);
                }
            }

            @Override
            public void onFailure(Call<AddFavResponse> call, Throwable t) {
                AddFavResponse addFavResponse=new AddFavResponse();
                addFavResponse.setMessage("Server Not Responding");
                addFavResponseSingleLiveEvent.setValue(addFavResponse);
            }
        });

        return addFavResponseSingleLiveEvent;
    }

    public SingleLiveEvent<DeleteFavResponse> deleteFavourite(int productId,int userId){

        Call<DeleteFavResponse> addFavResponseCall=apiInterface.deleteFavourite(productId,userId);

        addFavResponseCall.enqueue(new Callback<DeleteFavResponse>() {
            @Override
            public void onResponse(Call<DeleteFavResponse> call, Response<DeleteFavResponse> response) {
                DeleteFavResponse deleteFavResponse=response.body();

                if(response.isSuccessful()){
                    if(deleteFavResponse!=null){
                        if(deleteFavResponse.getData()!=null){
                            deleteFavResponseSingleLiveEvent.setValue(deleteFavResponse);
                        }else {
                            deleteFavResponse.setData("Something went wrong. please try again");
                            deleteFavResponseSingleLiveEvent.setValue(deleteFavResponse);
                        }
                    }else {
                        deleteFavResponse=new DeleteFavResponse();
                        deleteFavResponse.setData("Something went wrong. please try again");
                        deleteFavResponseSingleLiveEvent.setValue(deleteFavResponse);
                    }
                }else {
                    deleteFavResponse=new DeleteFavResponse();
                    deleteFavResponse.setData("Something went wrong. please try again");
                    deleteFavResponseSingleLiveEvent.setValue(deleteFavResponse);
                }
            }

            @Override
            public void onFailure(Call<DeleteFavResponse> call, Throwable t) {
                DeleteFavResponse deleteFavResponse=new DeleteFavResponse();
                deleteFavResponse.setData("Server Not Responding");
                deleteFavResponseSingleLiveEvent.setValue(deleteFavResponse);
            }
        });

        return deleteFavResponseSingleLiveEvent;
    }

    public Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(application.getBaseContext());
        View view = inflater.inflate(R.layout.menu_cartcount, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = (TextView) view.findViewById(R.id.count);
            textView.setText("" + count);
        }

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(application.getBaseContext().getResources(), bitmap);
    }

    public SingleLiveEvent<UpdateCartResponse> updateCart(int productID,int quantity,int userId){

        Call<UpdateCartResponse> updateCartResponseCall=apiInterface.updateCart(String.valueOf(productID),String.valueOf(quantity),userId);

        updateCartResponseCall.enqueue(new Callback<UpdateCartResponse>() {
            @Override
            public void onResponse(Call<UpdateCartResponse> call, Response<UpdateCartResponse> response) {
                UpdateCartResponse updateCartResponse=response.body();

                if(response.isSuccessful()){
                    if(updateCartResponse!=null){
                        if(updateCartResponse.getData()!=null){
                            updateCartResponseSingleLiveEvent.setValue(updateCartResponse);
                        }else {
                            updateCartResponseSingleLiveEvent.setValue(updateCartResponse);
                        }
                    }else {
                        updateCartResponse=new UpdateCartResponse();
                        updateCartResponse.setMessage("Something went wrong. Please try again");
                        updateCartResponseSingleLiveEvent.setValue(updateCartResponse);
                    }
                }else {
                    updateCartResponse=new UpdateCartResponse();
                    updateCartResponse.setMessage("Something went wrong. Please try again");
                    updateCartResponseSingleLiveEvent.setValue(updateCartResponse);
                }
            }

            @Override
            public void onFailure(Call<UpdateCartResponse> call, Throwable t) {
                UpdateCartResponse updateCartResponse=new UpdateCartResponse();
                updateCartResponse.setMessage("Server not responding");
                updateCartResponseSingleLiveEvent.setValue(updateCartResponse);
            }
        });

        return  updateCartResponseSingleLiveEvent;
    }

    public SingleLiveEvent<PlaceOrderResponse> placeOrder(String firstName,String emailAddress,String phone,
                                                          String address1,String address2,String city,int userId){
        Call<PlaceOrderResponse> placeOrderResponseCall=apiInterface.placeOrder(firstName, emailAddress, phone, address1, address2, city, userId);

        placeOrderResponseCall.enqueue(new Callback<PlaceOrderResponse>() {
            @Override
            public void onResponse(Call<PlaceOrderResponse> call, Response<PlaceOrderResponse> response) {
                PlaceOrderResponse placeOrderResponse=response.body();

                if(response.isSuccessful()){
                    if(placeOrderResponse!=null){
                        placeOrderResponseSingleLiveEvent.setValue(placeOrderResponse);
                    }else {
                        placeOrderResponse=new PlaceOrderResponse();
                        placeOrderResponse.setData("Something went wrong. please try again");
                        placeOrderResponseSingleLiveEvent.setValue(placeOrderResponse);
                    }
                }else {
                    placeOrderResponse=new PlaceOrderResponse();
                    placeOrderResponse.setData("Something went wrong. please try again");
                    placeOrderResponseSingleLiveEvent.setValue(placeOrderResponse);
                }
            }

            @Override
            public void onFailure(Call<PlaceOrderResponse> call, Throwable t) {
                PlaceOrderResponse placeOrderResponse=new PlaceOrderResponse();
                placeOrderResponse.setData("Server not responding");
                placeOrderResponseSingleLiveEvent.setValue(placeOrderResponse);
            }
        });

        return placeOrderResponseSingleLiveEvent;
    }

    public CartDataTable getCart2(int prodId,int userId){
        return cartDao.getCart2(prodId,userId);
    }

    public FavDataTable getFavourite(int prodId,int userId){
        return favDao.getFavourite(prodId,userId);
    }

    public LiveData<CartDataTable> getCart(int productId,int userId){
        return cartDao.getCart(productId,userId);
    }

    public LiveData<List<CartDataTable>> getCartLis(int userId){
        return cartDao.getCartsList(userId);
    }

    public LiveData<List<Integer>> getGetProductIds(int userId){
        return cartDao.getProductIds(userId);
    }

    public LiveData<Integer> getCartCount(int userId){
        return cartDao.getCartCount(userId);
    }

    public void insertCart(CartDataTable cartDataTable){

        new insertCartAsyncTask(cartDao).execute(cartDataTable);

    }


    private static class insertCartAsyncTask extends AsyncTask<CartDataTable,Void,Void>{

        private CartDao cartDao;

        public insertCartAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(CartDataTable... cartDataTables) {
            cartDao.insert(cartDataTables[0]);
            return null;
        }
    }

    public void updateCart(CartDataTable cartDataTable){

        new updateCartAsyncTask(cartDao).execute(cartDataTable);

    }

    private static class updateCartAsyncTask extends AsyncTask<CartDataTable,Void,Void>{

        private CartDao cartDao;

        public updateCartAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(CartDataTable... cartDataTables) {
            cartDao.update(cartDataTables[0]);
            return null;
        }
    }

    public void deleteCart(CartDataTable cartDataTable){

        new deleteCartAsyncTask(cartDao).execute(cartDataTable);

    }

    private static class deleteCartAsyncTask extends AsyncTask<CartDataTable,Void,Void>{

        private CartDao cartDao;

        public deleteCartAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(CartDataTable... cartDataTables) {
            if (cartDataTables != null) {
                cartDao.delete(cartDataTables[0]);
            }
            return null;
        }
    }

    public void deleteCartTable(){

        new deleteCartTableAsyncTask(cartDao).execute();

    }

    private static class deleteCartTableAsyncTask extends AsyncTask<Void,Void,Void>{

        private CartDao cartDao;

        public deleteCartTableAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cartDao.deleteAllCart();
            return null;
        }
    }

    public LiveData<List<FavDataTable>> getAllFavourites(int userId){
        return favDao.getAllFavourites(userId);
    }

    public void insertFav(FavDataTable favDataTable){

        new insertFavAsyncTask(favDao).execute(favDataTable);

    }


    private static class insertFavAsyncTask extends AsyncTask<FavDataTable,Void,Void>{

        private FavDao favDao;

        public insertFavAsyncTask(FavDao favDao) {
            this.favDao = favDao;
        }

        @Override
        protected Void doInBackground(FavDataTable... favDataTables) {
            favDao.insert(favDataTables[0]);
            return null;
        }
    }

    public void updateFav(FavDataTable favDataTable){

        new updateFavAsyncTask(favDao).execute(favDataTable);

    }

    private static class updateFavAsyncTask extends AsyncTask<FavDataTable,Void,Void>{

        private FavDao favDao;

        public updateFavAsyncTask(FavDao favDao) {
            this.favDao = favDao;
        }

        @Override
        protected Void doInBackground(FavDataTable... favDataTables) {
            favDao.update(favDataTables[0]);
            return null;
        }
    }

    public void deleteFavTable(FavDataTable favDataTable){

        new deleteFavTableAsyncTask(favDao).execute(favDataTable);

    }

    private static class deleteFavTableAsyncTask extends AsyncTask<FavDataTable,Void,Void>{

        private FavDao favDao;

        public deleteFavTableAsyncTask(FavDao favDao) {
            this.favDao = favDao;
        }

        @Override
        protected Void doInBackground(FavDataTable... favDataTables) {
            favDao.delete(favDataTables[0]);
            return null;
        }
    }

    public void CartforUpdate(int productQuantity, int userId, int productId) {
        cartDao.cartforUpdate(productQuantity, userId, productId);
    }

}