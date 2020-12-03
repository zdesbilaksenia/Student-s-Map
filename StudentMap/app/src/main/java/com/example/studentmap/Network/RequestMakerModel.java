package com.example.workwithapi;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workwithapi.Entity.Place;
import com.example.workwithapi.Entity.Post;
import com.example.workwithapi.Entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;

public class RequestMakerModel extends ViewModel {


    private ExecutorService service = Executors.newFixedThreadPool(5);
    private MutableLiveData<User> liveDataUser;
    private MutableLiveData<ArrayList<User>> livedataAllUser;
    private MutableLiveData<Integer> liveDataInt;
    private MutableLiveData<ArrayList<Post>> liveDataAllPosts;
    private MutableLiveData<ArrayList<Place>> liveDataAllPlaces;
    private MutableLiveData<Place> liveDataPlace;
    OkHttpClient client;


   // -------------------------------------- LiveData for User-------------------------------------------------
    LiveData<User> getUserData(){
        if(liveDataUser == null){
            liveDataUser = new MutableLiveData<>();
        }
        return liveDataUser;
    }
    LiveData<ArrayList<User>> getAllUsersData(){
        if(livedataAllUser == null){
            livedataAllUser = new MutableLiveData<>();
        }
        return livedataAllUser;
    }
    // -------------------------------------- LiveData for Post-------------------------------------------------

    LiveData<Integer> getDataInt(){
        if(liveDataInt == null){
            liveDataInt = new MutableLiveData<>();
        }
        return liveDataInt;
    }

    LiveData<ArrayList<Post>> getAllPosts(){
        if(liveDataAllPosts == null){
            liveDataAllPosts = new MutableLiveData<>();
        }
        return liveDataAllPosts;
    }

    // -------------------------------------- LiveData for Place-------------------------------------------------

    LiveData<ArrayList<Place>> getAllPlaces(){
        if(liveDataAllPlaces == null){
            liveDataAllPlaces = new MutableLiveData<>();
        }
        return liveDataAllPlaces;
    }

    LiveData<Place> getPlaceData(){
        if(liveDataPlace == null){
            liveDataPlace = new MutableLiveData<>();
        }
        return liveDataPlace;
    }

