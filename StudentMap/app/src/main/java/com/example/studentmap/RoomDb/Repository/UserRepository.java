package com.example.studentmap.RoomDb.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.studentmap.RoomDb.DAO.UserDao;
import com.example.room.Entity.User;
import com.example.studentmap.RoomDb.MyRoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {

    private final UserDao userDao;
    private ExecutorService service = Executors.newFixedThreadPool(3);


    public UserRepository(Context context){
        userDao = MyRoomDatabase.getDatabase(context).userDao();
    }

    public void addUser(final User user){
        service.submit(new Runnable() {
            @Override
            public void run() {
                userDao.insert(user);
            }
        });
    }

    public void deleteUser(final User user){
        service.submit(new Runnable() {
            @Override
            public void run() {
                userDao.delete(user);
            }
        });
    }

    public void updateUser(final User user){
        service.submit(new Runnable() {
            @Override
            public void run() {
                userDao.update(user);
            }
        });
    }

    public LiveData<User> findUserByLogAndPass(final String login, final String password){ return userDao.getUserByLoginAndPass(login, password); }

    public LiveData<List<User>>getAllUsers(){return userDao.getAllUsers();}



    
}
