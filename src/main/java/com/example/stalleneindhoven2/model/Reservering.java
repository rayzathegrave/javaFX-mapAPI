package com.example.stalleneindhoven2.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Reservering {

    private final StringProperty naam;
    private final StringProperty geboortedatum;
    private final StringProperty locatie;
    private final StringProperty typeReservering;

    public Reservering(String naam, LocalDate geboortedatum, String locatie, String typeReservering) {
        this.naam = new SimpleStringProperty(naam);
        this.geboortedatum = new SimpleStringProperty(geboortedatum != null ? geboortedatum.toString() : "");
        this.locatie = new SimpleStringProperty(locatie);
        this.typeReservering = new SimpleStringProperty(typeReservering);
    }

    // Property Getters
    public StringProperty naamProperty() {
        return naam;
    }

    public StringProperty geboortedatumProperty() {
        return geboortedatum;
    }

    public StringProperty locatieProperty() {
        return locatie;
    }

    public StringProperty typeReserveringProperty() {
        return typeReservering;
    }

    // String Getters
    public String getNaam() {
        return naam.get();
    }

    public String getGeboortedatum() {
        return geboortedatum.get();
    }

    public String getLocatie() {
        return locatie.get();
    }

    public String getTypeReservering() {
        return typeReservering.get();
    }

    // String Setters
    public void setNaam(String naam) {
        this.naam.set(naam);
    }

    public void setGeboortedatum(String geboortedatum) {
        this.geboortedatum.set(geboortedatum);
    }

    public void setLocatie(String locatie) {
        this.locatie.set(locatie);
    }

    public void setTypeReservering(String typeReservering) {
        this.typeReservering.set(typeReservering);
    }
}
