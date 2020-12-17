package com.example.studentmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.studentmap.Fragments.EnterFragment;
import com.example.studentmap.Fragments.PostMakerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;

import com.yandex.mapkit.mapview.MapView;

public class MainActivity extends AppCompatActivity {

    private int lastItemId = R.id.fragment2;
    Fragment lastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        MapFragment mapFragment = new MapFragment();
        //   lastFragment = mapFragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mapFragment, "MapFragment").commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }




    final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            switch (itemId) {
                case R.id.fragment2:
                    //getSupportFragmentManager().popBackStack();
                    //getSupportFragmentManager().beginTransaction();
                    if (getSupportFragmentManager().findFragmentByTag("MapFragment") == null) {
                        MapFragment mapFragment = new MapFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mapFragment, "MapFragment").commit();
                    }
                    //lastItemId = itemId;
                    //lastFragment = mapFragment;
                    return true;

                case R.id.fragment1:
                    //getSupportFragmentManager().popBackStack();
                    //getSupportFragmentManager().beginTransaction();
                    if (getSupportFragmentManager().findFragmentByTag("ListFragment") == null) {
                        ListFragment listFragment = new ListFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, listFragment, "ListFragment").commit();
                    }
                    //lastItemId = itemId;
                    //lastFragment = listFragment;
                    return true;

                case R.id.fragment3:
                    PostMakerFragment postMakerFragment = new PostMakerFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.fragmentContainer,postMakerFragment);
                    ft.commit();
                    return true;

                case R.id.fragment4:
                    EnterFragment enterFragment = new EnterFragment();
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.addToBackStack(null);
                    ft1.replace(R.id.fragmentContainer,enterFragment);
                    ft1.commit();
                    return true;

            }

            return false;
        }
    };
}