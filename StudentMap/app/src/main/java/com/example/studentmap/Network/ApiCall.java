package com.example.workwithapi;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


    public class ApiCall {


        public static String GET(OkHttpClient client, HttpUrl url) throws IOException {
            Log.d("ApiCallGET", "IN");
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            Log.d("AfterReq", "true");
            Log.d("Response cod",Integer.toString(response.code()) );
            String answer = response.body().string();
            response.close();
            switch (response.code()){

                case 200:{
                    Log.d("answFromApiCall",answer);
                    return answer;
                }
                case 404:{
                    Log.d("answFromApiCall","NOT_FOUND" );
                    return "NOT_FOUND";
                }
                case 500: {
                    Log.d("answFromApiCall","SERVER_ERROR");
                    return "SERVER_ERROR";
                }

                default: {
                    Log.d("answFromApiCall", "ERROR");
                    return "ERROR";
                }
            }

        }

        //POST network request
        public static String POST(OkHttpClient client, HttpUrl url, RequestBody body) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            String answer = response.body().string();
            Log.d("ApiCall_Post", answer);

            switch (response.code()){

                case 200: return answer;
                case 201 : return answer;
                case 404: return "NOT_FOUND";
                case 500: return "SERVER_ERROR";
                default:return "ERROR";
            }


        }
    }

