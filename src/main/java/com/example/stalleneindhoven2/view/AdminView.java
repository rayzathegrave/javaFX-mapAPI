package com.example.stalleneindhoven2.view;

import com.example.stalleneindhoven2.model.Reservering;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AdminView extends VBox {

    private final TableView<Reservering> tableView;
    private final Button updateButton;
    private final Button deleteButton;

    public AdminView() {
        tableView = new TableView<>();
        tableView.setEditable(true); // Make the table view editable

        // Set up the "Naam" column
        TableColumn<Reservering, String> naamColumn = new TableColumn<>("Naam");
        naamColumn.setCellValueFactory(cellData -> cellData.getValue().naamProperty());
        naamColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        naamColumn.setOnEditCommit(event -> {
            Reservering reservering = event.getRowValue();
            reservering.setNaam(event.getNewValue());
        });

        // Set up the "Geboortedatum" column with LocalDate
        TableColumn<Reservering, String> geboortedatumColumn = new TableColumn<>("Geboortedatum");
        geboortedatumColumn.setCellValueFactory(cellData -> {
            LocalDate geboortedatum = cellData.getValue().getGeboortedatum();
            return new SimpleStringProperty(geboortedatum != null ? geboortedatum.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "");
        });

        geboortedatumColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        geboortedatumColumn.setOnEditCommit(event -> {
            Reservering reservering = event.getRowValue();
            String newDateString = event.getNewValue();
            try {
                LocalDate newDate = LocalDate.parse(newDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                reservering.setGeboortedatum(newDate);  // Set the new LocalDate
            } catch (Exception e) {
                // Handle invalid date format (you can show an error message here)
                System.out.println("Invalid date format entered: " + newDateString);
            }
        });

        // Set up the "Locatie" column
        TableColumn<Reservering, String> locatieColumn = new TableColumn<>("Locatie");
        locatieColumn.setCellValueFactory(cellData -> cellData.getValue().locatieProperty());
        locatieColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        locatieColumn.setOnEditCommit(event -> {
            Reservering reservering = event.getRowValue();
            reservering.setLocatie(event.getNewValue());
        });

        // Set up the "Type Reservering" column
        TableColumn<Reservering, String> typeReserveringColumn = new TableColumn<>("Type Reservering");
        typeReserveringColumn.setCellValueFactory(cellData -> cellData.getValue().typeReserveringProperty());
        typeReserveringColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        typeReserveringColumn.setOnEditCommit(event -> {
            Reservering reservering = event.getRowValue();
            reservering.setTypeReservering(event.getNewValue());
        });

        // Add the columns to the table
        tableView.getColumns().addAll(naamColumn, geboortedatumColumn, locatieColumn, typeReserveringColumn);

        // Set up the buttons
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");

        // Add the table and buttons to the view
        this.getChildren().addAll(tableView, updateButton, deleteButton);

        // Set up event handler for the "Update" button
        updateButton.setOnAction(event -> {
            // Get the selected reservation
            Reservering selectedReservering = tableView.getSelectionModel().getSelectedItem();
            if (selectedReservering != null) {
                // Call the update logic in the controller (assuming it's set elsewhere)
                handleUpdate(selectedReservering);
            } else {
                showAlert("Fout", "Selecteer een reservering om bij te werken.");
            }
        });

        // Set up event handler for the "Delete" button
        deleteButton.setOnAction(event -> {
            // Get the selected reservation
            Reservering selectedReservering = tableView.getSelectionModel().getSelectedItem();
            if (selectedReservering != null) {
                // Call the delete logic in the controller (assuming it's set elsewhere)
                handleDelete(selectedReservering);
            } else {
                showAlert("Fout", "Selecteer een reservering om te verwijderen.");
            }
        });
    }

    public TableView<Reservering> getTableView() {
        return tableView;
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setTableData(ObservableList<Reservering> data) {
        tableView.setItems(data);
    }

    private void handleUpdate(Reservering reservering) {
        // Call the update logic in the controller (assuming it's set elsewhere)
        // Ensure that handleUpdate() is defined in the controller
        // You can add your update code here if needed
        showAlert("Update", "Reservering is updated: " + reservering.getNaam());
    }

    private void handleDelete(Reservering reservering) {
        // Call the delete logic in the controller (assuming it's set elsewhere)
        // You can add your delete code here if needed
        showAlert("Delete", "Reservering is deleted: " + reservering.getNaam());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
