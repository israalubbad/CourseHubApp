package com.example.coursehubapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void userInsert(User user);

    @Update
    void userUpdate(User user);

    @Delete
    void userDelete(User user);

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUser();

    @Query("select * from user WHERE userEmail = :email ")
    LiveData<User>getUserByEmail(String email );

    @Query("select * from user WHERE userEmail = :email AND userPassword = :password ")
    LiveData<User>getUserByEmailAndPassword(String email , String password);

    @Query("select * from user WHERE userPassword = :password ")
    LiveData<User>getUserByPassword(String password );

    @Query("SELECT * FROM USER WHERE userId = :userId ")
    LiveData<User>getUserId(int userId);


}
