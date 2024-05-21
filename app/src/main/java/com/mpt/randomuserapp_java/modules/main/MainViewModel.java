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
    private final int itemsPerPage = 10;
    private boolean databaseIsEmpty = true;

    private final MutableLiveData<List<User>> _users = new MutableLiveData<>();
    public LiveData<List<User>> getUsers() {
        return _users;
    }

    private final MutableLiveData<String> _filterEmail = new MutableLiveData<>();
    public LiveData<String> getFilterEmail() {
        return _filterEmail;
    }

    public void setFilterEmail(String filterEmail) {
        _filterEmail.postValue(filterEmail);
    }

    @Inject
    public MainViewModel(ApiRepository apiRepository, UserDao userDao) {
        this.apiRepository = apiRepository;
        this.userDao = userDao;
        apiRepository.syncDataWithBackend();
    }

    public void syncData(){
        // Después de sincronizar los datos, obtén los usuarios de la base de datos
        compositeDisposable.add(
                apiRepository.syncDataWithBackend()
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                this::getUsersFromDatabase,
                                throwable -> {
                                    // Aquí puedes manejar el error
                                }
                        )
        );
    }
    public void getUsersFromRepository(){
        compositeDisposable.add(
                apiRepository.getUsers(currentPage, itemsPerPage, "")
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
    }
    public void getUsersFromDatabase() {
        compositeDisposable.add(
                userDao.getUsersPagin(itemsPerPage, currentPage * itemsPerPage)
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                users -> {
                                    if (users.isEmpty()) {
                                        getUsersFromRepository();
                                        databaseIsEmpty = true;
                                    } else {
                                        List<User> currentUsers = _users.getValue();
                                        if (currentUsers == null) {
                                            currentUsers = new ArrayList<>();
                                        }
                                        databaseIsEmpty = false;
                                        currentUsers.addAll(users);

                                        _users.postValue(currentUsers);
                                    }

                                },
                                throwable -> {
                                    // Aquí puedes manejar el error
                                }
                        )
        );
    }

    public void getUsersStartingWith(String prefix) {
        currentPage = 1;
        compositeDisposable.add(
                userDao.getUsersStartingWith(prefix)
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                _users::postValue,
                                throwable -> {
                                    // Aquí puedes manejar el error
                                }
                        )
        );
    }

    public void loadNextPage() {
        int maxPages = 10;
        if (currentPage <= maxPages) {
            currentPage++;
            if (databaseIsEmpty) {
                getUsersFromRepository();
            } else {
                getUsersFromDatabase();
            }
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
        apiRepository.dispose();
    }

}
