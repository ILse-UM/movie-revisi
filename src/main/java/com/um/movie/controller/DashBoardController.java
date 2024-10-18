package com.um.movie.controller;

import com.um.movie.MovieApplication;
import com.um.movie.model.Ticket;
import com.um.movie.util.FileUtil;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class DashBoardController {

    @FXML
    private Label soldLabel;

    @FXML
    private Label incomeLabel;

    @FXML
    private Label moviesLabel;

    @FXML
    private ImageView carousel;

    private List<String> imagePaths;  // Daftar path gambar untuk carousel
    private int currentImageIndex = 0;
    private Timeline carouselTimeline;

    @FXML
    public void initialize() {
        // Inisialisasi gambar-gambar yang akan ditampilkan dalam carousel
        imagePaths = loadImagePaths();

        if (!imagePaths.isEmpty()) {
            // Menampilkan gambar pertama pada ImageView
            updateCarouselImage();
            // Memulai timeline untuk mengganti gambar setiap 3 detik (3000 ms)
            startCarousel();
        }

        // update data 3 box diatas carousel
        updateDashboard();
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

    private List<String> loadImagePaths() {
        // Daftar path gambar yang akan digunakan di carousel, bisa diambil dari file atau resource
        List<String> paths = new ArrayList<>();
        paths.add("file:/path/to/image1.jpg");
        paths.add("file:/path/to/image2.jpg");
        paths.add("file:/path/to/image3.jpg");
        // Tambahkan path gambar lainnya
        return paths;
    }

    private void updateCarouselImage() {
        // Mengubah gambar yang ditampilkan di ImageView
        if (!imagePaths.isEmpty()) {
            String imagePath = imagePaths.get(currentImageIndex);
            Image image = new Image(imagePath);
            carousel.setImage(image);
        }
    }

    private void startCarousel() {
        carouselTimeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            // Increment indeks gambar saat ini
            currentImageIndex = (currentImageIndex + 1) % imagePaths.size();
            // Update gambar di ImageView
            updateCarouselImage();
        }));
        carouselTimeline.setCycleCount(Timeline.INDEFINITE);  // Agar terus berjalan
        carouselTimeline.play();
    }

    public void stopCarousel() {
        if (carouselTimeline != null) {
            carouselTimeline.stop();
        }
    }


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
        MovieApplication.switchScene((Node) event.getSource(), "customer.fxml");
    }
}
