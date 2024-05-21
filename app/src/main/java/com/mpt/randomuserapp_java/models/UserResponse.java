package com.mpt.randomuserapp_java.models;

import java.util.ArrayList;

public class UserResponse {
    private ArrayList<User> results;
    private Info info;

    public UserResponse(ArrayList<User> results, Info info) {
        this.results = results;
        this.info = info;
    }

    public UserResponse() {

    }

    public ArrayList<User> getResults() {
        return results;
    }

    public Info getInfo() {
        return info;
    }
}
