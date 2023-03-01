package com.example.demoroom.Database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;


import com.example.demoroom.User;

@Database(entities = User.class, version = 1, exportSchema = false)
public abstract class UserDatabase extends androidx.room.RoomDatabase{

    private static final String DATABASE_NAME = "user.database";
    private static UserDatabase instance;

    public static synchronized UserDatabase getInstance(Context context){
       if(instance == null){
           instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, DATABASE_NAME)
                   .allowMainThreadQueries()
                   .build();
       }
         return instance;
    }

    public abstract UserDAO userDAO();
}
