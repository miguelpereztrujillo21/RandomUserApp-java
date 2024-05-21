package com.mpt.randomuserapp_java.network;

import com.mpt.randomuserapp_java.models.UserResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;

public interface ApiRepository {
    Single<UserResponse> getUsers(Integer page, Integer results, String gender);
    void syncDataWithBackend();

    void dispose();
}
