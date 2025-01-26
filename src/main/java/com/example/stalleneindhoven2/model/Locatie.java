package com.example.stalleneindhoven2.model;

public abstract class Locatie {

    private double longitude;
    private double latitude;

    // Constructor for longitude and latitude
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

    // Abstract method to be implemented by subclasses
    public abstract String getDescription();
}