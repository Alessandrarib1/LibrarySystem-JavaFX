package com.example.endassignment.Controller;

import com.example.endassignment.Data.Database;
import com.example.endassignment.Model.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;


public class MembersViewController {
    Database db;

    @FXML
    public TableView<Member> tableviewMembers;


    public void populateMembers() {
        tableviewMembers.getItems().clear();
        ObservableList<Member> myMembers = FXCollections.observableArrayList(db.getMembers());
        tableviewMembers.setItems(myMembers);
    }

}
