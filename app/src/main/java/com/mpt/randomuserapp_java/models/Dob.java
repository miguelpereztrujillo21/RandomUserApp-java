package com.mpt.randomuserapp_java.models;

public class Dob {
    private String date;
    private int age;

    public Dob(String date, int age) {
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