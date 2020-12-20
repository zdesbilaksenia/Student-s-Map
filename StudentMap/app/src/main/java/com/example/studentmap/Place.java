package com.example.studentmap;


import java.io.Serializable;

public class Place implements Serializable {
    private double longitude;
    private double latitude;
    private String name;
    private double rating;
    private String icon;
    private String vicinity;
    private String distance;
    private String photo;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() { return rating; }

    public void setRating(double rating) { this.rating = rating; }

    public String getIcon() { return icon; }

    public void setIcon(String icon) { this.icon = icon; }

    public String getVicinity() { return vicinity; }

    public void setVicinity(String vicinity) { this.vicinity = vicinity; }

    public String getDistance() { return distance; }

    public void setDistance(String distance) { this.distance = distance; }

    public String getPhoto() { return photo; }

    public void setPhoto(String photo) { this.photo = photo; }
}