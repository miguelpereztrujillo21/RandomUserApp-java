package com.mpt.randomuserapp_java.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mpt.randomuserapp_java.models.User;

@Database(entities = {User.class}, version = 2)
@TypeConverters({userConverters.class})
public abstract class AppDataBase extends RoomDatabase {
    public abstract UserDao userDao();
}
