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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.studentmap.MainActivity;
import com.example.studentmap.R;



public class PersonalPageFragment extends Fragment {

    TextView name;
    TextView surname;
    TextView age;
    TextView login;
    SharedPreferences myPreferences;

    Button back;
    Button edit;
    ImageView personImage;







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
        personImage = (ImageView) rootView.findViewById(R.id.imagePerson);
        Glide.with((getContext())).load(R.drawable.avatar).into(personImage);

        back = (Button) rootView.findViewById(R.id.exit);
        edit = (Button) rootView.findViewById(R.id.edit);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapFragment mapFragment = new MapFragment();



                SharedPreferences.Editor editor = myPreferences.edit();

                editor.clear().commit();
                out();

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditPersonalPageFragment editFragment = new EditPersonalPageFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, editFragment, "EditPersonalPageFragment").commit();
            }
        });




        name.setText(myPreferences.getString("name","0"));
        surname.setText(myPreferences.getString("surname", "0"));
        age.setText(myPreferences.getString("age", "0"));
        login.setText(myPreferences.getString("login", "0"));
        if(myPreferences.getString("personImg", "0") != "0"){
            Glide.with(getContext()).load(myPreferences.getString("personImg", "0")).into(personImage);
        }






        return rootView;
    }
    void out(){
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
