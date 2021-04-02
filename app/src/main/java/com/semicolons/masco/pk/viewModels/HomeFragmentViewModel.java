package com.semicolons.masco.pk.viewModels;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.semicolons.masco.pk.dataModels.CategoryDM;
import com.semicolons.masco.pk.dataModels.SliderImagesResponse;
import com.semicolons.masco.pk.dataModels.TopSellingResponse;
import com.semicolons.masco.pk.repository.DealMallRepository;

public class HomeFragmentViewModel extends AndroidViewModel {

    DealMallRepository dealMallRepository;

    public HomeFragmentViewModel(@NonNull Application application) {
        super(application);
        dealMallRepository = new DealMallRepository(application);
    }

    public LiveData<TopSellingResponse> getTopSellingProducts (int page) {

        return dealMallRepository.getTopSellingProducts(page);
    }

    public LiveData<TopSellingResponse> getLatestProducts (int page) {

        return dealMallRepository.getLatestProducts(page);
    }

    public LiveData<TopSellingResponse> getAllProducts(int page) {

        return dealMallRepository.getAllProducts(page);
    }

    public LiveData<SliderImagesResponse> getSliderImages() {

        return dealMallRepository.getSliderImages();
    }

    public LiveData<CategoryDM> getAllCategories() {

        return dealMallRepository.getAllCategories();
    }


    public Drawable getDrawableForCounter(int count, int backgroundImageId) {
        return dealMallRepository.buildCounterDrawable(count, backgroundImageId);
    }

}
