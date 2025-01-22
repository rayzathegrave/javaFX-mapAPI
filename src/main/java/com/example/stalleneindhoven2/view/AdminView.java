package com.example.stalleneindhoven2.view;

import com.example.stalleneindhoven2.model.Reservering;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class AdminView extends VBox {

    private final TableView<Reservering> tableView;
    private final Button updateButton;
    private final Button deleteButton;

    public AdminView() {
        tableView = new TableView<>();

        TableColumn<Reservering, String> naamColumn = new TableColumn<>("Naam");
        naamColumn.setCellValueFactory(cellData -> cellData.getValue().naamProperty());

        TableColumn<Reservering, String> geboortedatumColumn = new TableColumn<>("Geboortedatum");
        geboortedatumColumn.setCellValueFactory(cellData -> cellData.getValue().geboortedatumProperty());

        TableColumn<Reservering, String> locatieColumn = new TableColumn<>("Locatie");
        locatieColumn.setCellValueFactory(cellData -> cellData.getValue().locatieProperty());

        TableColumn<Reservering, String> typeReserveringColumn = new TableColumn<>("Type Reservering");
        typeReserveringColumn.setCellValueFactory(cellData -> cellData.getValue().typeReserveringProperty());

        tableView.getColumns().addAll(naamColumn, geboortedatumColumn, locatieColumn, typeReserveringColumn);

        updateButton = new Button("Update");
        deleteButton = new Button("Delete");

        this.getChildren().addAll(tableView, updateButton, deleteButton);
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
}
