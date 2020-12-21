package com.example.studentmap.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.studentmap.R;


public class EditPersonalPageFragment extends Fragment {



    EditText name;
    EditText surname;
    EditText age;
    RadioGroup radioGroup;
    EditText login;
    EditText password;
    SharedPreferences myPreferences;

    Button edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView =  inflater.inflate(R.layout.fragment_edit_personal_page, container, false);
        myPreferences = getActivity().getSharedPreferences("mysettings", Context.MODE_PRIVATE);

        name = rootView.findViewById(R.id.edit_name);
        surname = rootView.findViewById(R.id.edit_surname);
        age = rootView.findViewById(R.id.edit_age);
        login = rootView.findViewById(R.id.edit_login);
        radioGroup = rootView.findViewById(R.id.editRadioGroup);
        password = rootView.findViewById(R.id.edit_password);

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
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, personalPageFragment, "PersonalPageFragment").commit();
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
}