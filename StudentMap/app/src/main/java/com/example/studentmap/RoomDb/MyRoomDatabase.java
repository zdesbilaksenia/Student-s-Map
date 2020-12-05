package com.example.studentmap.RoomDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.studentmap.RoomDb.DAO.UserDao;
import com.example.room.Entity.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class MyRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    private static volatile MyRoomDatabase INSTANCE;

    static public MyRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (MyRoomDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MyRoomDatabase.class, "studants_map_bd")
                        .build();
            }
        }
        return INSTANCE;

    }




}
