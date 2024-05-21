package com.mpt.randomuserapp_java.models;

public class Street {
    private int number;
    private String name;

    public Street(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }
}
