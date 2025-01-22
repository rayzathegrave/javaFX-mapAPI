package com.example.stalleneindhoven2.model;

public class ScooterBerging extends Locatie {
    private String bergingNaam;

    // Getters and setters
    public String getBergingNaam() {
        return bergingNaam;
    }

    public void setBergingNaam(String bergingNaam) {
        this.bergingNaam = bergingNaam; // Consistent naming
    }

    // Default constructor
    public ScooterBerging() {
    }

    // Constructor initializing bergingNaam, longitude, and latitude
    public ScooterBerging(String bergingNaam, double longitude, double latitude) {
        super(longitude, latitude); // Call the superclass constructor
        this.bergingNaam = bergingNaam;
    }
}

