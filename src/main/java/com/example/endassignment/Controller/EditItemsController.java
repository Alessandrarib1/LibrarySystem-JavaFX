package com.example.endassignment.Controller;

import com.example.endassignment.Data.Database;
import com.example.endassignment.Model.Availability;
import com.example.endassignment.Model.Item;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditItemsController implements Initializable {

    Database db;
    MainViewController mainViewController;
    Item item;

    public EditItemsController(Database db, MainViewController mainViewController, Item item) {
        this.db = db;
        this.mainViewController = mainViewController;
        this.item = item;
    }


    //comboBox not being used right now
    public ComboBox<Availability> availability;
    public TextField title;
    public TextField author;
    public Button updateItemButton;
    public Button cancelItemButton;
    public Label displayUsefulMessage;


    @FXML
    public void onUpdateItemButton() {
        if (!availability.getSelectionModel().isEmpty() && !title.getText().isEmpty() && !author.getText().isEmpty()) {
            item.setAuthor(author.getText());
            item.setTitle(title.getText());
            item.setAvailability(availability.getSelectionModel().getSelectedItem());

            mainViewController.populateItemView();
            Stage stage = (Stage) title.getScene().getWindow();
            stage.close();
        } else {
            displayUsefulMessage.setText("All fields are required in order to process your request! ");
        }
    }

    @FXML
    public void onCancelItemButton() {
        mainViewController.tableviewItem.getItems();
        Stage stage = (Stage) title.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText(item.getTitle());
        author.setText(item.getAuthor());
        availability.getItems().addAll(Availability.values());
        availability.setValue(item.availability);
    }


}