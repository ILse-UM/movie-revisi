package com.um.movie.controller;

import com.um.movie.MovieApplication;
import com.um.movie.util.FileUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignInController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        FileUtil.loadAdmins();
    }

    @FXML
    public void handleLogin() {
        String usernameText = this.username.getText();
        String passwordText = this.password.getText();

        if (FileUtil.verifyLogin(usernameText, passwordText)) {
            System.out.println("Login berhasil, masuk ke dalam sistem");

            // Pindah scene ke dashboard
            MovieApplication.switchScene(loginButton, "dashboard.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Username atau Password salah");
            alert.showAndWait();
        }
    }
}
