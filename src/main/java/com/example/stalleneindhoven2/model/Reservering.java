package com.example.stalleneindhoven2.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;

public class Reservering {
    private final StringProperty naam;
    private final ObjectProperty<LocalDate> geboortedatum;
    private final StringProperty locatie;
    private final StringProperty typeReservering;

    // Original values
    private final String originalNaam;
    private final LocalDate originalGeboortedatum;
    private final String originalLocatie;
    private final String originalTypeReservering;

    public Reservering(String naam, LocalDate geboortedatum, String locatie, String typeReservering) {
        this.naam = new SimpleStringProperty(naam);
        this.geboortedatum = new SimpleObjectProperty<>(geboortedatum);
        this.locatie = new SimpleStringProperty(locatie);
        this.typeReservering = new SimpleStringProperty(typeReservering);

        // Initialize original values
        this.originalNaam = naam;
        this.originalGeboortedatum = geboortedatum;
        this.originalLocatie = locatie;
        this.originalTypeReservering = typeReservering;
    }

    public StringProperty naamProperty() {
        return naam;
    }

    public String getNaam() {
        return naam.get();
    }

    public void setNaam(String naam) {
        this.naam.set(naam);
    }

    public ObjectProperty<LocalDate> geboortedatumProperty() {
        return geboortedatum;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum.get();
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum.set(geboortedatum);
    }

    public StringProperty locatieProperty() {
        return locatie;
    }

    public String getLocatie() {
        return locatie.get();
    }

    public void setLocatie(String locatie) {
        this.locatie.set(locatie);
    }

    public StringProperty typeReserveringProperty() {
        return typeReservering;
    }

    public String getTypeReservering() {
        return typeReservering.get();
    }

    public void setTypeReservering(String typeReservering) {
        this.typeReservering.set(typeReservering);
    }

    // Getters for original values
    public String getOriginalNaam() {
        return originalNaam;
    }

    public LocalDate getOriginalGeboortedatum() {
        return originalGeboortedatum;
    }

    public String getOriginalLocatie() {
        return originalLocatie;
    }

    public String getOriginalTypeReservering() {
        return originalTypeReservering;
    }
}