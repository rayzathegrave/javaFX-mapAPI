package com.example.stalleneindhoven2.controller;

import com.example.stalleneindhoven2.model.Fietsenstalling;
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
            System.out.println("Loading URL: " + url.toExternalForm());
        } else {
            System.err.println("Resource not found");
        }

        fetchDataAndDisplay();
    }

    private void fetchDataAndDisplay() {
        try {
            String jsonData = DataFetcher.fetchData("https://data.eindhoven.nl/api/explore/v2.1/catalog/datasets/fietsenstallingen/records?");
            List<Fietsenstalling> fietsenstallingen = DataParser.parseData(jsonData);

            for (Fietsenstalling fietsenstalling : fietsenstallingen) {
                String script = String.format("addPin('%f' , '%f', '%s');", fietsenstalling.getLongitude(), fietsenstalling.getLatitude(), fietsenstalling.getNaam());
                webEngine.executeScript(script);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



