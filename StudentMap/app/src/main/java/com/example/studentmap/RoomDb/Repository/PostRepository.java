package com.example.studentmap.RoomDb.Repository;

import android.content.Context;


import androidx.lifecycle.LiveData;

import com.example.studentmap.RoomDb.DAO.PostDao;
import com.example.studentmap.RoomDb.DAO.UserDao;

import com.example.studentmap.RoomDb.Entity.Postdb;
import com.example.studentmap.RoomDb.Entity.User;
import com.example.studentmap.RoomDb.MyRoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostRepository {

    private final PostDao postDao;
    private ExecutorService service = Executors.newFixedThreadPool(3);

    public PostRepository(Context context){
        postDao = MyRoomDatabase.getDatabase(context).postDao();
    }

    public void addPOst(final Postdb post){
        service.submit(new Runnable() {
            @Override
            public void run() {
                postDao.insert(post);
            }
        });
    }



    public LiveData<List<Postdb>> getAllPosts(){return postDao.getAllPosts();}
}
