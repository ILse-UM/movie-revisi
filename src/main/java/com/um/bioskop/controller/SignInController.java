package com.um.bioskop.controller;

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
    private Button login;

    @FXML
    public void handlelogin(){
        String username = this.username.getText();
        String password = this.password.getText();

        if(FileUtil.verifyLogin(username, password)){

            System.out.println("login berhasil, masuk ke dalam sistem");

            //to-do pindah scene ke dashboard
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Username atau Password salah");
            alert.showAndWait();
        }
    }
}
