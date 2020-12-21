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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.studentmap.MainActivity;
import com.example.studentmap.Network.Entity.User;
import com.example.studentmap.Network.RequestMakerModel;
import com.example.studentmap.R;
import com.google.android.material.textfield.TextInputEditText;

import okhttp3.OkHttpClient;


public class Registration extends Fragment {

    OkHttpClient client;
    TextInputEditText newLogin;
    TextInputEditText newPassword;
    TextInputEditText nameRegistration;
    TextInputEditText surnameRegistration;
    TextInputEditText ageRegistration;
    RadioGroup radioGroup;
    Button enterBtn;
    Button registrationBtn;
    String name;
    String surname;
    String login;
    String age;
    String gender;
    String password;



    SharedPreferences myPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration,
                container, false);
        myPreferences = getActivity().getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        newLogin = view.findViewById(R.id.new_login);
        newPassword = view.findViewById(R.id.new_password);
        enterBtn = view.findViewById(R.id.btn_to_enter);
        registrationBtn = view.findViewById(R.id.btn_registration);
        nameRegistration = view.findViewById(R.id.name_registration);
        surnameRegistration = view.findViewById(R.id.surname_registration);
        ageRegistration = view.findViewById(R.id.age_registration);
        radioGroup = view.findViewById(R.id.radioGroup);

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login login = new Login();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, login, "Login").commit();
            }
        });


        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = newLogin.getText().toString();
                name = nameRegistration.getText().toString();
                surname = surnameRegistration.getText().toString();
                age = ageRegistration.getText().toString();
                password = newPassword.getText().toString();
               // gender =


                User user = new User();
                user.setLogin(newLogin.getText().toString());
                user.setPassword(newPassword.getText().toString());
                user.setName(nameRegistration.getText().toString());
                user.setSurname(surnameRegistration.getText().toString());
                user.setAge(Integer.parseInt(ageRegistration.getText().toString()));
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(radioButtonID);
                int index = radioGroup.indexOfChild(radioButton);
                RadioButton radioBtn = (RadioButton) radioGroup.getChildAt(index);
                String selectedGender = radioBtn.getText().toString();
                user.setGender(selectedGender);
                registration(user);
            }
        });

        return view;
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

                    SharedPreferences.Editor editor = myPreferences.edit();
                    editor.putString("auth","1");
                    editor.putString("login", login);
                    editor.putString("name", name);
                    editor.putString("surname", surname);
                    editor.putString("age", age);
                    editor.putString("password", password);
                    editor.apply();
                    close();
                }else Toast.makeText(getContext(), "Ошибка создания учетной записи!!!", Toast.LENGTH_SHORT).show();
                Log.d("Answer", Integer.toString(integer));
            }
        });

    }


    void close(){
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

    }
}