package com.semicolons.masco.pk.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.semicolons.masco.pk.dataModels.SignUpResponse;
import com.semicolons.masco.pk.repository.DealMallRepository;


public class LoginActivityViewModel extends AndroidViewModel {

    DealMallRepository advocateRepository;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);

        advocateRepository = new DealMallRepository(application);
    }

    public LiveData<SignUpResponse> loginUser(String email, String password) {

        return advocateRepository.loginUser(email, password);
    }


}
