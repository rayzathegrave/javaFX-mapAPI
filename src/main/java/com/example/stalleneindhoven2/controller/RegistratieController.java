package com.example.stalleneindhoven2.controller;

import com.example.stalleneindhoven2.model.Registratie;
import com.example.stalleneindhoven2.util.DBCPDataSource;
import com.example.stalleneindhoven2.view.RegistratieView;

public class RegistratieController {

    private final RegistratieView view;
    private Registratie registratie;

    public RegistratieController(RegistratieView view) {
        this.view = view;
        this.registratie = new Registratie("", null); // Lege registratie als startpunt
        initialize();
    }

    private void initialize() {
        view.getNaamVeld().setOnAction(event -> handleNaamInvoer());
        view.getGeboortedatumPicker().setOnAction(event -> {
            handleGeboortedatumInvoer();
            saveToDatabase();
        });
    }

    private void handleNaamInvoer() {
        String naam = view.getNaamVeld().getText();
        registratie.setNaam(naam);
        System.out.println("Naam ingevoerd: " + registratie.getNaam());
    }

    private void handleGeboortedatumInvoer() {
        if (view.getGeboortedatumPicker().getValue() != null) {
            registratie.setGeboortedatum(view.getGeboortedatumPicker().getValue());
            System.out.println("Geboortedatum geselecteerd: " + registratie.getGeboortedatum());
        }
    }

    private void saveToDatabase() {
        System.out.println("Opslaan van gegevens in database...");

        String naam = view.getNaamVeld().getText();
        java.time.LocalDate geboortedatum = view.getGeboortedatumPicker().getValue();

        if (naam.isEmpty() || geboortedatum == null) {
            System.out.println("Vul alstublieft zowel naam als geboortedatum in.");
            return;
        }

        try {
            DBCPDataSource.insertRegistratie(naam, geboortedatum);
            System.out.println("Invoer succesvol opgeslagen: " + naam + ", " + geboortedatum);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fout bij het opslaan van de gegevens.");
        }
    }



}
