package com.semicolons.masco.pk.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import com.semicolons.masco.pk.dataModels.CartDataTable;
import com.semicolons.masco.pk.dataModels.CartProductResponse;
import com.semicolons.masco.pk.dataModels.CartResponse;
import com.semicolons.masco.pk.dataModels.UpdateCartResponse;
import com.semicolons.masco.pk.repository.DealMallRepository;

public class CartViewModel extends AndroidViewModel {
    DealMallRepository dealMallRepository;
    private LiveData<List<Integer>> productIds;
    private LiveData<Integer> cartCount;
    private LiveData<List<CartDataTable>> cartDataTableLiveData;
    private LiveData<CartDataTable> cartTable;
    private CartDataTable cartDataTable;

    public CartViewModel(@NonNull Application application) {
        super(application);
        dealMallRepository=new DealMallRepository(application);
    }

    public LiveData<CartResponse> addProductToCart(int userId,int productId,int productQuantity){
        return dealMallRepository.addProductToCart(userId,productId,productQuantity);
    }

    public LiveData<CartProductResponse> getCartList(int userId){
        return dealMallRepository.getCartList(userId);
    }

    public LiveData<UpdateCartResponse> updateCart(int prodId,int quantity,int userId){
        return dealMallRepository.updateCart(prodId, quantity,userId);
    }

    public LiveData<List<CartDataTable>> getCartDataTableLiveData(int userId) {
        cartDataTableLiveData=dealMallRepository.getCartLis(userId);
        return cartDataTableLiveData;
    }

    public LiveData<List<Integer>> getProductIDs(int userId) {
        productIds=dealMallRepository.getGetProductIds(userId);
        return productIds;
    }

    public LiveData<Integer> getCartCount(int userId) {
        cartCount=dealMallRepository.getCartCount(userId);
        return cartCount;
    }

    public LiveData<CartDataTable> getCartTable(int prodId,int userId) {
        cartTable=dealMallRepository.getCart(prodId,userId);
        return cartTable;
    }

    public void insertCart(CartDataTable cartDataTable){
        dealMallRepository.insertCart(cartDataTable);
    }

    public void updateCart(CartDataTable cartDataTable){
        dealMallRepository.updateCart(cartDataTable);
    }

    public void deleteCart(CartDataTable cartDataTable){
        dealMallRepository.deleteCart(cartDataTable);
    }

    public void deleteCartTable() {
        dealMallRepository.deleteCartTable();
    }

    public CartDataTable getCart2(int prodId, int userId) {
        cartDataTable = dealMallRepository.getCart2(prodId, userId);
        return cartDataTable;
    }

    public void CartforUpdate(int productQuantity, int userId, int productId) {
        dealMallRepository.CartforUpdate(productQuantity, userId, productId);
    }

}
