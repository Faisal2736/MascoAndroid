package com.semicolons.masco.pk.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.semicolons.masco.pk.dataModels.OrderDetailResponse;
import com.semicolons.masco.pk.dataModels.OrdersResponse;
import com.semicolons.masco.pk.dataModels.PlaceOrderResponse;
import com.semicolons.masco.pk.repository.DealMallRepository;

public class OrderViewModel extends AndroidViewModel {
    DealMallRepository dealMallRepositoryItem;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        dealMallRepositoryItem = new DealMallRepository(application);
    }

    public LiveData<OrdersResponse> getAllOrders(int user_id){
        return dealMallRepositoryItem.getAllOrders(user_id);
    }

    public LiveData<PlaceOrderResponse> placeOrder(String firstName, String emailAddress, String phone,
                                                   String address1, String address2, String city, int userId){
        return dealMallRepositoryItem.placeOrder(firstName, emailAddress, phone, address1, address2, city, userId);
    }

    public LiveData<OrderDetailResponse> getOrderDetail(int userId) {
        return dealMallRepositoryItem.getOrderDetail(userId);
    }

}
