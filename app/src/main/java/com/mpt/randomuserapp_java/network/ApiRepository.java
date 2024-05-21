package com.mpt.randomuserapp_java.network;

import com.mpt.randomuserapp_java.models.UserResponse;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface ApiRepository {
    Single<UserResponse> getUsers(Integer page, Integer results, String gender);
    @NonNull Completable syncDataWithBackend();

    void dispose();
}
