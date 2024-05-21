package com.mpt.randomuserapp_java.models;

public class ProfilePicture {
    private String large;
    private String medium;
    private String thumbnail;

    public ProfilePicture(String large, String medium, String thumbnail) {
        this.large = large;
        this.medium = medium;
        this.thumbnail = thumbnail;
    }

    public String getLarge() {
        return large;
    }

    public String getMedium() {
        return medium;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}