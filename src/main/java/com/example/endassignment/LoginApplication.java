package com.example.endassignment;

import com.example.endassignment.Controller.LoginController;
import com.example.endassignment.Data.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginApplication extends Application {
    private final Database db = new Database();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login-view.fxml"));
        LoginController loginController = new LoginController(db);
        fxmlLoader.setController(loginController);
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/com/example/endassignment/style.css").toExternalForm());
        stage.setTitle("Login!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() {
        //everything here will be excuted when pressing close
       db.writeAllFiles();
    }
}