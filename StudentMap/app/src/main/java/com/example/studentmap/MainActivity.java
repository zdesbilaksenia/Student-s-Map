package com.example.studentmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import com.example.studentmap.Fragments.FeedListFragment;
import com.example.studentmap.Fragments.ListFragment;
import com.example.studentmap.Fragments.Login;
import com.example.studentmap.Fragments.MapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences myPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
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
                }else {
                    Fragment fragment =  getSupportFragmentManager().findFragmentByTag("MapFragment");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment, "MapFragment").commit();
                }

                BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
                navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
            }




    }




    final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            switch (itemId) {
                case R.id.fragment2:
                    if (getSupportFragmentManager().findFragmentByTag("MapFragment") == null) {
                        MapFragment mapFragment = new MapFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mapFragment, "MapFragment").commit();
                    }else {
                        Fragment fragment =  getSupportFragmentManager().findFragmentByTag("MapFragment");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment, "MapFragment").commit();
                    }

                    return true;

                case R.id.fragment1:
                    if (getSupportFragmentManager().findFragmentByTag("ListFragment") == null) {
                        ListFragment listFragment = new ListFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, listFragment, "ListFragment").commit();
                    }
                    return true;

                case R.id.fragment3:
                    if (getSupportFragmentManager().findFragmentByTag("FeedFragment") == null) {
                        FeedListFragment feedListFragment = new FeedListFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, feedListFragment, "FeedFragment").commit();
                    }
                    return true;

                case R.id.fragment4:
                    if (getSupportFragmentManager().findFragmentByTag("Login") == null) {
                    Login login = new Login();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, login, "LoginFragment").commit();
                    }
                    return true;

            }

            return false;
        }
    };
}