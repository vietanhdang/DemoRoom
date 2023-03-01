package com.example.demoroom.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.demoroom.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    public void insertUser(User user);

    @Query("SELECT * FROM user")
    public List<User> getAllUser();

    @Query("SELECT * FROM user WHERE username = :username ")
    List<User> checkUser(String username);

    @Update
    void updateUser(User user);


}