    public void  GetAllUsers(OkHttpClient client1){

        client = client1;
        final ArrayList<User> users = new ArrayList<User>();
        service.submit(new Runnable() {

            @Override
            public void run() {
                try {
                    Log.d("RequestMaker", "IN thread");
                    String response_text;
                    response_text = ApiCall.GET(client,RequestBuilder.buildURL("users"));
                    Log.d("RequestMaker_AllUsers", response_text);

                    if(response_text != "NOT_FOUND" && response_text != "SERVER_ERROR" && response_text != "ERROR"){

                        try {
                            JSONArray jsonArray = new JSONArray(response_text);

                            for(int  i =0; i < jsonArray.length(); i++){

                                User user = new User();
                                user.setLogin(jsonArray.getJSONObject(i).getString("login"));
                                user.setPassword(jsonArray.getJSONObject(i).getString("password"));
                                user.setName(jsonArray.getJSONObject(i).getString("name"));
                                user.setSurname(jsonArray.getJSONObject(i).getString("surname"));
                                user.setAge(jsonArray.getJSONObject(i).getInt("age"));
                                user.setGender(jsonArray.getJSONObject(i).getString("gender"));
                                users.add(user);
                                Log.d("user", user.getName() + "  "+  user.getPassword() + " " + user.getLogin()  );
                            }

                            livedataAllUser.postValue(users);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }


      User userHelp;

      void GetUserByLogin(OkHttpClient client1 ,final String login){
        client = client1;
        userHelp = new User();

        service.submit(new Runnable() {
            @Override
            public void run() {
                String response_text;
                try {
                    response_text = ApiCall.GET(client,RequestBuilder.buildURL("users",login));
                    Log.d("RequestMaker_ByLogin", response_text);
                    if(response_text != "NOT_FOUND" && response_text != "SERVER_ERROR" && response_text != "ERROR") {
                        try {
                            Log.d("make_Json_object", "IN");
                            JSONObject user_json = new JSONObject(response_text);
                            userHelp.setLogin(user_json.getString("login"));
                            userHelp.setPassword(user_json.getString("password"));
                            userHelp.setName(user_json.getString("name"));
                            userHelp.setSurname(user_json.getString("surname"));
                            userHelp.setGender(user_json.getString("gender"));
                            userHelp.setAge(user_json.getInt("age"));
                            Log.d("User","User: " + userHelp.getLogin() + " " + userHelp.getPassword() + " " + userHelp.getName());
                            liveDataUser.postValue(userHelp);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void GetUserByLoginAndPassword(OkHttpClient client1 , final String login, final String password){
        client = client1;
        userHelp = new User();

        service.submit(new Runnable() {
            @Override
            public void run() {
                String response_text;
                try {
                    response_text = ApiCall.GET(client,RequestBuilder.buildURL("users",login,password));
                    Log.d("ReqtMak_ByLoginAndPass", response_text);
                    if(response_text != "NOT_FOUND" && response_text != "SERVER_ERROR" && response_text != "ERROR") {
                        try {
                            Log.d("make_Json_object", "IN");
                            JSONObject user_json = new JSONObject(response_text);
                            userHelp.setLogin(user_json.getString("login"));
                            userHelp.setPassword(user_json.getString("password"));
                            userHelp.setName(user_json.getString("name"));
                            userHelp.setSurname(user_json.getString("surname"));
                            userHelp.setGender(user_json.getString("gender"));
                            userHelp.setAge(user_json.getInt("age"));
                            Log.d("User","User: " + userHelp.getLogin() + " " + userHelp.getPassword() + " " + userHelp.getName());

                            liveDataUser.postValue(userHelp);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    void addUser(OkHttpClient client1, final User user){
        client = client1;

        service.submit(new Runnable() {
            @Override
            public void run() {
                String response_text;
                try {
                    response_text = ApiCall.POST(client,RequestBuilder.buildURL("users"),RequestBuilder.addBody(user));
                    Log.d("RequestMaker_AddUser", response_text);
                    if(response_text != "NOT_FOUND" && response_text != "SERVER_ERROR" && response_text != "ERROR") {

                        Log.d("AddUserPost", "well");
                        liveDataInt.postValue(1);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    //-----------------------------------      Работа с Постами Post        ----------------------------------------------


    //Получить все посты с указанным логином
    void GetAllPostsByLogin(OkHttpClient client1, final String login){
        client = client1;
        final ArrayList<Post> posts = new ArrayList<>();

        service.submit(new Runnable() {

            @Override
            public void run() {
                try {
                    String response_text;
                    response_text = ApiCall.GET(client,RequestBuilder.buildURL("posts","login", login));
                    Log.d("RequestMaker_AllUsers", response_text);

                    if(response_text != "NOT_FOUND" && response_text != "SERVER_ERROR" && response_text != "ERROR"){

                        try {
                            JSONArray jsonArray = new JSONArray(response_text);

                            for(int  i =0; i < jsonArray.length(); i++){

                                Post post = new Post();
                                post.setName(jsonArray.getJSONObject(i).getString("name"));
                                post.setLogin(jsonArray.getJSONObject(i).getString("login"));
                                post.setDate(jsonArray.getJSONObject(i).getString("date"));
                                post.setLocation(jsonArray.getJSONObject(i).getString("location"));
                                post.setText(jsonArray.getJSONObject(i).getString("text"));
                                post.setImg(jsonArray.getJSONObject(i).getString("img"));
                                posts.add(post);
                                Log.d("Post", post.getName() + " " + post.getLocation() );
                            }

                            liveDataAllPosts.postValue(posts);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    // Получить все посты имеющиеся в БД
    void GetAllPosts(OkHttpClient client1){
        client = client1;
        final ArrayList<Post> posts = new ArrayList<>();

        service.submit(new Runnable() {

            @Override
            public void run() {
                try {
                    String response_text;
                    response_text = ApiCall.GET(client,RequestBuilder.buildURL("posts"));
                    Log.d("RequestMaker_AllUsers", response_text);

                    if(response_text != "NOT_FOUND" && response_text != "SERVER_ERROR" && response_text != "ERROR"){

                        try {
                            JSONArray jsonArray = new JSONArray(response_text);

                            for(int  i =0; i < jsonArray.length(); i++){

                                Post post = new Post();
                                post.setName(jsonArray.getJSONObject(i).getString("name"));
                                post.setLogin(jsonArray.getJSONObject(i).getString("login"));
                                post.setDate(jsonArray.getJSONObject(i).getString("date"));
                                post.setLocation(jsonArray.getJSONObject(i).getString("location"));
                                post.setText(jsonArray.getJSONObject(i).getString("text"));
                                post.setImg(jsonArray.getJSONObject(i).getString("img"));
                                posts.add(post);
                                Log.d("Post", post.getName() + " " + post.getLocation() );
                            }

                            liveDataAllPosts.postValue(posts);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    //Получить все посты с указанным местом
    void GetAllPostsByNamePlace(OkHttpClient client1, final String name){
        client = client1;
        final ArrayList<Post> posts = new ArrayList<>();

        service.submit(new Runnable() {

            @Override
            public void run() {
                try {
                    String response_text;
                    response_text = ApiCall.GET(client,RequestBuilder.buildURL("posts","name", name));
                    Log.d("RequestMaker_AllUsers", response_text);

                    if(response_text != "NOT_FOUND" && response_text != "SERVER_ERROR" && response_text != "ERROR"){

                        try {
                            JSONArray jsonArray = new JSONArray(response_text);

                            for(int  i =0; i < jsonArray.length(); i++){

                                Post post = new Post();
                                post.setName(jsonArray.getJSONObject(i).getString("name"));
                                post.setLogin(jsonArray.getJSONObject(i).getString("login"));
                                post.setDate(jsonArray.getJSONObject(i).getString("date"));
                                post.setLocation(jsonArray.getJSONObject(i).getString("location"));
                                post.setText(jsonArray.getJSONObject(i).getString("text"));
                                post.setImg(jsonArray.getJSONObject(i).getString("img"));
                                posts.add(post);
                                Log.d("Post", post.getName() + " " + post.getLocation() );
                            }

                            liveDataAllPosts.postValue(posts);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //Добавить новый пост
    void addPost(OkHttpClient client1, final Post post){
        client = client1;

        service.submit(new Runnable() {
            @Override
            public void run() {
                String response_text;
                try {
                    response_text = ApiCall.POST(client,RequestBuilder.buildURL("posts"),RequestBuilder.addBody(post));
                    Log.d("RequestMaker_AddPost", response_text);
                    if(response_text != "NOT_FOUND" && response_text != "SERVER_ERROR" && response_text != "ERROR") {

                        liveDataInt.postValue(1);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    //-----------------------------------      Работа с Местами Place        ----------------------------------------------

    // Добавить новое место
    void addPlace(OkHttpClient client1, final Place place){
        client = client1;

        service.submit(new Runnable() {
            @Override
            public void run() {
                String response_text;
                try {
                    response_text = ApiCall.POST(client,RequestBuilder.buildURL("places"),RequestBuilder.addBody(place));
                    Log.d("RequestMaker_AddPlace", response_text);
                    if(response_text != "NOT_FOUND" && response_text != "SERVER_ERROR" && response_text != "ERROR") {

                        liveDataInt.postValue(1);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    // Получить все места список
    void GetAllPlaces(OkHttpClient client1){
        client = client1;
        final ArrayList<Place> places = new ArrayList<>();

        service.submit(new Runnable() {

            @Override
            public void run() {
                try {
                    String response_text;
                    response_text = ApiCall.GET(client,RequestBuilder.buildURL("places"));
                    Log.d("RequestMaker_AllPlaces", response_text);

                    if(response_text != "NOT_FOUND" && response_text != "SERVER_ERROR" && response_text != "ERROR"){

                        try {
                            JSONArray jsonArray = new JSONArray(response_text);

                            for(int  i =0; i < jsonArray.length(); i++){

                                Place place = new Place();
                                place.setName(jsonArray.getJSONObject(i).getString("name"));
                                place.setLocation(jsonArray.getJSONObject(i).getString("location"));
                                place.setImg(jsonArray.getJSONObject(i).getString("img"));

                                places.add(place);
                                Log.d("Place", place.getName() );
                            }

                            liveDataAllPlaces.postValue(places);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    // Получить информацию о месте по имени места
    void GetPlaceByName(OkHttpClient client1 ,final String name){
        client = client1;
        final Place placeHelp = new Place();

        service.submit(new Runnable() {
            @Override
            public void run() {
                String response_text;
                try {
                    response_text = ApiCall.GET(client,RequestBuilder.buildURL("places",name));
                    Log.d("RequestMaker_ByName", response_text);
                    if(response_text != "NOT_FOUND" && response_text != "SERVER_ERROR" && response_text != "ERROR") {
                        try {
                            Log.d("make_Json_object", "IN");
                            JSONObject place_json = new JSONObject(response_text);

                            placeHelp.setName(place_json.getString("name"));
                            placeHelp.setLocation(place_json.getString("location"));
                            placeHelp.setImg(place_json.getString("img"));

                            Log.d("Place","Place: " + placeHelp.getName());
                            liveDataPlace.postValue(placeHelp);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
