package com.semicolons.masco.pk.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import com.semicolons.masco.pk.dataModels.AddFavResponse;
import com.semicolons.masco.pk.dataModels.FavDataTable;
import com.semicolons.masco.pk.dataModels.TopSellingResponse;
import com.semicolons.masco.pk.repository.DealMallRepository;


public class ProductListFragmentViewModel extends AndroidViewModel {
    DealMallRepository dealMallRepositoryItem;


    public ProductListFragmentViewModel(@NonNull Application application) {
        super(application);
        dealMallRepositoryItem = new DealMallRepository(application);

    }


    public LiveData<TopSellingResponse> getProductsList(int cat_id,int pageNo) {

        return dealMallRepositoryItem.getProductsList(cat_id,pageNo);
    }

    public LiveData<AddFavResponse> addToFavourite(String productId,int userId){
        return dealMallRepositoryItem.addToFavourite(productId,userId);
    }

    public void insertFavToDB(FavDataTable favDataTable){
        dealMallRepositoryItem.insertFav(favDataTable);
    }

    public LiveData<List<FavDataTable>> getAllFavourites(int userId){
        return dealMallRepositoryItem.getAllFavourites(userId);
    }
}
