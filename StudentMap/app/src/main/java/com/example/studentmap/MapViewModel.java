package com.example.studentmap;

import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
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
    private MutableLiveData<List<List<HashMap<String, String>>>> liveDataRoute;
    private List<List<HashMap<String, String>>> routes;
    private String urlRoute;
    private Integer spinner;

    public void setSpinner(int sp) {
        spinner = sp;
    }

    public Integer getSpinner() {
        return spinner;
    }

    public LiveData<List<List<HashMap<String, String>>>> getDataRoutes() {
        liveDataRoute = new MutableLiveData<>();
        loadDataRoutes();
        return liveDataRoute;
    }

    private void loadDataRoutes(){
        ParsePlace parser = new ParsePlace(this);
        parser.ParseRoute(urlRoute);
    }

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

    @Override
    public void responseRoute(List<List<HashMap<String, String>>> route) {
        routes = route;
        liveDataRoute.postValue(route);
    }

    public void setUrlRoute(String url){
        urlRoute = url;
    }
    public String getUrlRoute() {
        return urlRoute;
    }

}