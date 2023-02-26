package com.example.endassignment.Controller;

import com.example.endassignment.Data.Database;
import com.example.endassignment.Model.Member;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class AddMembersController implements Initializable {

    private Database db;
    private MainViewController mainViewController;

    public AddMembersController(Database db, MainViewController mainViewController) {
        this.db = db;
        this.mainViewController = mainViewController;
    }

    @FXML
    public TextField firstname;
    @FXML
    public TextField lastname;
    @FXML
    private DatePicker birthdate;
    @FXML
    public Button addMemberButton;
    @FXML
    public Button cancelButtonAdd;
    public Label displayErrorMessageMember;
    private static final String dateFormat = "dd/MM/yyyy";


    @FXML
    public void onCancelButton() {
        mainViewController.populateMembers();
        Stage stage = (Stage) firstname.getScene().getWindow();
        stage.close();
    }


    public String getDateFromDatePicker(DatePicker datepicker) {

        if (datepicker.getValue() != null) {
            return datepicker.getValue().format(DateTimeFormatter.ofPattern(dateFormat));
        } else {
            try {
                LocalDate.parse(datepicker.getEditor().getText(), DateTimeFormatter.ofPattern(dateFormat));
            } catch (DateTimeParseException dtpe) {
                return null;
            }
            return datepicker.getEditor().getText();
        }
    }

    @FXML
    public void onAddMemberButton() {
        if (!firstname.getText().isEmpty() && !lastname.getText().isEmpty() && (birthdate.getValue() != null || !birthdate.getEditor().getText().isEmpty())) {
            int id = db.getMembers().size() + 1;
            Member member = new Member(id, firstname.getText(), lastname.getText(), getDateFromDatePicker(birthdate));
            db.getMembers().add(member);
            mainViewController.populateMembers();
            Stage stage = (Stage) firstname.getScene().getWindow();
            stage.close();
        } else {
            displayErrorMessageMember.setTextFill(Color.RED);
            displayErrorMessageMember.setText("All fields are required in order to process your request! ");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        birthdate.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                if (object != null) {
                    return formatter.format(object);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);
                } else {
                    return null;
                }
            }
        });
    }
}
