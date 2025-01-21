package com.example.stalleneindhoven2.model;

public class EbikeStalling extends Locatie {
    private String stallingNaam;

    // Constructor
    public EbikeStalling(String stallingNaam, double longitude, double latitude) {
        super(longitude, latitude); // Correct call to superclass constructor
        this.stallingNaam = stallingNaam;
    }

    // Default constructor
    public EbikeStalling() {
    }

    // Getters and setters
    public String getStallingNaam() {
        return stallingNaam;
    }

    public void setStallingNaam(String stallingNaam) {
        this.stallingNaam = stallingNaam;
    }
}
