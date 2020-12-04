package com.example.studentmap.RoomDb.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.room.Entity.Post;
import com.example.room.Entity.User;

import java.lang.invoke.ConstantCallSite;
import java.util.List;

@Dao
public interface PostDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Post post);

    @Delete
    void delete(Post delete);

    @Update
    void update(Post update);

    @Query("DELETE FROM post_table")
    void deleteAll();

    @Query("SELECT * FROM post_table")
    LiveData<List<Post>> getAllPosts();


    @Query("SELECT * FROM post_table WHERE location  =:location")
    LiveData<List<Post>> getAllPostsByLocation(String location);

}
