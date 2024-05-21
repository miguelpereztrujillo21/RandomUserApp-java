package com.mpt.randomuserapp_java.modules.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MutableLiveData<String> _filterEmail = new MutableLiveData<>();
    public LiveData<String> getFilterEmail() {
        return _filterEmail;
    }
}
