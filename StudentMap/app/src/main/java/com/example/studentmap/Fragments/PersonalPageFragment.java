package com.example.studentmap.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.studentmap.MainActivity;
import com.example.studentmap.R;



public class PersonalPageFragment extends Fragment {

    TextView name;
    TextView surname;
    TextView age;
    TextView login;
    SharedPreferences myPreferences;

    Button back;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView =  inflater.inflate(R.layout.fragment_personal_page, container, false);
        myPreferences = getActivity().getSharedPreferences("mysettings", Context.MODE_PRIVATE);

        name = (TextView) rootView.findViewById(R.id.name_page);
        surname = (TextView) rootView.findViewById(R.id.surname_page);
        age = (TextView) rootView.findViewById(R.id.age_page);
        login = (TextView) rootView.findViewById(R.id.login_page);

        back = (Button) rootView.findViewById(R.id.exit);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapFragment mapFragment = new MapFragment();



                SharedPreferences.Editor editor = myPreferences.edit();

                editor.clear().commit();
                out();

            }
        });




        name.setText(myPreferences.getString("name","0"));
        surname.setText(myPreferences.getString("surname", "0"));
        age.setText(myPreferences.getString("age", "0"));
        login.setText(myPreferences.getString("login", "0"));






        return rootView;
    }
    void out(){
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}