package com.example.endassignment.Controller;

import com.example.endassignment.Data.Database;
import com.example.endassignment.LoginApplication;
import com.example.endassignment.Model.Availability;
import com.example.endassignment.Model.Item;
import com.example.endassignment.Model.Member;
import com.example.endassignment.Model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    Database db;
    User user;



    public MainViewController(Database db) {
        this.db = db;
    }

    public MainViewController(Database db, User user) {
        this.db = db;
        this.user = user;

    }


    @FXML
    public Label welcomeMessage;
    @FXML
    public Label errorMessageCollection;
    @FXML
    public Label errorMessageMembers;
    @FXML
    public Label errorMessageReceiving;
    @FXML
    public Label errorMessageLending;
    @FXML
    public Label lateReturnErrorMessage;

    @FXML
    public Button lendItemButton;
    @FXML
    public Button receivingItemButton;
    @FXML
    public Button deleteButton;
    @FXML
    public TextField itemCodeLendingItem;
    @FXML
    public RadioButton unavailableItemsRadioButton;

    @FXML
    public RadioButton allItemsRadioButton;
    @FXML
    public TextField membersIdTextBox;
    @FXML
    public TextField itemCodeRecivingItem;
    @FXML
    public TableView<Item> tableviewItem;
    @FXML
    public TableView<Item> newTableViewOfItems;
    @FXML
    public TableView<Member> tableviewMembers;

    @FXML
    public Button addMemberButton;
    @FXML
    public Button deleteMemberButton;
    @FXML
    public Button editMemberButton;
    private static final String styleSheetsPath = "/com/example/endassignment/style.css";
    final ToggleGroup groupOfRadioButtons = new ToggleGroup();

    public void setRadioButtons(){
        unavailableItemsRadioButton.setToggleGroup(groupOfRadioButtons);
        allItemsRadioButton.setToggleGroup(groupOfRadioButtons);
        allItemsRadioButton.setSelected(true);

    }
    public void onButtonImportClick(){
       FileChooser chosenFile = new FileChooser();
        chosenFile.setTitle("Open csv File");
        File csvFile = chosenFile.showOpenDialog(receivingItemButton.getScene().getWindow());
        int memberCode;
        if (csvFile != null) {
            try {
                Files.readAllLines(Paths.get(csvFile.getPath()))
                        .stream().skip(1)
                        .forEach(line ->
                                db.getMembers().add(
                                        new Member(
                                                db.getMembers().size() + 1,
                                                line.split(";")[2],
                                                line.split(";")[1],
                                                line.split(";")[4]
                                        )

                                )
                        );
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        populateMembers();
    }





    public void cleanLabelsReceiving() {
        errorMessageReceiving.setText("");
        errorMessageLending.setText("");
        lateReturnErrorMessage.setText("");

    }

    public void displaySuccessfullMessages() {
        errorMessageLending.setTextFill(Color.GREEN);
        errorMessageLending.setText("Successful operation, enjoy the book!!!");

        itemCodeLendingItem.setText("");
        membersIdTextBox.setText("");
    }

    public void displaySuccessfullMessagesREceiving() {
        errorMessageReceiving.setTextFill(Color.GREEN);
        errorMessageReceiving.setText("Successful operation, thanks for returning the book!!!");
        itemCodeRecivingItem.setText("");
    }

    public void displayNoCodeFoundMessage() {
        errorMessageLending.setTextFill(Color.RED);
        errorMessageLending.setText("No item code found in the database");
    }

    public void displayNoCodeFoundMessageReceiving() {
        errorMessageReceiving.setTextFill(Color.RED);
        errorMessageReceiving.setText("No code item found in the database");
    }

    public void displayNoMemberFoundMessage() {
        errorMessageLending.setTextFill(Color.RED);
        errorMessageLending.setText("No member code found in the database");
    }

    public void inputTypeErrorMessage() {
        errorMessageLending.setTextFill(Color.RED);
        errorMessageLending.setText("We expect you to enter a number, not letters or empty texfields are accepted here! Thanks");
    }

    public void inputTypeErrorMessageReceiving() {
        errorMessageReceiving.setTextFill(Color.RED);
        errorMessageReceiving.setText("We expect you to enter a number, not letters or empty texfields are accepted here! Thanks");
    }

    public void errorItemNotLended() {
        errorMessageReceiving.setTextFill(Color.RED);
        errorMessageReceiving.setText("This item has not been lending!!!");
    }

    public void errorBookNotAvailableAtTheMoment() {
        errorMessageLending.setTextFill(Color.RED);
        errorMessageLending.setText("Sorry, this book is not available at the moment. Try again in a different day!");
    }


    @FXML
    private void buttonLendingClick() {
        cleanLabelsReceiving();
        try {
            int itemCode = Integer.parseInt(itemCodeLendingItem.getText());
            int memberId = Integer.parseInt(membersIdTextBox.getText());
            Item item = db.getItemByID(itemCode);
            if (item == null) {
                displayNoCodeFoundMessage();
                return;
            }
            Member member = db.getMemberByID(memberId);
            if (member == null) {
                displayNoMemberFoundMessage();
                return;
            }
            if (item.availability == Availability.no) {
                errorBookNotAvailableAtTheMoment();
                return;
            }
            item.availability = Availability.no;
            item.setMember(member);
            item.setLender(member.getFullName());
            item.setLendindDate(LocalDate.now());
            //item.setLendindDate(LocalDate.of(2022, 12, 01));
            displaySuccessfullMessages();
            populateItemView();
        } catch (Exception e) {
            inputTypeErrorMessage();
        }

    }

    @FXML
    private void onDeleteItemButtonClick() {
        Item item = tableviewItem.getSelectionModel().getSelectedItem();
        if (item != null) {
            for (Item i : db.getItems()) {
                if (item.getItemCode() == i.getItemCode()) {
                    db.getItems().remove(item);
                    populateItemView();

                    break;
                }
            }

        }
    }

    @FXML
    private void onLendingItemButtonClick() {
        cleanLabelsReceiving();
        try {
            int itemCode = Integer.parseInt(itemCodeRecivingItem.getText());
            Item item = db.getItemByID(itemCode);
            if (item == null) {
                displayNoCodeFoundMessageReceiving();
                return;
            }
            if (item.availability == Availability.no) {
                long time = ChronoUnit.DAYS.between(item.getLendindDate(), LocalDate.now());
                if (time >= 21) {
                    lateReturnErrorMessage.setTextFill(Color.RED);
                    lateReturnErrorMessage.setText("The book is late by " + time + "days");
                }
                item.availability = Availability.yes;
                item.setLendindDate(null);
                displaySuccessfullMessagesREceiving();
                populateItemView();
            } else {
                errorItemNotLended();
            }
        } catch (Exception e) {
            inputTypeErrorMessageReceiving();
        }
    }

    public void populateMembers() {
        tableviewMembers.getItems().clear();
        ObservableList<Member> myMembers = FXCollections.observableArrayList(db.getMembers());
        tableviewMembers.setItems(myMembers);
    }

    @FXML
    public void onEditItemButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("editItems-view.fxml"));
        Item item = tableviewItem.getSelectionModel().getSelectedItem();
        if (item != null) {
            EditItemsController editItemsController = new EditItemsController(db, this, item);
            fxmlLoader.setController(editItemsController);
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(getClass().getResource(styleSheetsPath).toExternalForm());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); //disables the other windowns. only use one at time
            stage.setTitle("Edit Item!");
            stage.setScene(scene);
            stage.showAndWait();
        } else {
            errorMessageCollection.setTextFill(Color.RED);
            errorMessageCollection.setText("Please select an item in order to be able to edit it!!!");
        }
    }

    @FXML
    public void onEditMemberButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("editMembers-view.fxml"));
        Member member = tableviewMembers.getSelectionModel().getSelectedItem();
        if (member != null) {
            EditMembersController editMembersController = new EditMembersController(db, this, member);
            fxmlLoader.setController(editMembersController);
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(getClass().getResource(styleSheetsPath).toExternalForm());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); //disables the other windowns. only use one at time
            stage.setTitle("Edit Member!");
            stage.setScene(scene);
            stage.showAndWait();
        } else {
            errorMessageMembers.setTextFill(Color.RED);
            errorMessageMembers.setText("Please select a member in order to be able to edit it!!!");
        }
    }

    public void clearFields() {
        errorMessageMembers.setText("");
    }

    @FXML
    public void onAddNewItemButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("addItem-view.fxml"));
        AddItemsController addItemsController = new AddItemsController(db, this);
        fxmlLoader.setController(addItemsController);
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource(styleSheetsPath).toExternalForm());
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); //disables the other windowns. only use one at time
        stage.setTitle("Add Items!");
        stage.setScene(scene);
        stage.showAndWait();

    }

    @FXML
    public void onAddMembersButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("addMembers-view.fxml"));
        AddMembersController addMembersController = new AddMembersController(db, this);
        fxmlLoader.setController(addMembersController);
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource(styleSheetsPath).toExternalForm());
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); //disables the other windowns. only use one at time
        stage.setTitle("Add Members!");
        stage.setScene(scene);
        stage.showAndWait();

    }

    @FXML
    public void onDeleteButtonClick() {
        Member member = tableviewMembers.getSelectionModel().getSelectedItem();

        if (member != null) {
            for (Member m : db.getMembers()) {
                if (member.getId() == (m.getId())) {
                    db.getMembers().remove(member);
                    populateMembers();
                    break;
                }
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeMessage.setText("Welcome " + user.getFullName());
        setRadioButtons();
        populateItemView();
        populateMembers();
        //allItemsRadioButton.setSelected(true);
    }
    @FXML
    public void populateItemView() {
        tableviewItem.getItems().clear();
        ObservableList<Item> desirableList;
        ObservableList<Item> myItems = FXCollections.observableArrayList(db.getItems());
       ObservableList<Item> unavailableItems = FXCollections.observableArrayList(db.getListOfUnavailableItems());
        unavailableItemsRadioButton.setUserData(db.getListOfUnavailableItems());
        allItemsRadioButton.setUserData(myItems);
        groupOfRadioButtons.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (groupOfRadioButtons.getSelectedToggle() == unavailableItemsRadioButton) {
                    tableviewItem.setItems(unavailableItems);


                } else {

                    tableviewItem.setItems(myItems);

                }
            }
        });

       tableviewItem.setItems(myItems);
    }
}
