package com.um.movie.controller;

import com.um.movie.MovieApplication;
import com.um.movie.model.Movie;
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

public class ScreenController {

    @FXML
    private TextField search;

    @FXML
    private ImageView admin, titleImage;

    @FXML
    private Label titleLabel;

    @FXML
    private ComboBox<String> boxLabel;

    @FXML
    private TableView<Movie> tableView;

    @FXML
    private TableColumn<Movie, String> titleColumn, genreColumn, durationColumn, currentColumn;

    private ObservableList<Movie> movieList;

    @FXML
    public void initialize() {
        //set admin image
        admin.setImage(new Image(MovieApplication.class.getResourceAsStream("admin.png")));

        // Initialize table columns
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        genreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenre()));
        durationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDuration())));
        currentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCurrent()));

        // Load data from file
        movieList = FXCollections.observableArrayList(FileUtil.loadMoviesFromFile());
        tableView.setItems(movieList);

        // Set listener for table view selection
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                displaySelectedMovie(newSelection);
            }
        });
    }

    private void displaySelectedMovie(Movie movie) {
        titleLabel.setText(movie.getTitle());
        titleImage.setImage(new Image(movie.getImage()));
        boxLabel.setValue(movie.getCurrent()); // Set ComboBox to the current status of the movie
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        Movie selectedMovie = tableView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            selectedMovie.setCurrent(boxLabel.getValue()); // Update the current property based on ComboBox value

            tableView.refresh();
            FileUtil.deleteMovieFromFile(selectedMovie.getTitle());
            FileUtil.saveMoviesToFile(movieList);
        } else {
            showAlert("Error", "No movie selected.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleClear(ActionEvent event) {
        clearFields();
    }

    @FXML
    public void handleSearch() {
        String searchText = search.getText().toLowerCase();
        ObservableList<Movie> filteredList = FXCollections.observableArrayList();

        for (Movie movie : movieList) {
            if (movie.getTitle().toLowerCase().contains(searchText)) {
                filteredList.add(movie);
            }
        }

        if (searchText.isEmpty()) {
            tableView.setItems(movieList);
        } else {
            tableView.setItems(filteredList);
        }
    }

    private void clearFields() {
        titleLabel.setText("");
        titleImage.setImage(null);
        boxLabel.setValue(null); // Clear ComboBox selection
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
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
