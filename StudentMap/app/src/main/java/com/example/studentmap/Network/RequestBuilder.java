package com.example.studentmap.Network;

import android.util.Log;

import com.example.studentmap.Network.Entity.Place;
import com.example.studentmap.Network.Entity.Post;
import com.example.studentmap.Network.Entity.User;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestBuilder {


    public static RequestBody addBody(User user) {
        String json = "";
        JSONObject user_json = new JSONObject();
        try {
            user_json.put("login", user.getLogin());
            user_json.put("password", user.getPassword());
            user_json.put("name", user.getName());
            user_json.put("surname", user.getSurname());
            user_json.put("age", user.getAge());
            user_json.put("gender", user.getGender());
            user_json.put("img",user.getImg());
            json = user_json.toString();
            Log.d("User_For_PostReq", json);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        return body;

    }

    public static RequestBody addBody(Post post) {
        String json = "";
        JSONObject user_json = new JSONObject();
        try {
            user_json.put("name", post.getName());
            user_json.put("login", post.getLogin());
            user_json.put("date", post.getDate());
            user_json.put("location", post.getLocation());
            user_json.put("text", post.getText());
            user_json.put("img",  post.getImg());
            user_json.put("rating", post.getRating());
            json = user_json.toString();
            Log.d("Post_For_PostReq", json);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        return body;

    }

    public static RequestBody addBody(Place place) {
        String json = "";
        JSONObject user_json = new JSONObject();
        try {
            user_json.put("name", place.getName());
            user_json.put("location", place.getLocation());
            user_json.put("img", place.getImg());

            json = user_json.toString();
            Log.d("Post_For_PostReq", json);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        return body;

    }

    public static HttpUrl buildURL(String pathSegment1) {
        return new HttpUrl.Builder()
                .scheme("https") //http
                .host("rest-api-for-student-map.herokuapp.com")
                .addPathSegment(pathSegment1)
                .build();
    }
        public static HttpUrl buildURL(String pathSegment1, String pathSegment2) {
            return new HttpUrl.Builder()
                    .scheme("https") //http
                    .host("rest-api-for-student-map.herokuapp.com")
                    .addPathSegment(pathSegment1)
                    .addPathSegment(pathSegment2)
                    .build();

    }

    public static HttpUrl buildURL(String pathSegment1, String pathSegment2, String pathSegment3) {
        return new HttpUrl.Builder()
                .scheme("https") //http
                .host("rest-api-for-student-map.herokuapp.com")
                .addPathSegment(pathSegment1)
                .addPathSegment(pathSegment2)
                .addPathSegment(pathSegment3)
                .build();

    }
}
