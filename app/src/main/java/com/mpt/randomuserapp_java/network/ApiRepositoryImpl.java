package com.mpt.randomuserapp_java.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mpt.randomuserapp_java.models.UserResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class ApiRepositoryImpl implements ApiRepository {
    private ApiService api;

    @Inject
    public ApiRepositoryImpl(ApiService api) {
        this.api = api;
    }

    @Override
    public UserResponse getUsers(Integer page, Integer results, String gender) {
        Call<UserResponse> call = api.getUsers(page, results, gender);
        UserResponse userResponse = null;
        try {
            Response<UserResponse> response = call.execute();
            if (response.isSuccessful()) {
                userResponse = response.body();
                if (userResponse == null) {
                    userResponse = new UserResponse();
                }
            } else {
                Gson gson = new Gson();
                assert response.errorBody() != null;
                JsonObject jsonObject = gson.fromJson(response.errorBody().string(), JsonObject.class);
                String errorValue = jsonObject.get("error").getAsString();
                throw new Exception("Error en la solicitud: " + response.code() + " " + errorValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userResponse;
    }
}