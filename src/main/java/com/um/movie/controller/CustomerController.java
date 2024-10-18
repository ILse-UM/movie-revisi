package com.um.movie.controller;

import com.um.movie.model.Ticket;
import com.um.movie.util.FileUtil;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomerController {
    @FXML
    private TableView<Ticket> tableView;
    @FXML
    private TableColumn<Ticket, String> ticketNumColumn;
    @FXML
    private TableColumn<Ticket, String> titleColumn;
    @FXML
    private TableColumn<Ticket, Double> totalColumn;
    @FXML
    private TableColumn<Ticket, String> dateColumn;
    @FXML
    private TableColumn<Ticket, String> timeColumn;

    @FXML
    private TextField ticketNumTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField totalTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField timeTextField;
    @FXML
    private TextField search;

    private ObservableList<Ticket> ticketList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // pick up data from file that already created before
        ticketList.setAll(FileUtil.loadTicketsFromFile());
        tableView.setItems(ticketList);

    }

    @FXML
    public void handleClear() {
        clearFields();
    }

    private void clearFields() {
        ticketNumTextField.clear();
        titleTextField.clear();
        totalTextField.clear();
        dateTextField.clear();
        timeTextField.clear();
    }

    @FXML
    public void handleDelete() {
        Ticket selectedTicket = tableView.getSelectionModel().getSelectedItem();
        if (selectedTicket != null) {
            ticketList.remove(selectedTicket);
            // Optionally, remove from file or handle further
        }
    }

    @FXML
    public void handleSearch() {
        String searchText = search.getText().toLowerCase();
        ObservableList<Ticket> filteredList = FXCollections.observableArrayList();

        for (Ticket ticket : ticketList) {
            if (ticket.getTitle().toLowerCase().contains(searchText)) {
                filteredList.add(ticket);
            }
        }

        tableView.setItems(filteredList);
    }
}
