package com.semicolons.masco.pk.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.semicolons.masco.pk.dataModels.CategoryDM;
import com.semicolons.masco.pk.repository.DealMallRepository;


public class CategoryFragmentViewModel extends AndroidViewModel {
    DealMallRepository dealMallRepositoryItem;


    public CategoryFragmentViewModel(@NonNull Application application) {
        super(application);
        dealMallRepositoryItem = new DealMallRepository(application);

    }


    public LiveData<CategoryDM> getAllCategories() {

        return dealMallRepositoryItem.getAllCategories();
    }


}
