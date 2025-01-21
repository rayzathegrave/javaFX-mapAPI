package com.example.stalleneindhoven2.model;

public class ScooterBerging extends Locatie {
    private String bergingNaam;

    // Getters and setters
    public String getStallingNaam() {
        return bergingNaam;
    }

    public void setStallingNaam(String stallingNaam) {
        this.bergingNaam = stallingNaam;
    }

    public ScooterBerging() {
    }

    // Constructor
    public ScooterBerging(String stallingNaam, double longitude, double latitude) {
        super(longitude, latitude); // Call the superclass constructor
        this.bergingNaam = stallingNaam;
    }
}
