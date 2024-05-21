package com.mpt.randomuserapp_java.network;

import com.mpt.randomuserapp.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/")
    Call<UserResponse> getUsers(
            @Query("page") Integer page,
            @Query("results") Integer results,
            @Query("gender") String gender
    );
}
