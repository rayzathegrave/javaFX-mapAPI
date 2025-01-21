package com.example.stalleneindhoven2;

import com.example.stalleneindhoven2.controller.RegistratieController;
import com.example.stalleneindhoven2.view.RegistratieView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegistratieApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        RegistratieView view = new RegistratieView();
        new RegistratieController(view);

        Scene scene = new Scene(view, 300, 200);
        primaryStage.setTitle("Registratie");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
