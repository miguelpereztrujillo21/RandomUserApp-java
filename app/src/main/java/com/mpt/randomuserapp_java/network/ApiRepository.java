package com.mpt.randomuserapp_java.network;

import com.mpt.randomuserapp.models.UserResponse;

public interface ApiRepository {
    UserResponse getUsers(Integer page, Integer results, String gender) throws Exception;
}
