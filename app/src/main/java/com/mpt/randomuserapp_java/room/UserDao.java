package com.mpt.randomuserapp_java.room;

import androidx.paging.PagingSource;
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

    @Query("SELECT * FROM user LIMIT :limit OFFSET :offset")
    Single<List<User>> getUsersPagin(int limit, int offset);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Delete
    void delete(User user);
}
