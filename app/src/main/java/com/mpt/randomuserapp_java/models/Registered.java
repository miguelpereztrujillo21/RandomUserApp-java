package com.mpt.randomuserapp_java.models;

public class Registered {
    private String date;
    private int age;

    public Registered(String date, int age) {
        this.date = date;
        this.age = age;
    }

    public String getDate() {
        return date;
    }

    public int getAge() {
        return age;
    }
}
