package com.mpt.randomuserapp_java.network;

import android.database.Observable;

import com.mpt.randomuserapp_java.models.UserResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/")
    Call<UserResponse> getUsers(
            @Query("page") Integer page,
            @Query("results") Integer results,
            @Query("gender") String gender,
            @Query("seed") String seed
    );
}
