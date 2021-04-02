package com.semicolons.masco.pk.retrofit;

import com.semicolons.masco.pk.dataModels.AddFavResponse;
import com.semicolons.masco.pk.dataModels.CartProductResponse;
import com.semicolons.masco.pk.dataModels.CartResponse;
import com.semicolons.masco.pk.dataModels.CategoryDM;
import com.semicolons.masco.pk.dataModels.DeleteFavResponse;
import com.semicolons.masco.pk.dataModels.OrderDetailResponse;
import com.semicolons.masco.pk.dataModels.OrdersResponse;
import com.semicolons.masco.pk.dataModels.PlaceOrderResponse;
import com.semicolons.masco.pk.dataModels.Points;
import com.semicolons.masco.pk.dataModels.ProfileUpdateResponse;
import com.semicolons.masco.pk.dataModels.SignUpResponse;
import com.semicolons.masco.pk.dataModels.SliderImagesResponse;
import com.semicolons.masco.pk.dataModels.TopSellingResponse;
import com.semicolons.masco.pk.dataModels.UpdateCartResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("api-top-selling-products")
    Call<TopSellingResponse> getTopSellingProducts(
            @Query("page") int page
    );

    @GET("api-latest-products")
    Call<TopSellingResponse> getLatestProducts(
            @Query("page") int page
    );

    @GET("api-all-products")
    Call<TopSellingResponse> getAllProducts(
            @Query("page") int page
    );

    @GET("api-slider")
    Call<SliderImagesResponse> getSliderImages();

//    @GET("api-all-categories")
//    Call<CategoryDM> getAllCategories();

    @GET("api-main-categories")
    Call<CategoryDM> getAllCategories();

//    @FormUrlEncoded
//    @POST("get-categories-by-id")
//    Call<CategoryDM> getAllSubCategories(
//            @Field("cat_id") int cat_id
//    );

    @FormUrlEncoded
    @POST("api-get-sub-categories")
    Call<CategoryDM> getAllSubCategories(
            @Field("cat_id") int cat_id
    );

    @FormUrlEncoded
    @POST("api-products-by-cat-id")
    Call<TopSellingResponse> getProductsList(
            @Query("paged") int pageNo,
            @Field("cat_id") int cat_id
    );

    @FormUrlEncoded
    @POST("search-by-keyword")
    Call<TopSellingResponse> getSearchList(
            @Field("keyword") String keyword
    );


    @FormUrlEncoded
    @POST("api-signup")
    Call<SignUpResponse> signupUser(
            @Field("txtUserName") String name,
            @Field("txtEmail") String email,
            @Field("txtPassword") String password,
            @Field("txtMobNo") String mobile_number,
            @Field("txtAddress") String txtAddress
    );

    @FormUrlEncoded
    @POST("api-login")
    Call<SignUpResponse> login(
            @Field("txtEmail") String email,
            @Field("txtPassword") String password
    );

    @FormUrlEncoded
    @POST("order-detail")
    Call<OrderDetailResponse> getOrderDetail(
            @Field("txtorderID") int orderId
    );

    @FormUrlEncoded
    @POST("api-user-profile")
    Call<OrdersResponse> getAllOrders(
            @Field("txtuserID") int txtuserID
    );

    @FormUrlEncoded
    @POST("api-userpoints")
    Call<Points> getUserPointsById(
            @Field("txtuserID") int txtuserID
    );

    @FormUrlEncoded
    @POST("api-update-profile")
    Call<ProfileUpdateResponse> updateUserProfile(
            @Field("txtuserId") int txtuserID,
            @Field("txtfirstName") String name,
            @Field("txtAddress") String address,
            @Field("txtMobNo") String mob_no,
            @Field("txtpassword") String pswd
    );


    @FormUrlEncoded
    @POST("add-to-cart")
    Call<CartResponse> addProductToCart(
            @Field("txtuserId") int userId,
            @Field("txtProductId") int productId,
            @Field("txtquantity") int quantity
    );

    @FormUrlEncoded
    @POST("api-update-cart")
    Call<UpdateCartResponse> updateCart(
            @Field("txtProductId") String productId,
            @Field("txtquantity") String quantity,
            @Field("txtuserId") int userId
    );

    @FormUrlEncoded
    @POST("cart-list")
    Call<CartProductResponse> getCartList(
            @Field("txtuserId") int userId
    );

    @FormUrlEncoded
    @POST("api-place-order")
    Call<PlaceOrderResponse> placeOrder(
            @Field("txtbilling_firstName") String firstName,
            @Field("txtbilling_emailAddress") String emailAddress,
            @Field("txtbilling_phone") String phone,
            @Field("txtbilling_address1") String address1,
            @Field("txtbilling_address2") String address2,
            @Field("txtbilling_city") String city,
            @Field("txtuserID") int userId
    );

    @FormUrlEncoded
    @POST("add-to-favorite")
    Call<AddFavResponse> addToFavourite(
            @Field("txtProductId") String productId,
            @Field("txtUserId") int userId
    );

    @FormUrlEncoded
    @POST("favorite-list")
    Call<TopSellingResponse> getFavouriteList(
            @Field("txtUserId") int userId
    );

    @FormUrlEncoded
    @POST("delete-favorite")
    Call<DeleteFavResponse> deleteFavourite(
            @Field("product_id") int productId,
            @Field("user_id") int userId
    );
}

