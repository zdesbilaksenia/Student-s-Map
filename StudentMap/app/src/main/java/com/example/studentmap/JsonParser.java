package com.example.studentmap;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.util.concurrent.ForwardingBlockingQueue;

import org.json.JSONArray;
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

public class JsonParser {

    private HashMap<String, String> parseJsonObject(JSONObject object) {
        HashMap<String, String> dataList = new HashMap<>();
        try {
            String name = object.getString("name");
            String latitude = object.getJSONObject("geometry")
                    .getJSONObject("location").getString("lat");
            String longitude = object.getJSONObject("geometry")
                    .getJSONObject("location").getString("lng");
            String rating = "0";
            if (object.has("rating")) {
                rating = object.getString("rating");
                dataList.put("rating", rating);
            }
            String icon = object.getString("icon");
            String vicinity = object.getString("vicinity");
            dataList.put("name", name);
            dataList.put("lat", latitude);
            dataList.put("lng", longitude);
            dataList.put("rating", rating);
            dataList.put("icon", icon);
            dataList.put("vicinity", vicinity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    private String getPhotoReference(JSONObject object) throws JSONException {
        String photo = null;
        photo = object.getString("photo_reference");
        return photo;
    }

    public HashMap<String, String> parseJsonDist(JSONObject object) {
        HashMap<String, String> dataList = new HashMap<>();
        try {
            String distance = object.getJSONObject("distance").getString("text");
            dataList.put("distance", distance);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    private List<HashMap<String, String>> parseJsonArray(JSONArray jsonArray) {
        List<HashMap<String, String>> dataList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                HashMap<String, String> data = parseJsonObject((JSONObject) jsonArray.get(i));
                JSONArray photos = null;
                if (((JSONObject) jsonArray.get(i)).has("photos")) {
                    photos = ((JSONObject) jsonArray.get(i)).getJSONArray("photos");
                    if (getPhotoReference((JSONObject) photos.get(0)) != null) {
                        data.put("photo",
                                "https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&maxheight=300&photoreference=" +
                                        getPhotoReference((JSONObject) photos.get(0)) +
                                        "&key=AIzaSyBomRHM2cJo2o33ZULSbZHbisJs4JZQSKE");
                    }
                }
                else {
                    data.put("photo", null);
                }

                dataList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    private List<HashMap<String, String>> parseJsonArrayDist(JSONArray jsonArray) {
        List<HashMap<String, String>> dataList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                HashMap<String, String> data = parseJsonDist((JSONObject) jsonArray.get(i));
                dataList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    public List<HashMap<String, String>> parseResult(JSONObject object) {
        JSONArray jsonArray = null;
        try {
            jsonArray = object.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parseJsonArray(jsonArray);
    }

    public List<HashMap<String, String>> parseResultDist(JSONObject object) {
        JSONArray jsonArray = null;
        try {
            jsonArray = object.getJSONArray("rows");
            jsonArray = ((JSONObject) jsonArray.get(0)).getJSONArray("elements");;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parseJsonArrayDist(jsonArray);
    }
}

