package com.example.studentmap;

import java.util.HashMap;
import java.util.List;

public interface ResponseCallback {
    void response(List<Place> placeList);
    void responseRoute(List<List<HashMap<String,String>>> route);
}
