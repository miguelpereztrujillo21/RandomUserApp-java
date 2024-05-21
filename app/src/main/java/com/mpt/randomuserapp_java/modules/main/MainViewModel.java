package com.mpt.randomuserapp_java.modules.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mpt.randomuserapp_java.models.User;
import com.mpt.randomuserapp_java.network.ApiRepository;
import com.mpt.randomuserapp_java.room.UserDao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@HiltViewModel
public class MainViewModel extends ViewModel {

    private final ApiRepository apiRepository;
    private final UserDao userDao;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private int currentPage = 1;

    private boolean isLoading = false;

    @Inject
    public MainViewModel(ApiRepository apiRepository, UserDao userDao){
        this.apiRepository = apiRepository;
        this.userDao = userDao;
        apiRepository.syncDataWithBackend();
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
        isLoading = true ;
        compositeDisposable.add(
                apiRepository.getUsers(currentPage, 10, "male")
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                userResponse -> {
                                    List<User> currentUsers = _users.getValue();
                                    if (currentUsers == null) {
                                        currentUsers = new ArrayList<>();
                                    }
                                    currentUsers.addAll(userResponse.getResults());
                                    _users.postValue(currentUsers);
                                },
                                throwable -> {
                                }
                        )
        );
        isLoading = false;
    }
    public void getUsersFromDatabase() {
        compositeDisposable.add(
                userDao.getAll()
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                _users::postValue,
                                throwable -> {
                                    // Handle the error
                                    System.out.println("Error getting users from the database: " + throwable.getMessage());
                                }
                        )
        );
    }

    public void loadNextPage() {
        int maxPages = 10;
        if (currentPage <= maxPages) {
            currentPage++;
            getUsersFromRepository();
        }
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
        apiRepository.dispose();
    }


}
