package com.example.studentmap.RoomDb;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.room.Entity.User;
import com.example.studentmap.RoomDb.Repository.UserRepository;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    private LiveData<List<User>> allUsersData;
    private LiveData<User> userData;

    public RoomViewModel(Application application){
        super(application);
        userRepository = new UserRepository(application);
        allUsersData = userRepository.getAllUsers();

    }

    LiveData<List<User>> getAllUsers(){return allUsersData;}

    public void addUser(User user){userRepository.addUser(user);}
    public void updateUser(User user){userRepository.addUser(user);}
    public void deleteUser(User user){userRepository.deleteUser(user);}

    public LiveData<User> findUserByLogAndPass(String login, String password){ return userRepository.findUserByLogAndPass(login, password);}

}
