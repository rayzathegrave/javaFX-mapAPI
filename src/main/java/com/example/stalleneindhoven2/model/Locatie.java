package com.example.stalleneindhoven2.model;

public class Locatie {

    private double longitude;
    private double latitude;

    // Constructor with stallingNaam, initializes longitude and latitude
    public Locatie(String stallingNaam, double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    // Constructor initializing only longitude and latitude
    public Locatie(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    // Default constructor
    public Locatie() {
    }

    // Getters and setters
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
}