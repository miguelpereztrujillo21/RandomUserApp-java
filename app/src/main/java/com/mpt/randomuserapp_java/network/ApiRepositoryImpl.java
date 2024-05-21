package com.mpt.randomuserapp_java.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mpt.randomuserapp_java.models.User;
import com.mpt.randomuserapp_java.models.UserResponse;
import com.mpt.randomuserapp_java.room.UserDao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

@Singleton
public class ApiRepositoryImpl implements ApiRepository {
    private final ApiService api;
    private UserDao userDao;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public ApiRepositoryImpl(ApiService api, UserDao userDao) {
        this.api = api;
        this.userDao = userDao;
    }

    @Override
    public Single<UserResponse> getUsers(Integer page, Integer results, String gender) {
        return Single.fromCallable(() -> {
            try {
                Call<UserResponse> call = api.getUsers(page, results, gender, "ac3536fef6ef5ad9");
                Response<UserResponse> response = call.execute();
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if (userResponse == null) {
                        userResponse = new UserResponse();
                    }
                    return userResponse;
                } else {
                    Gson gson = new Gson();
                    assert response.errorBody() != null;
                    JsonObject jsonObject = gson.fromJson(response.errorBody().string(), JsonObject.class);
                    String errorValue = jsonObject.get("error").getAsString();
                    throw new Exception("Error en la solicitud: " + response.code() + " " + errorValue);
                }
            } catch (Exception e) {
                throw new Exception("Error al ejecutar la solicitud: " + e.getMessage());
            }
        });
    }

    @Override
    public @NonNull Completable syncDataWithBackend() {
        return getUsers(1, 100, "")
                .subscribeOn(Schedulers.io())
                .flatMapCompletable(userResponse -> {
                    List<User> usersFromBackend = userResponse.getResults();
                    return userDao.getAll()
                            .subscribeOn(Schedulers.io())
                            .doOnSuccess(usersFromDatabase -> {
                                // Elimina los usuarios que ya no están en el backend
                                for (User user : usersFromDatabase) {
                                    if (!usersFromBackend.contains(user)) {
                                        userDao.delete(user);
                                    }
                                }
                                // Agrega los nuevos usuarios del backend
                                for (User user : usersFromBackend) {
                                    if (!usersFromDatabase.contains(user)) {
                                        user.setCompleteName(user.getName().getFirst() + " " + user.getName().getLast());
                                        userDao.insertUser(user);
                                    }
                                }
                            }).ignoreElement();
                });
    }

    @Override
    public void dispose() {
        compositeDisposable.dispose();
    }
}