package com.example.studentmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.studentmap.Fragments.FeedListFragment;
import com.example.studentmap.Fragments.ListFragment;
import com.example.studentmap.Fragments.Login;
import com.example.studentmap.Fragments.MapFragment;
import com.example.studentmap.Fragments.PersonalPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Console;
import java.util.ArrayList;

//import kotlin.Unit;
//import kotlin.jvm.functions.Function1;
//import meow.bottomnavigation.MeowBottomNavigation;


public class MainActivity extends AppCompatActivity {

    //MeowBottomNavigation bottomNavigation;
    com.etebarian.meowbottomnavigation.MeowBottomNavigation nav;
    SharedPreferences myPreferences;
    int fragNum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        if (savedInstanceState!=null) {
            fragNum = savedInstanceState.getInt("fr");
        }
        //bottomNavigation = findViewById(R.id.nav);
        nav = findViewById(R.id.btm_nav);
        nav.add(new com.etebarian.meowbottomnavigation.MeowBottomNavigation.Model(1,R.drawable.ic_list_white_48dp));
        nav.add(new com.etebarian.meowbottomnavigation.MeowBottomNavigation.Model(2,R.drawable.ic_map_white_24dp));
        nav.add(new com.etebarian.meowbottomnavigation.MeowBottomNavigation.Model(3,R.drawable.ic_post_list));
        nav.add(new com.etebarian.meowbottomnavigation.MeowBottomNavigation.Model(4,R.drawable.ic_account_circle_white_24dp));



        nav.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

                //Toast.makeText(getApplicationContext(), "Clicked item" + item.getId(), Toast.LENGTH_SHORT).show();
                int itemId = item.getId();
                switchFragment(itemId);
            }});

        nav.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                }
            });
        nav.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });

        myPreferences = getSharedPreferences("mysettings", Context.MODE_PRIVATE);


            if(myPreferences.getString("auth","0") == "0"){
                Log.d("Pref","IN");

                if (getSupportFragmentManager().findFragmentByTag("Login") == null) {
                    Login login = new Login();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, login, "LoginFragment").commit();
                }

        }else{
                Log.d("Pref",myPreferences.getString("mysettings","0") );
//                MapFragment mapFragment = new MapFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mapFragment, "MapFragment").commit();

                if (getSupportFragmentManager().findFragmentByTag("MapFragment") == null) {
                    MapFragment mapFragment = new MapFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mapFragment, "MapFragment").commit();
                }else  {
                    Fragment fragment =  getSupportFragmentManager().findFragmentByTag("MapFragment");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment, "MapFragment").commit();
                }
                if (fragNum != 0) {
                    switchFragment(fragNum);
                }

            }




    }

    void switchFragment(int n) {
        switch (n) {
            case 2:
                if (getSupportFragmentManager().findFragmentByTag("MapFragment") == null) {
                    MapFragment mapFragment = new MapFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mapFragment, "MapFragment").addToBackStack(null).commit();

                }else {
                    Fragment fragment =  getSupportFragmentManager().findFragmentByTag("MapFragment");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment, "MapFragment").addToBackStack(null).commit();
                }
                fragNum=2;
                break;
            case 1:
                if (getSupportFragmentManager().findFragmentByTag("ListFragment") == null) {
                    ListFragment listFragment = new ListFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, listFragment, "ListFragment").addToBackStack(null).commit();
                }else {
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag("ListFragment");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment, "ListFragment").addToBackStack(null).commit();
                }
                fragNum=1;
                break;
            case 3:
                if (getSupportFragmentManager().findFragmentByTag("FeedFragment") == null) {
                    FeedListFragment feedListFragment = new FeedListFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, feedListFragment, "FeedFragment").addToBackStack(null).commit();
                }else {
                    Fragment fragment =  getSupportFragmentManager().findFragmentByTag("FeedFragment");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment, "FeedFragment").addToBackStack(null).commit();
                }
                fragNum=3;
                break;
            case 4:
                if (getSupportFragmentManager().findFragmentByTag("Login") == null) {
                    PersonalPageFragment personalPageFragment = new PersonalPageFragment();

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, personalPageFragment, "personalPageFragment").addToBackStack(null).commit();

                }else {
                    Fragment fragment =  getSupportFragmentManager().findFragmentByTag("Login");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment, "Login").addToBackStack(null).commit();
                }
                fragNum=4;
                break;
        }
        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF="+fragNum);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("fr", fragNum);
    }


}