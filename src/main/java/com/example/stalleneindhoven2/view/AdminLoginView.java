package com.example.stalleneindhoven2.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;

public class AdminLoginView extends GridPane {

    private PasswordField adminCodeField;
    private Button loginButton;

    public AdminLoginView() {
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new javafx.geometry.Insets(10));

        // Admin code field
        Label adminCodeLabel = new Label("Admin Code:");
        adminCodeField = new PasswordField();

        // Login button
        loginButton = new Button("Login");

        // Add components to the GridPane
        this.add(adminCodeLabel, 0, 0);
        this.add(adminCodeField, 1, 0);
        this.add(loginButton, 1, 1);
    }

    public PasswordField getAdminCodeField() {
        return adminCodeField;
    }

    public Button getLoginButton() {
        return loginButton;
    }
}