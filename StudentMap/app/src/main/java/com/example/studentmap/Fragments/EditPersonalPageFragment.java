package com.example.studentmap.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.studentmap.FireBase.Firebase;
import com.example.studentmap.Network.Entity.User;
import com.example.studentmap.Network.RequestMakerModel;
import com.example.studentmap.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import okhttp3.OkHttpClient;


public class EditPersonalPageFragment extends Fragment {



    EditText name;
    EditText surname;
    EditText age;
    RadioGroup radioGroup;
    EditText login;
    EditText password;
    SharedPreferences myPreferences;
    public Uri imageUri = null;
    Button selectImageButton;
    ImageView imagePerson;
    private StorageReference mStorageRef;
    Firebase firebase;
    private OkHttpClient client;

    Button edit;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStorageRef = FirebaseStorage.getInstance().getReference("ImageIconDb");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView =  inflater.inflate(R.layout.fragment_edit_personal_page, container, false);
        myPreferences = getActivity().getSharedPreferences("mysettings", Context.MODE_PRIVATE);

        firebase = new Firebase();
        LiveData<String> newUri = firebase.getUridata();
        newUri.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Log.d("Firbase_img_ready",s);
                PersonalPageFragment personalPageFragment = new PersonalPageFragment();
                User user = new User();
                user.setName(name.getText().toString());
                user.setSurname(surname.getText().toString());
                user.setAge(Integer.parseInt(age.getText().toString()));
                user.setImg(s);
                user.setLogin(login.getText().toString());
                user.setPassword(password.getText().toString());
                SharedPreferences.Editor editor = myPreferences.edit();
                editor.putString("personImg",s);
                editor.apply();




                sendPostOnServer(user);



                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, personalPageFragment, "PersonalPageFragment").commit();
            }
        });

        name = rootView.findViewById(R.id.edit_name);
        surname = rootView.findViewById(R.id.edit_surname);
        age = rootView.findViewById(R.id.edit_age);
        login = rootView.findViewById(R.id.edit_login);
        radioGroup = rootView.findViewById(R.id.editRadioGroup);
        password = rootView.findViewById(R.id.edit_password);
        selectImageButton = rootView.findViewById(R.id.change_photo);
        imagePerson = rootView.findViewById(R.id.imagePerson);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });

        edit = rootView.findViewById(R.id.edit_btn);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = myPreferences.edit();

                editor.putString("login", login.getText().toString());
                editor.putString("name", name.getText().toString());
                editor.putString("surname", surname.getText().toString());
                editor.putString("age", age.getText().toString());
                editor.apply();
                PersonalPageFragment personalPageFragment = new PersonalPageFragment();

                if(imageUri != null ){
                    firebase.uploadImageIcon(imageUri, mStorageRef);


                }else Toast.makeText(getContext(),"Заполните все поля!", Toast.LENGTH_SHORT).show();
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, personalPageFragment, "PersonalPageFragment").commit();


            }
        });

        name.setText(myPreferences.getString("name","0"));
        surname.setText(myPreferences.getString("surname", "0"));
        age.setText(myPreferences.getString("age", "0"));
        login.setText(myPreferences.getString("login", "0"));
        password.setText(myPreferences.getString("password", "0"));
        String gender = myPreferences.getString("gender", "0");
        Log.d("TAG", "onCreateView:" + gender);
       View radioButtonn = radioGroup.findViewById(R.id.editRadioButtonMale);
       radioButtonn.setSelected(true);
//        if (gender == "Мужской") {
//            View radioButton = radioGroup.findViewById(R.id.editRadioButtonMale);
//            radioButton.setSelected(true);
//        }
//        if (gender == "Женский"){
//            View radioButton = radioGroup.findViewById(R.id.editRadioButtonFemale);
//            radioButton.setSelected(true);
//        }


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
            imagePerson.setImageURI(imageUri);

        }
    }


    void sendPostOnServer(User user){

        RequestMakerModel model = ViewModelProviders.of(this).get(RequestMakerModel.class);
        LiveData<Integer> data = model.getDataInt();
        client = new OkHttpClient();

        model.updateUser(client,user);

        data.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Toast.makeText(getContext(), "Учетная запись изменена!!!", Toast.LENGTH_SHORT).show();
                Log.d("ReadyAll", "ready");



            }
        });
    }
}