package com.example.stalleneindhoven2.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;

public class Reservering {
    private final StringProperty naam;
    private final ObjectProperty<LocalDate> geboortedatum;  // Changed to ObjectProperty<LocalDate>
    private final StringProperty locatie;
    private final StringProperty typeReservering;

    public Reservering(String naam, LocalDate geboortedatum, String locatie, String typeReservering) {
        this.naam = new SimpleStringProperty(naam);
        this.geboortedatum = new SimpleObjectProperty<>(geboortedatum);  // Using SimpleObjectProperty for LocalDate
        this.locatie = new SimpleStringProperty(locatie);
        this.typeReservering = new SimpleStringProperty(typeReservering);
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
}
