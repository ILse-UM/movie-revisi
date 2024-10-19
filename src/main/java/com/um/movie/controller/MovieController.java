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
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDate;

public class MovieController {

    @FXML
    private TextField movieTitle, genre, duration, publishedDate, search;

    @FXML
    private ImageView admin, titleImage;

    @FXML
    private Button importButton, insertButton, updateButton, deleteButton, clearButton;

    @FXML
    private TableView<Movie> tableView;

    @FXML
    private TableColumn<Movie, String> titleColumn, genreColumn, durationColumn, publishedDateColumn;

    private ObservableList<Movie> movieList;

    @FXML
    public void initialize() {
        //set admin image
        admin.setImage(new Image(MovieApplication.class.getResourceAsStream("admin.png")));

        // Initialize table columns
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        genreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenre()));
        durationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDuration())));
        publishedDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getShowingDate().toString()));

        // Load data from file
        movieList = FXCollections.observableArrayList(FileUtil.loadMoviesFromFile());
        tableView.setItems(movieList);

        // Listener for row selection
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> handleRowClick());
    }

    @FXML
    public void handleRowClick() {
        Movie selectedMovie = tableView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            // Fill TextFields with the selected movie's data
            movieTitle.setText(selectedMovie.getTitle());
            genre.setText(selectedMovie.getGenre());
            duration.setText(String.valueOf(selectedMovie.getDuration()));
            publishedDate.setText(selectedMovie.getShowingDate().toString());
            titleImage.setImage(new Image(selectedMovie.getImage()));
        }
    }

    @FXML
    private void handleInsert(ActionEvent event) {
        try {
            Movie movie = new Movie(
                    movieTitle.getText(),
                    genre.getText(),
                    Integer.parseInt(duration.getText()),
                    LocalDate.parse(publishedDate.getText()),
                    titleImage.getImage().getUrl()
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
            selectedMovie.setImage(titleImage.getImage().getUrl());

            FileUtil.deleteMovieFromFile(movieTitle.getText());
            FileUtil.saveMoviesToFile(movieList);
            clearFields();
            tableView.refresh();
        } else {
            showAlert("Error", "No movie selected.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        Movie selectedMovie = tableView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            movieList.remove(selectedMovie);
            FileUtil.deleteMovieFromFile(selectedMovie.getTitle());
            clearFields();
            tableView.refresh();
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
        if (file != null && file.getName().matches(".*\\.(png|jpg|jpeg|gif)$")) {
            titleImage.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    public void handleSearch() {
        String searchText = search.getText().toLowerCase();
        ObservableList<Movie> filteredList = FXCollections.observableArrayList();

        for (Movie movie : movieList) {
            if (movie.getTitle().toLowerCase().contains(searchText) ||
                    movie.getGenre().toLowerCase().contains(searchText) ||
                    String.valueOf(movie.getDuration()).contains(searchText) ||
                    movie.getShowingDate().toString().contains(searchText)) {
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
        titleImage.setImage(null);
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
