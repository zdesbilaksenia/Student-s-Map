package com.example.studentmap.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.studentmap.Fragments.Registration;
import com.example.studentmap.MainActivity;
import com.example.studentmap.Network.Entity.User;
import com.example.studentmap.Network.RequestMakerModel;
import com.example.studentmap.R;
import com.google.android.material.textfield.TextInputEditText;

import okhttp3.OkHttpClient;


public class Login extends Fragment {
    int count = 0;
    OkHttpClient client;
    TextInputEditText login;
    TextInputEditText password;
    Button enterBtn;
    Button registrBtn;
    SharedPreferences myPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login,
                container, false);

        login = view.findViewById(R.id.login);
        password = view.findViewById(R.id.password);
        enterBtn = view.findViewById(R.id.btn_enter);
        registrBtn = view.findViewById(R.id.btn_registr);
        myPreferences = getActivity().getSharedPreferences("mysettings", Context.MODE_PRIVATE);

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Login = login.getText().toString();
                String Password = password.getText().toString();
                if(count <= 4){
                    getUser(Login, Password);
                }else{
                    Toast.makeText(getContext(), "Зарегистрируйтесь!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        registrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registration registration = new Registration();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, registration, "Registration").commit();
            }
        });

        return view;
    }

    void getUser(String login, String password) {
        RequestMakerModel model = ViewModelProviders.of(this).get(RequestMakerModel.class);
        LiveData<User> data = model.getUserData();
        client = new OkHttpClient();

        model.GetUserByLoginAndPassword(client,login,password);

        data.observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user.getName() == "NOT_FOUND") {
                    count++;
                    Log.d("Answer", "User not found");
                    Toast.makeText(getContext(), "Учетная запись не найдена!!!", Toast.LENGTH_SHORT).show();

                }else{

                    String name = user.getName();
                    String surname = user.getSurname();
                    Integer age = user.getAge();
                    String gender = user.getGender();
                    String login = user.getLogin();
                    String password = user.getPassword();
                    String icon = user.getImg();



                    Log.d("Ready", user.getName());




                        // Сохраняем факт входа пользователя
                        SharedPreferences.Editor editor = myPreferences.edit();
                        editor.putString("auth","1");
                        editor.putString("login", login);
                        editor.putString("name", name);
                        editor.putString("surname", surname);
                        editor.putString("age", Integer.toString(age));
                        editor.putString("password", password);
                        editor.putString("personImg", icon);
                        editor.apply();
                        close();






                }

            }
        });

    }
    void close(){
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

    }

}
