package com.example.stalleneindhoven2.model;

public class Fietsenstalling extends Locatie {
    private String naam;

    // Getters and setters
    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Fietsenstalling() {
    }

    // Constructor
    public Fietsenstalling(String naam, double longitude, double latitude) {
        super(longitude, latitude); // Call the superclass constructor
        this.naam = naam;
    }

    @Override
    public String getDescription() {
        return "Fietsenstalling: " + naam;
    }
}