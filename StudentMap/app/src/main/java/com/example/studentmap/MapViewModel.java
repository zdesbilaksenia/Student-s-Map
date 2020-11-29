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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapViewModel extends AndroidViewModel {
    private MutableLiveData<List<Place>> liveData;

    String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
            "?location=55.751244,37.618423" +
            "&radius=2000" + "&type=atm" + "&sensor=true" +
            "&key=" + "AIzaSyBomRHM2cJo2o33ZULSbZHbisJs4JZQSKE";

    public LiveData<List<Place>> getData(){
        if (liveData == null) {
            liveData = new MutableLiveData<>();
            loadData();
        }
        return liveData;
    }

    private void loadData(){
        List<Place> places = new ArrayList<>();
        for (int i =0; i < 10; i ++) {
            Place pl = new Place();
            pl.setLatitude(55+i*0.02);
            pl.setLongitude(37+i*0.02);
            pl.setName("Check");
            places.add(pl);
        }
        liveData.postValue(places);
    }

    public MapViewModel(@NonNull Application application) {
        super(application);
    }
}
