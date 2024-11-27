package com.example.stalleneindhoven2.controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebErrorEvent;
import javafx.scene.web.WebView;

import java.net.URL;

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
    }
}