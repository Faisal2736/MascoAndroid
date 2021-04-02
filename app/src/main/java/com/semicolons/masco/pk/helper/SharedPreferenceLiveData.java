package com.semicolons.masco.pk.helper;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class SharedPreferenceLiveData<String> extends MutableLiveData<String> {

    SharedPreferences sharedPrefs;
    String key;
    public int defValue;

    public SharedPreferenceLiveData(String value, SharedPreferences sharedPrefs, String key, int defValue) {
        super(value);
        this.sharedPrefs = sharedPrefs;
        this.key = key;
        this.defValue = defValue;
    }

    public SharedPreferenceLiveData() {
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
        super.observe(owner, observer);
    }

    @Nullable
    @Override
    public String getValue() {
        return super.getValue();
    }
}
