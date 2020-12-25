package com.example.studentmap.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.studentmap.FireBase.Firebase;
import com.example.studentmap.Network.Entity.Post;
import com.example.studentmap.Network.RequestMakerModel;
import com.example.studentmap.R;
import com.example.studentmap.RoomDb.Entity.Postdb;
import com.example.studentmap.RoomDb.RoomViewModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.LocalDate;

import okhttp3.OkHttpClient;

public class PostMakerFragment extends Fragment {

    ImageView imageView;
    Button addPostButton;
    Button selectImageButton;
    RatingBar ratingBar;
    Button backButton;
    EditText textPost;
    public Uri imageUri = null;
    private StorageReference mStorageRef;
    Firebase firebase;
    private OkHttpClient client;
    SharedPreferences myPreferences;
    float rating;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStorageRef = FirebaseStorage.getInstance().getReference("ImageDb");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myPreferences = getActivity().getSharedPreferences("mysettings", Context.MODE_PRIVATE);


         firebase = new Firebase();
        LiveData<String> newUri = firebase.getUridata();
        newUri.observe(getViewLifecycleOwner(), new Observer<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(String s) {
                String address = "";
                String name = "";
                Bundle bundle = getArguments();
                if(bundle != null){
                     address = bundle.getString("address");
                     name = bundle.getString("name");
                    Log.d("address", address);
                    Log.d("name", name);
                }
                Log.d("Firbase_img_ready ",s);
                Post post = new Post();
                Postdb postDb = new Postdb();
                post.setImg(s);
                LocalDate date = LocalDate.now();
                String dateString =Integer.toString(date.getDayOfMonth()) + "." + Integer.toString(date.getMonthValue()) + "."
                        +Integer.toString(date.getYear()) ;

                post.setDate(dateString);
                postDb.setDate(dateString);
                post.setText(textPost.getText().toString());
                postDb.setText(textPost.getText().toString());

                postDb.setName("Пост " + name);
                postDb.setLocation(address);
                post.setLocation(address);
                post.setName("Пост " + name);
                post.setLogin(myPreferences.getString("login","0"));
                postDb.setLogin(myPreferences.getString("login","0"));
                rating = ratingBar.getRating();
                Log.d("Rating", Float.toString(rating));
                post.setRating(rating);
                postDb.setRating(rating);

               // sendPostToDatabase(postDb);


                sendPostOnServer(post);
            }
        });

        View rootView = inflater.inflate(R.layout.fragment_post_maker, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.imagePerson);
        ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar1);

        selectImageButton = (Button) rootView.findViewById(R.id.button);
        addPostButton = (Button) rootView.findViewById(R.id.button2);
        textPost = (EditText) rootView.findViewById(R.id.textPost);


        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });

        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textPost.getText().toString();
                if(imageUri != null && text.length() > 1){
                    firebase.uploadImage(imageUri, mStorageRef);

                }else Toast.makeText(getContext(),"Заполните все поля!", Toast.LENGTH_SHORT).show();
            }
        });



        return rootView;
    }



    private void getImage(){
        Intent intentChooser = new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && data.getData() != null) {

            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }
    }

    private void sendPostOnServer(Post post){
        RequestMakerModel model = ViewModelProviders.of(this).get(RequestMakerModel.class);
        LiveData<Integer> data = model.getDataInt();
        client = new OkHttpClient();
        model.addPost(client,post);
        data.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Toast.makeText(getContext(), "Пост добавлен!", Toast.LENGTH_SHORT).show();
                Log.d("ReadyAll", "ready");
                end();


            }
        });

    }


    private void sendPostToDatabase( Postdb post){

        RoomViewModel roomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        roomViewModel.addPost(post);
    }
    void end(){
        MapFragment mapFragment = new MapFragment();

        FeedListFragment feedListFragment = new FeedListFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, feedListFragment, "FeedListFragment").commit();
    }

}