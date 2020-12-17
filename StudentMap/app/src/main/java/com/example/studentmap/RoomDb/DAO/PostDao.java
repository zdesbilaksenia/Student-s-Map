package com.example.studentmap.RoomDb.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.studentmap.RoomDb.Entity.Postdb;
import com.example.studentmap.RoomDb.Entity.User;

import java.lang.invoke.ConstantCallSite;
import java.util.List;

@Dao
public interface PostDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Postdb post);

    @Delete
    void delete(Postdb delete);

    @Update
    void update(Postdb update);

    @Query("DELETE FROM post_table")
    void deleteAll();

    @Query("SELECT * FROM post_table")
    LiveData<List<Postdb>> getAllPosts();


    @Query("SELECT * FROM post_table WHERE location  =:location")
    LiveData<List<Postdb>> getAllPostsByLocation(String location);

}
