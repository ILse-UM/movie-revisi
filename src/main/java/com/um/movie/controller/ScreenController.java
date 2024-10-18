package com.um.movie.controller;

import com.um.movie.model.Movie;
import com.um.movie.util.FileUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDate;

public class ScreenController {

    @FXML
    private TextField movieTitle, genre, duration, publishedDate, search;

    @FXML
    private ImageView image;

    @FXML
    private Button importButton, insertButton, updateButton, deleteButton, clearButton;

    @FXML
    private ComboBox<String> boxLabel;

    @FXML
    private TableView<Movie> tableView;

    @FXML
    private TableColumn<Movie, String> titleColumn, genreColumn, durationColumn, currentColumn;

    private ObservableList<Movie> movieList;

    @FXML
    public void initialize() {
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

        boxLabel.getItems().addAll("Showing", "End SHowing");
    }

    private void displaySelectedMovie(Movie movie) {
        movieTitle.setText(movie.getTitle());
        genre.setText(movie.getGenre());
        duration.setText(String.valueOf(movie.getDuration()));
        publishedDate.setText(movie.getShowingDate().toString());
        image.setImage(new Image(movie.getImage()));
        boxLabel.setValue(movie.getCurrent()); // Set ComboBox to the current status of the movie
    }

    @FXML
    private void handleBox(ActionEvent event) {
        String selectedValue = boxLabel.getValue();
        if (selectedValue != null) {
            Movie selectedMovie = tableView.getSelectionModel().getSelectedItem();
            if (selectedMovie != null) {
                selectedMovie.setCurrent(selectedValue);
                tableView.refresh(); // Refresh the table to show the updated current status
                FileUtil.saveMoviesToFile(movieList); // Save updated movie list to file
            }
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        Movie selectedMovie = tableView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            selectedMovie.setTitle(movieTitle.getText());
            selectedMovie.setGenre(genre.getText());
            selectedMovie.setDuration(Integer.parseInt(duration.getText()));
            selectedMovie.setShowingDate(LocalDate.parse(publishedDate.getText()));
            selectedMovie.setImage(image.getImage().getUrl());
            selectedMovie.setCurrent(boxLabel.getValue()); // Update the current property based on ComboBox value

            tableView.refresh();
            FileUtil.saveMoviesToFile(movieList);
            clearFields();
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

        tableView.setItems(filteredList);
    }

    private void clearFields() {
        movieTitle.clear();
        genre.clear();
        duration.clear();
        publishedDate.clear();
        image.setImage(null);
        boxLabel.setValue(null); // Clear ComboBox selection
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
