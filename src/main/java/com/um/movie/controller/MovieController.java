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

public class MovieController {

    @FXML
    private TextField movieTitle, genre, duration, publishedDate, search;

    @FXML
    private ImageView image;

    @FXML
    private Button importButton, insertButton, updateButton, deleteButton, clearButton;

    @FXML
    private TableView<Movie> tableView;

    @FXML
    private TableColumn<Movie, String> titleColumn, genreColumn, durationColumn, publishedDateColumn;

    private ObservableList<Movie> movieList;

    @FXML
    public void initialize() {
        // Initialize table columns
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        genreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenre()));
        durationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDuration())));
        publishedDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getShowingDate().toString()));

        // Load data from file
        movieList = FXCollections.observableArrayList(FileUtil.loadMoviesFromFile());
        tableView.setItems(movieList);
    }

    @FXML
    private void handleInsert(ActionEvent event) {
        try {
            Movie movie = new Movie(
                    movieTitle.getText(),
                    genre.getText(),
                    Integer.parseInt(duration.getText()),
                    LocalDate.parse(publishedDate.getText()),
                    image.getImage().getUrl()
            );
            movieList.add(movie);
            FileUtil.saveMoviesToFile(movieList);
            clearFields();
        } catch (Exception e) {
            showAlert("Error", "Invalid input. Please check your data.", Alert.AlertType.ERROR);
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

            tableView.refresh();
            FileUtil.saveMoviesToFile(movieList);
            clearFields();
        } else {
            showAlert("Error", "No movie selected.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        Movie selectedMovie = tableView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            movieList.remove(selectedMovie);
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
    private void handleImport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            image.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    public void handleSearch(){
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
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
