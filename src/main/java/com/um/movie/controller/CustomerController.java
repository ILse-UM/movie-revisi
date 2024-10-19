package com.um.movie.controller;

import com.um.movie.MovieApplication;
import com.um.movie.model.Ticket;
import com.um.movie.util.FileUtil;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

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

        // Menghubungkan data dari Ticket ke dalam tabel
        ticketNumColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTicketNum()));
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        totalColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotal()).asObject());
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTime()));

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> handleRowClick());
    }

    public boolean validateInput() {
        try {
            Double.parseDouble(totalTextField.getText());
            return true;
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Total harus berupa angka.");
            alert.show();
            return false;
        }
    }


    @FXML
    public void handleRowClick() {
        Ticket selectedTicket = tableView.getSelectionModel().getSelectedItem();
        if (selectedTicket != null) {
            // Mengisi TextField dengan data dari tiket yang dipilih
            ticketNumTextField.setText(selectedTicket.getTicketNum());
            titleTextField.setText(selectedTicket.getTitle());
            totalTextField.setText(String.valueOf(selectedTicket.getTotal()));
            dateTextField.setText(selectedTicket.getDate());
            timeTextField.setText(selectedTicket.getTime());
        }
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
            if (validateInput()) {
                // Hapus tiket dari ticketList
                ticketList.remove(selectedTicket);

                // Hapus tiket dari file berdasarkan ticketNum
                FileUtil.deleteTicketFromFile(selectedTicket.getTicketNum());

                // Clear fields setelah delete
                clearFields();

                // Refresh tampilan tabel jika perlu
                tableView.refresh();
            }
        }
    }



    @FXML
    public void handleSearch() {
        String searchText = search.getText().toLowerCase();
        if (searchText.isEmpty()) {
            tableView.setItems(ticketList);  // Reset to original data
            return;
        }

        ObservableList<Ticket> filteredList = FXCollections.observableArrayList();

        for (Ticket ticket : ticketList) {
            if (ticket.getTitle().toLowerCase().contains(searchText)) {
                filteredList.add(ticket);
            }
        }

        tableView.setItems(filteredList);
    }



    // method dibawah ini merupakan penerapan dari tombol navigasi sebelah kiri
    @FXML
    private void handleDashBoard(ActionEvent event) {
        // Implementasi untuk dashboard button action
        MovieApplication.switchScene((Node) event.getSource(), "dashboard.fxml");
    }

    @FXML
    private void handleMovies(ActionEvent event) {
        // Implementasi untuk movies button action
        MovieApplication.switchScene((Node) event.getSource(), "add.fxml");
    }

    @FXML
    private void handleAvailable(ActionEvent event) {
        // Implementasi untuk available movies button action
        MovieApplication.switchScene((Node) event.getSource(), "availableMovie.fxml");
    }

    @FXML
    private void handleScreen(ActionEvent event) {
        // Implementasi untuk edit screening button action
        MovieApplication.switchScene((Node) event.getSource(), "editscreening.fxml");
    }

    @FXML
    private void handleCustomer(ActionEvent event) {
        // Implementasi untuk customers button action
        MovieApplication.switchScene((Node) event.getSource(), "customers.fxml");
    }
}
