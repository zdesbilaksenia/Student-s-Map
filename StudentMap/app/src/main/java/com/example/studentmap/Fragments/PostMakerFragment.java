package com.example.studentmap.Fragments;

import android.content.Intent;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import java.util.Date;

import okhttp3.OkHttpClient;

public class PostMakerFragment extends Fragment {

    ImageView imageView;
    Button addPostButton;
    Button selectImageButton;
    Button backButton;
    EditText textPost;
    public Uri imageUri = null;
    private StorageReference mStorageRef;
    Firebase firebase;
    private OkHttpClient client;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStorageRef = FirebaseStorage.getInstance().getReference("ImageDb");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         firebase = new Firebase();
        LiveData<String> newUri = firebase.getUridata();
        newUri.observe(getViewLifecycleOwner(), new Observer<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(String s) {
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
                post.setName("Name");
                postDb.setName("Name");
                post.setLocation("Some location");

                sendPostToDatabase(postDb);

                sendPostOnServer(post);
            }
        });

        View rootView = inflater.inflate(R.layout.fragment_post_maker, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
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

            }
        });

    }


    private void sendPostToDatabase( Postdb post){

        RoomViewModel roomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        roomViewModel.addPost(post);
    }

}