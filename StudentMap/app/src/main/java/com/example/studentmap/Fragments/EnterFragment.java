package com.example.studentmap.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.studentmap.Network.Entity.Post;
import com.example.studentmap.Network.Entity.User;
import com.example.studentmap.Network.RequestMakerModel;
import com.example.studentmap.R;
import com.example.studentmap.RoomDb.Entity.Postdb;
import com.example.studentmap.RoomDb.RoomViewModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class EnterFragment extends Fragment {

    Button getData;
    Button getUser;
    private OkHttpClient client;
    RoomViewModel roomViewModel;
    Integer count = 0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_enter, container, false);

        getData = (Button) rootView.findViewById(R.id.button_getData);
        getUser = (Button) rootView.findViewById(R.id.button_getUser);
         roomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);


         // Getting user by login and password
         getUser.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String login = "Andrey122";
                 String password = "1234";
                 if(count <= 2){
                     getUser(login, password);
                 }else{
                     Toast.makeText(getContext(), "Зарегистрируйтесь!!!", Toast.LENGTH_SHORT).show();
                 }

             }
         });

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllPosts();



         }
        });

        // Inflate the layout for this fragment
        return  rootView;
    }

    void getAllPosts(){


        RequestMakerModel model = ViewModelProviders.of(this).get(RequestMakerModel.class);
        LiveData<ArrayList<Post>> data = model.getAllPosts();

        ArrayList<Post> allPosts= new ArrayList<Post>();
        client = new OkHttpClient();
        model.GetAllPosts(client);

        data.observe(this, new Observer<ArrayList<Post>>() {
            @Override
            public void onChanged(ArrayList<Post> posts) {


                allPosts.addAll(posts);

           
                String answer = " ";
                for(int i =0 ; i < posts.size(); i++)
                {
                    answer = answer + posts.get(i).getLocation();
                }
                Log.d("Ready_All_POsts", answer);


                for(int i = 0; i < allPosts.size(); i++){
                    Postdb post = new Postdb();

                    post.setImg(allPosts.get(i).getImg());
                    post.setText(allPosts.get(i).getText());
                    post.setDate(allPosts.get(i).getDate());
                    post.setLocation(allPosts.get(i).getLocation());
                    post.setName(allPosts.get(i).getName());


                    roomViewModel.addPost(post);
                }




            }
        });

    }


    void getUser(String login, String password){
        RequestMakerModel model = ViewModelProviders.of(this).get(RequestMakerModel.class);
        LiveData<User> data = model.getUserData();
        client = new OkHttpClient();

        model.GetUserByLoginAndPassword(client,login,password);

        data.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user.getName() == "NOT_FOUND") {
                    count++;
                    Log.d("Answer", "User not found");
                    Toast.makeText(getContext(), "Учетная запись не найдена!!!", Toast.LENGTH_SHORT).show();

                }else{
                    User userFromServer = new User();
                    String name = userFromServer.getName();
                    String surname = userFromServer.getSurname();
                    Integer age = userFromServer.getAge();
                    String gender = userFromServer.getGender();

//                    SharedPreferences myPreferences
//                            = PreferenceManager.getDefaultSharedPreferences(MyActivity.this);

                    // выводим эти данные куда-нибудь в поле о пользователе

                }

            }
        });

    }

    void registration(User user){
        RequestMakerModel model = ViewModelProviders.of(this).get(RequestMakerModel.class);
        LiveData<Integer> data = model.getDataInt();
        client = new OkHttpClient();

        model.addUser(client,user);

        data.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1){
                    // Переходим на карту.
                }else Toast.makeText(getContext(), "Ошибка создания учетной записи!!!", Toast.LENGTH_SHORT).show();
                Log.d("Answer", Integer.toString(integer));
            }
        });

    }
}