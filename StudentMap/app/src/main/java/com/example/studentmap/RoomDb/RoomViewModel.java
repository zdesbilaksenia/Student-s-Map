package com.example.studentmap.RoomDb;

import android.app.Application;
import android.graphics.PostProcessor;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.studentmap.RoomDb.Entity.Postdb;
import com.example.studentmap.RoomDb.Entity.User;
import com.example.studentmap.RoomDb.Repository.PostRepository;
import com.example.studentmap.RoomDb.Repository.UserRepository;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private PostRepository postRepository;

    private LiveData<List<User>> allUsersData;
    private LiveData<User> userData;

    private LiveData<List<Postdb>> allPostsData;

    public RoomViewModel(Application application){
        super(application);
        userRepository = new UserRepository(application);
        postRepository = new PostRepository(application);
        allUsersData = userRepository.getAllUsers();
        allPostsData = postRepository.getAllPosts();

    }

    LiveData<List<User>> getAllUsers(){return allUsersData;}
   public LiveData<List<Postdb>> getAllPosts(){return allPostsData;}


    public void addUser(User user){userRepository.addUser(user);}
    public void updateUser(User user){userRepository.addUser(user);}
    public void deleteUser(User user){userRepository.deleteUser(user);}
    public void addPost(Postdb post){postRepository.addPOst(post);}
    public void deleteAllPosts(){postRepository.deleteAll();}

    public LiveData<User> findUserByLogAndPass(String login, String password){ return userRepository.findUserByLogAndPass(login, password);}

}
