package com.mpt.randomuserapp_java.modules.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mpt.randomuserapp_java.models.User;
import com.mpt.randomuserapp_java.network.ApiRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@HiltViewModel
public class MainViewModel extends ViewModel {

    private final ApiRepository apiRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public MainViewModel(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    private final MutableLiveData<List<User>> _users = new MutableLiveData<>();
    public LiveData<List<User>> getUsers() {
        return _users;
    }

    private final MutableLiveData<String> _filterEmail = new MutableLiveData<>();
    public LiveData<String> getFilterEmail() {
        return _filterEmail;
    }



    public void getUsersFromRepository(){
        compositeDisposable.add(
                apiRepository.getUsers(1, 10, "male")
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                userResponse -> _users.postValue(userResponse.getResults()),
                                throwable -> {

                                }
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }



}
