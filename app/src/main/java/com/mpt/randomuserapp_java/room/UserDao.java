package com.mpt.randomuserapp_java.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mpt.randomuserapp_java.models.User;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    Single<List<User>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Delete
    void delete(User user);
}
