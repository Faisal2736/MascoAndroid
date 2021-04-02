package com.semicolons.masco.pk.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.semicolons.masco.pk.dataModels.ProfileUpdateResponse;
import com.semicolons.masco.pk.repository.DealMallRepository;

public class ProfileViewModel extends AndroidViewModel {
    DealMallRepository dealMallRepository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        dealMallRepository=new DealMallRepository(application);
    }

    public LiveData<ProfileUpdateResponse> getUserUpdateProfile(int user_id,String name,String address,String mob_no,String pswd){
        return dealMallRepository.updateUserProfile(user_id, name, address, mob_no, pswd);
    }
}
