module com.example.stalleneindhoven2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires json.simple;

    opens com.example.stalleneindhoven2.controller to javafx.fxml;
    exports com.example.stalleneindhoven2;

}
