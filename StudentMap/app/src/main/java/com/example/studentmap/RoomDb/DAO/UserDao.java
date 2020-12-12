package com.example.studentmap.RoomDb.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.studentmap.RoomDb.Entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("SELECT * FROM user_table")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user_table WHERE login = :login AND password =:password")
    LiveData<User> getUserByLoginAndPass(String login, String password);
}
