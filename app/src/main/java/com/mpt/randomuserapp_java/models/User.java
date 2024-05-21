package com.mpt.randomuserapp_java.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Entity
public class User {
    private String gender;
    private Name name;
    private String completeName;
    private Location location;
    private String email;
    private Login login;
    private Dob dob;
    private Registered registered;
    private String phone;
    private String cell;
    @PrimaryKey
    @NonNull
    private Id id;
    private ProfilePicture picture;
    private String nat;

    public User(String gender, Name name, Location location, String email, Login login, Dob dob, Registered registered, String phone, String cell, Id id, ProfilePicture picture, String nat) {
        this.gender = gender;
        this.name = name;
        this.location = location;
        this.email = email;
        this.login = login;
        this.dob = dob;
        this.registered = registered;
        this.phone = phone;
        this.cell = cell;
        this.id = id;
        this.picture = picture;
        this.nat = nat;
    }

    public String getGender() {
        return gender;
    }

    public Name getName() {
        return name;
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public Location getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public Login getLogin() {
        return login;
    }

    public Dob getDob() {
        return dob;
    }

    public Registered getRegistered() {
        return registered;
    }

    public String getPhone() {
        return phone;
    }

    public String getCell() {
        return cell;
    }

    public Id getId() {
        return id;
    }

    public ProfilePicture getPicture() {
        return picture;
    }

    public String getNat() {
        return nat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(picture, user.picture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, picture);
    }
}
