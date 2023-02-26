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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class AddItemsController implements Initializable {

    private Database db;
    private MainViewController mainViewController;


    public AddItemsController(Database db, MainViewController mainViewController) {
        this.db = db;
        this.mainViewController = mainViewController;
    }

    public ComboBox<Availability> availability;
    public TextField title;
    public TextField author;
    public Button addItemButton;
    public Button editItemButton;
    public Button cancelButtonAddItem;
    public Label displayUsefulMessage;

//TODO set two views based on the type of user

    @FXML
    public void populateComboBox() {
        availability.getItems().addAll(Availability.values());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateComboBox();

    }


    public void onAddItemButton() {
        if (!availability.getSelectionModel().isEmpty() && !title.getText().isEmpty() && !author.getText().isEmpty()) {
            int itemCode = db.getItems().size() + 1;
            Item item = new Item(itemCode, availability.getSelectionModel().getSelectedItem(), title.getText(), author.getText());
            db.getItems().add(item);
            mainViewController.populateItemView();
            Stage stage = (Stage) author.getScene().getWindow();
            stage.close();
        } else {
            displayUsefulMessage.setTextFill(Color.RED);
            displayUsefulMessage.setText("All fields are required in order to process your request! ");
        }
    }

    public void onCancelButton() {
        mainViewController.tableviewItem.getItems();
        Stage stage = (Stage) title.getScene().getWindow();
        stage.close();

    }
}
