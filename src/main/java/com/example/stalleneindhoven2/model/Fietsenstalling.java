package com.example.stalleneindhoven2.model;

public class Fietsenstalling {
    private String name;
    private double latitude;
    private double longitude;

    public Fietsenstalling(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
