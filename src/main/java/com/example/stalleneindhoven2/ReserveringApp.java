package com.example.stalleneindhoven2;

import com.example.stalleneindhoven2.controller.ReserveringController;
import com.example.stalleneindhoven2.view.ReserveringView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class ReserveringApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        ReserveringView view = new ReserveringView();
        new ReserveringController(view);

        Scene scene = new Scene(view, 400, 300);
        primaryStage.setTitle("Reservering");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
