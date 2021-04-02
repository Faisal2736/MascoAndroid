package com.semicolons.masco.pk.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.semicolons.masco.pk.dataModels.Points;
import com.semicolons.masco.pk.repository.DealMallRepository;

public class PointsViewModel extends AndroidViewModel {
    DealMallRepository dealMallRepository;

    public PointsViewModel(@NonNull Application application) {
        super(application);
        dealMallRepository=new DealMallRepository(application);
    }

    public MutableLiveData<Points> getUserPointsById(int user_id){
        return dealMallRepository.getUserPointsById(user_id);
    }
}
