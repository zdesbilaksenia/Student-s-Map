package com.example.workwithapi;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.workwithapi.Entity.Place;
import com.example.workwithapi.Entity.Post;
import com.example.workwithapi.Entity.User;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    TextView textReq;
    TextView text2;
    Button button_send;
    TextView textInt;
    private OkHttpClient client;
    String response_text;
   // ExecutorService service = Executors.newFixedThreadPool(5);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textReq = (TextView) findViewById(R.id.textForAnswer);
        client = new OkHttpClient();


    }

    //Получить пользователя по логину и паролю
    public void MakeQvery(View view) {

        RequestMakerModel model = ViewModelProviders.of(this).get(RequestMakerModel.class);
        LiveData<User> data = model.getUserData();
        model.GetUserByLogin(client,"Andrey123");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        model.GetUserByLoginAndPassword(client,"Katia22","87545");

        data.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                textReq.setText(user.getName()+ " " + user.getSurname() + " " + user.getLogin() + " " + user.getPassword());
            }
        });

    }
    //Получить список всех пользователей
    public void GetAllUsers(View view ){
        RequestMakerModel model = ViewModelProviders.of(this).get(RequestMakerModel.class);
        LiveData<ArrayList<User>> data = model.getAllUsersData();
        model.GetAllUsers(client);

        data.observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                String help = "";
                for(int i =0; i < users.size(); i++){
                    help= help + " " + users.get(i).getName()+ " ";
                }
                textReq.setText(help);
            }
        });
    }


    //Добавить пользователя POST
    public void AddUser(View view){
        RequestMakerModel model = ViewModelProviders.of(this).get(RequestMakerModel.class);
        LiveData<Integer> data = model.getDataInt();

        User Sem = new User();
        Sem.setLogin("Sem11");
        Sem.setName("Sem");
        Sem.setSurname("Kovanov");
        Sem.setPassword("1111");
        Sem.setAge(18);
        Sem.setGender("male");

        model.addUser(client, Sem);

        data.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                textInt.setText(integer.toString());
            }
        });
    }

    //-----------------------------------      Работа с Постами Post        ----------------------------------------------

    // Получить все посты с указанным логином автора
    public void GetAllPostsByUserLogin(View view){
        RequestMakerModel model = ViewModelProviders.of(this).get(RequestMakerModel.class);
        LiveData<ArrayList<Post>> data = model.getAllPosts();
        model.GetAllPostsByLogin(client, "andrey 123");

        data.observe(this, new Observer<ArrayList<Post>>() {
            @Override
            public void onChanged(ArrayList<Post> posts) {
                String help = "";
                for(int i =0; i < posts.size(); i++){
                    help = help +  posts.get(i).getLogin() + " " +  posts.get(i).getName() + " " +  posts.get(i).getLocation();
                }
                textReq.setText(help);
            }
        });

    }
    // Получить все имеющиеся в базе посты
    public void GetAllPosts(View view){
        RequestMakerModel model = ViewModelProviders.of(this).get(RequestMakerModel.class);
        LiveData<ArrayList<Post>> data = model.getAllPosts();
        model.GetAllPosts(client);

        data.observe(this, new Observer<ArrayList<Post>>() {
            @Override
            public void onChanged(ArrayList<Post> posts) {
                String help = "";
                for(int i =0; i < posts.size(); i++){
                    help = help +  posts.get(i).getLogin() + " " +  posts.get(i).getName() + " " +  posts.get(i).getLocation();
                }
                textReq.setText(help);
            }
        });

    }
    // Получить все посту по названию места
    public void GetAllPostsByNamePlace(View view){
        RequestMakerModel model = ViewModelProviders.of(this).get(RequestMakerModel.class);
        LiveData<ArrayList<Post>> data = model.getAllPosts();
        model.GetAllPostsByNamePlace(client, "г.Москва ул. Веселая");

        data.observe(this, new Observer<ArrayList<Post>>() {
            @Override
            public void onChanged(ArrayList<Post> posts) {
                String help = "";
                for(int i =0; i < posts.size(); i++){
                    help = help +  posts.get(i).getLogin() + " " +  posts.get(i).getName() + " " +  posts.get(i).getLocation();
                }
                textReq.setText(help);
            }
        });

    }
    // Добавить новый пост
    public void AddPost(View view){
        RequestMakerModel model = ViewModelProviders.of(this).get(RequestMakerModel.class);
        LiveData<Integer> data = model.getDataInt();

       Post post = new Post();
       post.setName("Пост 11");
        post.setLogin("Anna22");
        post.setDate("11.11.2020");
        post.setLocation("Москва ул.Бауманская");
        post.setText("Пост пост пост");
        post.setImg("http://");

        model.addPost(client, post);

        data.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                textReq.setText(integer.toString());
            }
        });
    }
//-----------------------------------      Работа с Местами Place        ----------------------------------------------
    // Добавить новое место
    public void AddPlace(View view){
        RequestMakerModel model = ViewModelProviders.of(this).get(RequestMakerModel.class);
        LiveData<Integer> data = model.getDataInt();

       Place place = new Place();
       place.setName("Выставка картин1");
       place.setLocation("г.Москва, ул.Большая д.2");
       place.setImg("http://");


        model.addPlace(client, place);

        data.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                textReq.setText(integer.toString());
            }
        });
    }
    // Получить все места из БД
    public void GetAllPlaces(View view){
        RequestMakerModel model = ViewModelProviders.of(this).get(RequestMakerModel.class);
        LiveData<ArrayList<Place>> data = model.getAllPlaces();
        model.GetAllPlaces(client);

        data.observe(this, new Observer<ArrayList<Place>>() {
            @Override
            public void onChanged(ArrayList<Place> places) {
                String help = "";
                for(int i =0; i < places.size(); i++){
                    help = help +  places.get(i).getName() + " " +  places.get(i).getLocation();
                }
                textReq.setText(help);
            }
        });

    }
    // Получить информацию о месте по имени места
    public void GetPlaceByName(View view) {

        RequestMakerModel model = ViewModelProviders.of(this).get(RequestMakerModel.class);
        LiveData<Place> data = model.getPlaceData();
        model.GetPlaceByName(client,"Театр Большой");



        data.observe(this, new Observer<Place>() {
            @Override
            public void onChanged(Place place) {
                textReq.setText(place.getName() + " " + place.getLocation());
            }
        });

    }

}
