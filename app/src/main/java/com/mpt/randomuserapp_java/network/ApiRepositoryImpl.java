package com.mpt.randomuserapp_java.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mpt.randomuserapp_java.models.UserResponse;
import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Response;

@Singleton
public class ApiRepositoryImpl implements ApiRepository {
    private final ApiService api;

    @Inject
    public ApiRepositoryImpl(ApiService api) {
        this.api = api;
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
}