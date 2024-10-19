package com.um.movie.controller;

import com.um.movie.MovieApplication;
import com.um.movie.model.Movie;
import com.um.movie.model.Ticket;
import com.um.movie.util.FileUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DashBoardController {

    @FXML
    private Label soldLabel;

    @FXML
    private Label incomeLabel;

    @FXML
    private Label moviesLabel;

    @FXML
    private ImageView movieImage, admin;

    @FXML
    public void initialize() {
        //set admin image
        admin.setImage(new Image(MovieApplication.class.getResourceAsStream("admin.png")));

        //membuat gambar film random dari yang masih tayang
        List<String> images = FileUtil.loadMoviesFromFile().stream()
                .filter(movie -> (movie.getCurrent().equals("Showing")))
                .map(Movie::getImage)
                .collect(Collectors.toList());
        // mengambil data image acak dari film yang masih berstatus showing
        randomImage(images);

        //update 3 data diatas gambar film
        updateDashboard();
    }

    private void randomImage(List<String> images){
        // Memeriksa apakah daftar tidak kosong
        if (!images.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(images.size()); // Mendapatkan indeks acak
            String randomImage = images.get(randomIndex); // Mengambil gambar menggunakan indeks acak

            movieImage.setImage(new Image(randomImage));
        } else {
            movieImage.setImage(new Image(MovieApplication.class.getResourceAsStream("no-movie-icon.png")));
        }

    }
    private void updateDashboard() {
        List<Ticket> tickets = FileUtil.loadTicketsFromFile();

        // Menghitung total sold ticket
        int totalSoldTickets = tickets.size();
        soldLabel.setText(String.valueOf(totalSoldTickets));

        // Menghitung total income
        double totalIncome = tickets.stream()
                .mapToDouble(Ticket::getTotal)
                .sum();
        incomeLabel.setText(String.valueOf(totalIncome));

        // Mengupdate label movie count, asumsi loadMoviesFromFile akan digunakan
        int movieCount = FileUtil.loadMoviesFromFile().size();
        moviesLabel.setText(String.valueOf(movieCount));
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
