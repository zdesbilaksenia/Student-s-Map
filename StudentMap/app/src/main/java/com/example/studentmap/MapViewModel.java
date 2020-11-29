package com.example.studentmap;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapViewModel extends AndroidViewModel {

    FusedLocationProviderClient client;
    Location currentLocation;

    public MapViewModel(@NonNull Application application) {
        super(application);
    }

    public void getCurrentLocation(MapView mapView) {
        Context context = getApplication();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }
        client = LocationServices.getFusedLocationProviderClient(context);
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                }
                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        //LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                        LatLng latLng = new LatLng(55.751244, 37.618423);
                        MarkerOptions options = new MarkerOptions().position(latLng).title("I am here").visible(true);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                        googleMap.addMarker(options);
                    }
                });
            }
        });
    }

}
