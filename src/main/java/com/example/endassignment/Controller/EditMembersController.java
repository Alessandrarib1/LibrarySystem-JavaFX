package com.example.endassignment.Controller;

import com.example.endassignment.Data.Database;
import com.example.endassignment.Model.Member;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class EditMembersController implements Initializable {
    private Database db;
    private MainViewController mainViewController;
    Member member;

    public EditMembersController(Database db, MainViewController mainViewController, Member member) {
        this.db = db;
        this.mainViewController = mainViewController;
        this.member = member;

    }

    @FXML
    public TextField firstname;
    @FXML
    public TextField lastname;
    @FXML
    private DatePicker birthdateMember;

    @FXML
    private Label labelDisplayMessageEditScene;
    @FXML
    public Button updateMemberButton;

    @FXML
    public Button cancelButton;

    @FXML
    public void onCancelButtonClick() {
        mainViewController.populateMembers();
        Stage stage = (Stage) firstname.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onUpdateMemberButton() {

        if (!firstname.getText().isEmpty() && !lastname.getText().isEmpty() && (birthdateMember.getValue() != null || birthdateMember.getEditor().getText() != null)) {
            member.setFirstname(firstname.getText());
            member.setLastname(lastname.getText());
            String date = getDateFromDatePicker(birthdateMember.getValue());

            member.setBirthdate(date);
            mainViewController.populateMembers();
            Stage stage = (Stage) firstname.getScene().getWindow();
            stage.close();
        }
    }

    private String getDateFromDatePicker(LocalDate oldDate) {

        if (!birthdateMember.getValue().equals(oldDate)) {
            return birthdateMember.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            try {
                LocalDate.parse(birthdateMember.getEditor().getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException dtpe) {
                return null;
            }
            return birthdateMember.getEditor().getText();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstname.setText(member.getFirstname());
        lastname.setText(member.getLastname());
        LocalDate date = LocalDate.parse(member.getBirthdate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        birthdateMember.setValue(date);
    }
}
