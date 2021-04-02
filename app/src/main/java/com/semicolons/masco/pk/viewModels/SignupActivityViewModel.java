package com.semicolons.masco.pk.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.semicolons.masco.pk.dataModels.SignUpResponse;
import com.semicolons.masco.pk.repository.DealMallRepository;


public class SignupActivityViewModel extends AndroidViewModel {

    DealMallRepository advocateRepository;

    public SignupActivityViewModel(@NonNull Application application) {
        super(application);

        advocateRepository = new DealMallRepository(application);
    }

    public LiveData<SignUpResponse> signupUser(String name, String email, String password, String mobile, String address) {

        return advocateRepository.signupUser(name, email, password, mobile, address);
    }
}
