package com.example.studentmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

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
        lastFragment = mapFragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.mapFragmentContainer, mapFragment).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == lastItemId) {
                return true;
            }
            switch (itemId) {
                case R.id.fragment2:
                    //getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().remove(lastFragment).commit();
                    MapFragment mapFragment = new MapFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mapFragmentContainer, mapFragment).commit();
                    lastItemId = itemId;
                    lastFragment = mapFragment;
                    return true;

                case R.id.fragment1:
                    //getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().remove(lastFragment).commit();
                    ListFragment listFragment = new ListFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.listFragmentContainer, listFragment).commit();
                    lastItemId = itemId;
                    lastFragment = listFragment;
                    return true;
            }

            return false;
        }
    };
}