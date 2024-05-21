package com.mpt.randomuserapp_java.room;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.mpt.randomuserapp_java.models.Dob;
import com.mpt.randomuserapp_java.models.Id;
import com.mpt.randomuserapp_java.models.Location;
import com.mpt.randomuserapp_java.models.Login;
import com.mpt.randomuserapp_java.models.Name;
import com.mpt.randomuserapp_java.models.ProfilePicture;
import com.mpt.randomuserapp_java.models.Registered;

public class userConverters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromName(Name name) {
        return gson.toJson(name);
    }

    @TypeConverter
    public static Name toName(String name) {
        return gson.fromJson(name, Name.class);
    }
    @TypeConverter
    public static String fromLocation(Location location) {
        return gson.toJson(location);
    }

    @TypeConverter
    public static Location toLocation(String location) {
        return gson.fromJson(location, Location.class);
    }

    @TypeConverter
    public static String fromLogin(Login login) {
        return gson.toJson(login);
    }

    @TypeConverter
    public static Login toLogin(String login) {
        return gson.fromJson(login, Login.class);
    }

    @TypeConverter
    public static String fromDob(Dob dob) {
        return gson.toJson(dob);
    }

    @TypeConverter
    public static Dob toDob(String dob) {
        return gson.fromJson(dob, Dob.class);
    }

    @TypeConverter
    public static String fromRegistered(Registered registered) {
        return gson.toJson(registered);
    }

    @TypeConverter
    public static Registered toRegistered(String registered) {
        return gson.fromJson(registered, Registered.class);
    }

    @TypeConverter
    public static String fromId(Id id) {
        return gson.toJson(id);
    }

    @TypeConverter
    public static Id toId(String id) {
        return gson.fromJson(id, Id.class);
    }

    @TypeConverter
    public static String fromProfilePicture(ProfilePicture profilePicture) {
        return gson.toJson(profilePicture);
    }

    @TypeConverter
    public static ProfilePicture toProfilePicture(String profilePicture) {
        return gson.fromJson(profilePicture, ProfilePicture.class);
    }
}
