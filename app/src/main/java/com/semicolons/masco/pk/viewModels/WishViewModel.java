package com.semicolons.masco.pk.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.semicolons.masco.pk.dataModels.DeleteFavResponse;
import com.semicolons.masco.pk.dataModels.FavDataTable;
import com.semicolons.masco.pk.dataModels.TopSellingResponse;
import com.semicolons.masco.pk.repository.DealMallRepository;

public class WishViewModel extends AndroidViewModel {
    private DealMallRepository dealMallRepository;


    public WishViewModel(@NonNull Application application) {
        super(application);
        dealMallRepository=new DealMallRepository(application);
    }

    public LiveData<TopSellingResponse> getFavouritesProductListByUserId(int userId){
        return dealMallRepository.getFavouriteListByUserId(userId);
    }

    public LiveData<DeleteFavResponse> deleteFavourite(int productId,int userId){
        return dealMallRepository.deleteFavourite(productId,userId);
    }

    public FavDataTable getFavourite(int productId,int userId){
        return dealMallRepository.getFavourite(productId,userId);
    }

    public void deleteFavouriteFromDB(FavDataTable favDataTable){
        dealMallRepository.deleteFavTable(favDataTable);
    }
}
