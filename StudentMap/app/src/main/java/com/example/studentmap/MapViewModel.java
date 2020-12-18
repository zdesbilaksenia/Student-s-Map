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

public class MapViewModel extends AndroidViewModel implements ResponseCallback{
    private MutableLiveData<List<Place>> liveData;
    private List<Place> listPlace = new ArrayList<>();
    private Location currentLocation;
    private String url;
    private String lastUrl;

    public LiveData<List<Place>> getData(){
        if (liveData == null || lastUrl != url) {
            liveData = new MutableLiveData<>();
            loadData();
        }
        return liveData;
    }

    public void setUrl(String url){
        lastUrl = this.url;
        this.url = url;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location location){
        currentLocation = location;
    }

    public String getUrl() {
        return url;
    }

    private void loadData(){
        ParsePlace parser = new ParsePlace(this);
        parser.Parse(url, currentLocation);
    }

    public MapViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void response(List<Place> placeList) {
        this.listPlace = placeList;
        liveData.postValue(listPlace);

    }
}