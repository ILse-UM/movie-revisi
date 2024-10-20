package com.um.movie.controller;

import com.um.movie.MovieApplication;
import com.um.movie.util.FileUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Label;

import com.um.movie.model.Movie;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AvailableController {

    @FXML
    private TableView<Movie> tableView;

    @FXML
    private TableColumn<Movie, String> titleColumn;

    @FXML
    private TableColumn<Movie, String> genreColumn;

    @FXML
    private TableColumn<Movie, String> dateColumn;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField genreTextField;

    @FXML
    private TextField dateTextField;

    @FXML
    private ImageView image, admin;

    @FXML
    private Label titleLabel;

    @FXML
    private Spinner<Integer> normalQuantity;

    @FXML
    private Spinner<Integer> specialQuantity;

    @FXML
    private Label normalPrice;

    @FXML
    private Label specialPrice;

    @FXML
    private Label totalPrice;

    private ObservableList<Movie> movieList;

    private final double normalTicketPrice = 10.0;
    private final double specialTicketPrice = 15.0;

    @FXML
    public void initialize() {
        //set admin image
        admin.setImage(new Image(MovieApplication.class.getResourceAsStream("admin.png")));

        // Set value factory for normal quantity Spinner (e.g. range from 0 to 10)
        SpinnerValueFactory<Integer> normalFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0);
        normalQuantity.setValueFactory(normalFactory);

        // Set value factory for special quantity Spinner (e.g. range from 0 to 5)
        SpinnerValueFactory<Integer> specialFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0);
        specialQuantity.setValueFactory(specialFactory);

        // Initialize table columns with movie data properties
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        genreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenre()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getShowingDate().toString()));

        movieList = FXCollections.observableArrayList(FileUtil.loadMoviesFromFile());
        tableView.setItems(movieList);

        // Spinner configuration for ticket quantities
        normalQuantity.valueProperty().addListener((obs, oldValue, newValue) -> updateTotalPrice());
        specialQuantity.valueProperty().addListener((obs, oldValue, newValue) -> updateTotalPrice());

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> handleRowClick());

    }

    public void handleRowClick(){
        Movie selectedMovie = tableView.getSelectionModel().getSelectedItem();
        if(selectedMovie != null){
            // Update the text fields with selected movie data
            titleTextField.setText(selectedMovie.getTitle());
            genreTextField.setText(selectedMovie.getGenre());
            dateTextField.setText(selectedMovie.getShowingDate().toString());
        }
    }
    @FXML
    private void selectMovie() {
        // Get selected movie from the table
        Movie selectedMovie = tableView.getSelectionModel().getSelectedItem();

        if (selectedMovie != null) {
            // Update the image and label
            titleLabel.setText(selectedMovie.getTitle());
            image.setImage(new Image(selectedMovie.getImage()));  // Assuming a valid image path
        }
    }

    @FXML
    private void handleClear(ActionEvent event) {
        // Clear all fields
        titleTextField.clear();
        genreTextField.clear();
        dateTextField.clear();
        titleLabel.setText("");
        image.setImage(null);
        normalQuantity.getValueFactory().setValue(0);
        specialQuantity.getValueFactory().setValue(0);
        normalPrice.setText("$0");
        specialPrice.setText("$0");
        totalPrice.setText("$0.0");
    }

    @FXML
    public void handleBuy() {
        String ticketNum = generateTicketNumber(); // Buat metode untuk menghasilkan nomor tiket
        String title = tableView.getSelectionModel().getSelectedItem().getTitle(); // Ambil judul film dari tabel
        double total = Double.parseDouble(totalPrice.getText().substring(1)); // Ambil total dari label

        // Mengambil waktu saat ini
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Format tanggal
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss")); // Format waktu

        // Simpan data ke file
        FileUtil.saveTicketToFile(ticketNum, title, total, date, time);
    }

    private String generateTicketNumber() {
        return "TICK" + System.currentTimeMillis();
    }


    @FXML
    private void handleReceipt(ActionEvent event) {
        // This can be expanded to generate a receipt
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Receipt");
        alert.setHeaderText("Purchase Summary");
        alert.setContentText("Movie: " + titleTextField.getText() + "\n" +
                "Normal Tickets: " + normalQuantity.getValue() + "\n" +
                "Special Tickets: " + specialQuantity.getValue() + "\n" +
                "Total Price: " + totalPrice.getText());
        alert.showAndWait();
    }

    private void updateTotalPrice() {
        int normalCount = normalQuantity.getValue() != null ? normalQuantity.getValue() : 0;
        int specialCount = specialQuantity.getValue() != null ? specialQuantity.getValue() : 0;

        double total = (normalCount * normalTicketPrice) + (specialCount * specialTicketPrice);

        // Update label harga
        normalPrice.setText("$" + (normalCount * normalTicketPrice));
        specialPrice.setText("$" + (specialCount * specialTicketPrice));
        totalPrice.setText("$" + total);
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

    @FXML
    private void handleSignOut(ActionEvent event) {
        // Implementasi untuk sign out, misalnya kembali ke halaman login
        MovieApplication.switchScene((Node) event.getSource(), "login.fxml");
    }


}
