package com.example.stalleneindhoven2.controller;

import com.example.stalleneindhoven2.model.EbikeStalling;
import com.example.stalleneindhoven2.model.Fietsenstalling;
import com.example.stalleneindhoven2.model.ScooterBerging;
import com.example.stalleneindhoven2.util.DBCPDataSource;
import com.example.stalleneindhoven2.util.DataFetcher;
import com.example.stalleneindhoven2.util.DataParser;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebErrorEvent;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.List;

public class MapController {
    @FXML
    private WebView mapWebView;
    private WebEngine webEngine;

    @FXML
    public void initialize() {
        webEngine = mapWebView.getEngine();
        webEngine.setJavaScriptEnabled(true); // Enable JavaScript

        // Add a listener to capture JavaScript errors
        webEngine.setOnError((WebErrorEvent event) -> {
            System.err.println("JavaScript Error: " + event.getMessage());
        });

        // Ensure the resource path is correct
        URL url = getClass().getResource("/com/example/stalleneindhoven2/view/map.html");
        if (url != null) {
            webEngine.load(url.toExternalForm());
           // System.out.println("Loading URL: " + url.toExternalForm());
        } else {
            System.err.println("Resource not found");
        }


    }

    @FXML
    private void loadmap() {
        fetchDataAndDisplay();
    }


    private void fetchDataAndDisplay() {
        try {
            // Fetch data from the API
            String jsonData = DataFetcher.fetchData("https://data.eindhoven.nl/api/explore/v2.1/catalog/datasets/fietsenstallingen/records?");
            List<Fietsenstalling> fietsenstallingen = DataParser.parseData(jsonData);

            // Fetch ebikestallingen from the database
            List<EbikeStalling> ebikestallingen = DBCPDataSource.fetchEbikestallingen();

            // Convert EbikeStalling to Fietsenstalling and add to the list
            for (EbikeStalling ebikeStalling : ebikestallingen) {
                fietsenstallingen.add(new Fietsenstalling(ebikeStalling.getStallingNaam(), ebikeStalling.getLongitude(), ebikeStalling.getLatitude()));
            }

            // Fetch scooterbergings from the database
            List<ScooterBerging> scooterBergingen = DBCPDataSource.fetchScooterBergingen();

            // Convert ScooterBerging to Fietsenstalling and add to the list
            for (ScooterBerging scooterBerging : scooterBergingen) {
                fietsenstallingen.add(new Fietsenstalling(scooterBerging.getBergingNaam(), scooterBerging.getLongitude(), scooterBerging.getLatitude()));
            }

            // Display all stalling data on the map
            for (Fietsenstalling fietsenstalling : fietsenstallingen) {
                String script = String.format("addPin('%s', '%s', '%s');", fietsenstalling.getLongitude(), fietsenstalling.getLatitude(), fietsenstalling.getNaam());
                webEngine.executeScript(script);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



