package com.example.stalleneindhoven2;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/stalleneindhoven2/view/map-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Stallen Eindhoven Map");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}