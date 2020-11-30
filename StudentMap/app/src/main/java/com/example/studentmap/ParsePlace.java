package com.example.studentmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParsePlace {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    private MutableLiveData<List<Place>> listPlaces;

    String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
            "?location=55.761244,37.628423"+
            "&radius=2000" + "&type=atm" + "&sensor=true" +
            "&key=" + "AIzaSyBomRHM2cJo2o33ZULSbZHbisJs4JZQSKE";
    final String[] finalData = new String[1];

    LiveData<List<Place>> getData(){
        if(listPlaces== null){
            listPlaces = new MutableLiveData<>();
        }
        return listPlaces;
    }
    public void Parse() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                String dataUrl = null;
                try {
                    dataUrl = downloadUrl(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                finalData[0] = dataUrl;

                JsonParser jsonParser = new JsonParser();
                List<HashMap<String, String>> mapList = null;
                JSONObject object = null;
                try {
                    object = new JSONObject(finalData[0]);
                    mapList = jsonParser.parseResult(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                List<Place> places = new ArrayList<>();
                for (int i = 0; i < mapList.size(); i++) {
                    HashMap<String, String> hashMapList = mapList.get(i);
                    Place temp = new Place();
                    temp.setLatitude(Double.parseDouble(hashMapList.get("lat")));
                    temp.setLongitude(Double.parseDouble(hashMapList.get("lng")));
                    temp.setName(hashMapList.get("name"));
                    places.add(temp);
                }
                listPlaces.postValue(places);
            }
        });
        executor.shutdown();

    }

    private String downloadUrl(String string) throws IOException {
        URL url = new URL(string);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader((new InputStreamReader(stream)));
        StringBuilder builder = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        builder.append(line);
        }
        String data = builder.toString();
        reader.close();
        return data;
    }
}
