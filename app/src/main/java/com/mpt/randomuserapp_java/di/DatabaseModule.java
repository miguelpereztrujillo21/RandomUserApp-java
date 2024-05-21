package com.mpt.randomuserapp_java.di;

import android.app.Application;

import androidx.room.Room;

import com.mpt.randomuserapp_java.room.AppDataBase;
import com.mpt.randomuserapp_java.room.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {
    @Provides
    @Singleton
    public AppDataBase provideDatabase(Application app) {
        return Room.databaseBuilder(app, AppDataBase.class, "ramdomuserapp_java_database")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    public UserDao provideUserDao(AppDataBase database) {
        return database.userDao();
    }
}
