package com.example.endassignment.Controller;
// Importing input output classes


import com.example.endassignment.Data.Database;
import com.example.endassignment.LoginApplication;
import com.example.endassignment.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LoginController {
    Database db;

    public LoginController(Database db) {
        this.db = db;
    }

    @FXML
    private PasswordField passwordText;
    @FXML
    private TextField usernameText;
    @FXML
    private Button loginButton;

    @FXML
    private Label labelDisplayError;

    @FXML
    protected void loginButtonClick() throws IOException {
        List<User> users = db.getUsers();

        for (User u : users) {
            if (u.getUsername().equals(usernameText.getText()) && u.getPassword().equals(passwordText.getText())) {
                labelDisplayError.setTextFill(Color.BLACK);
                labelDisplayError.setText("Congrats, you are in!");
                MainViewController controller = new MainViewController(db, u);
                openNewScene(controller, "mainView-view.fxml");
                break;
            } else {
                labelDisplayError.setTextFill(Color.RED);
                labelDisplayError.setText("Invalid username/password combination");
            }

        }


    }

    private void openNewScene(Object controller, String fxmlFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(fxmlFileName));
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(), 670, 470);
        Stage loginStage = (Stage) loginButton.getScene().getWindow();
        loginStage.setTitle("Main");
        loginStage.setScene(scene);
    }

}