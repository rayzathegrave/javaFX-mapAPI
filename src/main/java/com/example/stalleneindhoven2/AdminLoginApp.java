package com.example.stalleneindhoven2;

import com.example.stalleneindhoven2.controller.ReserveringController;
import com.example.stalleneindhoven2.view.ReserveringView;
import javafx.application.Application;
import javafx.stage.Stage;

public class AdminLoginApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        ReserveringView reserveringView = new ReserveringView();
        ReserveringController controller = new ReserveringController(reserveringView);
        controller.showAdminLogin(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}