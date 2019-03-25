package ui;

import Budgets.FoodBudget;
import Budgets.SocialBudget;
import Exceptions.DuplicateEntryException;
import Exceptions.EmptyBudgetException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javafx.util.converter.IntegerStringConverter;
import model.AbstractEntry;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SocialEntryController implements Initializable {


    private Budget socialBudget;

    @FXML
    private ImageView backgroundImage;
    @FXML
    private TableView<SocialEntry> socialEntryTableView;
    @FXML
    private TableColumn<SocialEntry, String> nameColumn;
    @FXML
    private TableColumn<SocialEntry, Integer> priceColumn;
    @FXML
    private TableColumn<SocialEntry, Integer> totalCostColumn;
    @FXML
    private TableColumn<SocialEntry, String> typeColumn;
    @FXML
    private TableColumn<SocialEntry, Integer> numUnitsColumn;

    @FXML
    private TextArea budgetLeftTextArea;
    @FXML
    private TextArea socialTextArea;
    @FXML
    private TextField socialEntryName;
    @FXML
    private TextField socialEntryUnits;
    @FXML
    private TextField socialEntryPrice;
    @FXML
    private TextField socialType;

    public void initData(Budget b) {
        this.socialBudget = b;
        budgetLeftTextArea.setText(Integer.toString(socialBudget.getTotalBudget()));
        backgroundImage.setImage(new Image("https://i.ibb.co/1GrdpqM/Social-Entry.png"));

    }


    public String getEntryName() {
        System.out.println("The entry name entered was " + socialEntryName.getText());
        return socialEntryName.getText();
    }

    public Integer getEntryPrice() {
        System.out.println("The entry price entered was " + socialEntryPrice.getText());
        return Integer.parseInt(socialEntryPrice.getText());
    }

    public Integer getEntryUnits() {
        System.out.println("The entry units entered was " + socialEntryUnits.getText());
        return Integer.parseInt(socialEntryUnits.getText());
    }

    public String getSocialType() {
        System.out.println("The food type entered was " + socialType.getText());
        return socialType.getText();
    }


    public void updateBudgetLeftText() {
        budgetLeftTextArea.setText(Integer.toString(socialBudget.getTotalBudget()));
    }


    public void createSocialEntryButtonPressed() throws EmptyBudgetException {

        SocialEntry socialEntry = new SocialEntry(getEntryName(), getEntryPrice(), getEntryUnits(), getSocialType(), (getEntryPrice() * getEntryUnits()));


        if (!(socialEntry.getType().equalsIgnoreCase("Eating Out") || socialEntry.getType().equalsIgnoreCase("Night Out") ||
                socialEntry.getType().equalsIgnoreCase("Gift") || socialEntry.getType().equalsIgnoreCase("Shopping") ||
                socialEntry.getType().equalsIgnoreCase("Trip") || socialEntry.getType().equalsIgnoreCase("Other"))) {

            AlertBox alertBox = new AlertBox();
            alertBox.display("Invalid type", "Sorry you have an invalid type. Please ONLY enter one of the types listed");

        }


        else {



            socialTextArea.textFormatterProperty();

            if (socialBudget.getTotalBudget() < socialEntry.getTotalCost()) {
                AlertBox alert = new AlertBox();
                alert.display("Not enough money!", "Sorry you do not have enough money left in this budget. Please choose" +
                        " an item that is less than " + socialBudget.getTotalBudget() + " " + "dollars");
            }


            if (socialBudget.getTotalBudget() == 0) {
                AlertBox alert = new AlertBox();
                alert.display("Empty Budget", "Sorry budget is now empty! Entries can no longer be added. You can modify, delete current entries.");
            }


            if (socialBudget.getEntryMap().containsKey(socialEntry.getItemName())) {

                try {
                    throw new DuplicateEntryException();

                } catch (DuplicateEntryException e) {
                    AlertBox alertBox = new AlertBox();
                    alertBox.display("Error", "You already have this entry.");
                }

            }

            if (!(socialBudget.getEntryMap().containsKey(socialEntry.getItemName())) && !(socialBudget.getTotalBudget() < socialEntry.getTotalCost())) {

                // adds the entry onto the foodBudget, updates the budget
                socialBudget.addEntry(socialEntry);
                addToSocialType(socialEntry, socialBudget);


                // Pulls out the value of the totalBudget (after adding entry)
                Integer budgetLeft = socialBudget.getTotalBudget();

                updateBudgetLeftText();


                // prints to text area so user can review
                this.socialTextArea.setText("You have just added " + socialEntry.getUnits() + " " + "of the entry" + " " + socialEntry.getItemName() + " " +
                        "with the total cost" + " " + socialEntry.getTotalCost() + " " + "into the budget" + " " + socialBudget.getBudgetName());

                socialTextArea.appendText("\n");


                // creates new FoodEntryRowWrapper

                this.socialTextArea.appendText("You now have" + " " + socialBudget.getTotalBudget() + " " + "dollars left in the budget");


                // adds properties to the table
                socialEntryTableView.getItems().add(socialEntry);

                if (socialBudget.getTotalBudget() == 0) {
                    AlertBox alert = new AlertBox();
                    alert.display("Empty budget", "Your budget is now empty with $0 left! Return to main screen or edit entries");
                }

            }

        }

    }

    public void changeEntryNameCellEvent(TableColumn.CellEditEvent editedCell) {
        SocialEntry socialSelected = socialEntryTableView.getSelectionModel().getSelectedItem();

        if (socialBudget.getEntryMap().containsKey(editedCell.getNewValue().toString())) {
            AlertBox alertBox = new AlertBox();
            alertBox.display("Duplicate Name", "You already have an entry with that name. Choose a different name, or edit the existing entry with that name.");
        }

        if (!(socialBudget.getEntryMap().containsKey(editedCell.getNewValue().toString()))) {
            socialSelected.setItemName(editedCell.getNewValue().toString());
            socialSelected.getItemName();
        }

    }

    public void changeEntryPriceCellEvent(TableColumn.CellEditEvent editedCell) {
        SocialEntry socialSelected = socialEntryTableView.getSelectionModel().getSelectedItem();
        int oldCost = socialSelected.getTotalCost();
        socialBudget.setTotalBudget(socialBudget.getTotalBudget() + oldCost);

        socialSelected.setPrice(Integer.parseInt(editedCell.getNewValue().toString()));
        int newCost = socialSelected.calculateTotalCost();
        socialBudget.setTotalBudget(socialBudget.getTotalBudget() - newCost);
        updateBudgetLeftText();

    }

    public void changeEntryUnitsCellEvent(TableColumn.CellEditEvent editedCell) {
        SocialEntry socialSelected = socialEntryTableView.getSelectionModel().getSelectedItem();
        int oldCost = socialSelected.getTotalCost();

        socialBudget.setTotalBudget(socialBudget.getTotalBudget() + oldCost);

        socialSelected.setUnits(Integer.parseInt(editedCell.getNewValue().toString()));
        int newCost = socialSelected.calculateTotalCost();
        socialBudget.setTotalBudget(socialBudget.getTotalBudget() - newCost);
        updateBudgetLeftText();
    }

    public void changeEntryTypeCellEvent(TableColumn.CellEditEvent editedCell) throws EmptyBudgetException {
        SocialEntry socialSelected = socialEntryTableView.getSelectionModel().getSelectedItem();
        removeFromSocialType(socialSelected,socialBudget);

        if (!(editedCell.getNewValue().toString().equalsIgnoreCase("Eating Out" )|| editedCell.getNewValue().toString().equalsIgnoreCase("Night Out" )||
                editedCell.getNewValue().toString().equalsIgnoreCase("Trip" ) || editedCell.getNewValue().toString().equalsIgnoreCase("Gift")
        || editedCell.getNewValue().toString().equalsIgnoreCase("Shopping") || editedCell.getNewValue().toString().equalsIgnoreCase("Other"))) {

            AlertBox alertBox = new AlertBox();
            alertBox.display("Invalid Type!", "Please ensure that you change the type to ONLY one of the types listed");

        }

        else {

            socialSelected.setType(editedCell.getNewValue().toString());

            addToSocialType(socialSelected, socialBudget);

        }


    }

    public void removeFromSocialType(SocialEntry oldType, Budget b) {

        if (oldType.getType().equalsIgnoreCase("Eating Out")) {
            b.getLists(1).remove(oldType);
        }

        if (oldType.getType().equalsIgnoreCase("Night Out")) {
            b.getLists(2).remove(oldType);
        }

        if (oldType.getType().equalsIgnoreCase("Gift")) {
           b.getLists(3).remove(oldType);
        }

        if (oldType.getType().equalsIgnoreCase("Shopping")) {
            b.getLists(4).remove(oldType);
        }

        if (oldType.getType().equalsIgnoreCase("Trip")) {
            b.getLists(5).remove(oldType);
        }

        if (oldType.getType().equalsIgnoreCase("Other")) {
           b.getLists(6).remove(oldType);
        }

    }


    public void addToSocialType(SocialEntry socialEntry, Budget b) throws EmptyBudgetException {

        SocialBudget sb = (SocialBudget) b;

        if (socialEntry.getType().equalsIgnoreCase("Eating Out")) {
            socialEntry.setEatingOut();
            sb.addToEatingOutList(socialEntry);
        }

        if (socialEntry.getType().equalsIgnoreCase("Night Out")) {
            socialEntry.setNightOut();
            sb.addToNightOuts(socialEntry);
        }

        if (socialEntry.getType().equalsIgnoreCase("Gift")) {
            socialEntry.setGift();
            sb.addToGifts(socialEntry);
        }

        if (socialEntry.getType().equalsIgnoreCase("Shopping")) {
            socialEntry.setShopping();
            sb.addToShoppingList(socialEntry);
        }

        if (socialEntry.getType().equalsIgnoreCase("Trip")) {
            socialEntry.setTrip();
            sb.addToTripList(socialEntry);
        }
    }


    public void printListsOfSocialType(Budget b, Integer listType) {
        socialTextArea.appendText("\n");
        for (AbstractEntry socialEntry : b.getLists(listType)) {
            this.socialTextArea.appendText(socialEntry.getItemName() + " " + "with the total cost" + " " + socialEntry.getTotalCost() + " " + "dollars");
            socialTextArea.appendText("\n");
        }

    }


    // eating out, night out, gift, shopping, other
    public void viewEatingOutButtonPressed() {
        printListsOfSocialType(socialBudget, 1);
    }
    public void viewNightOutsButtonPressed() {
        printListsOfSocialType(socialBudget,2 );
    }

    public void viewGiftsButtonPressed() {
        printListsOfSocialType(socialBudget, 3);
    }

    public void viewShoppingButtonPressed() {
        printListsOfSocialType(socialBudget, 4);
    }

    public void viewTripButtonPressed() {
        printListsOfSocialType(socialBudget, 5);
    }

    public void viewOtherButtonPressed() {
        printListsOfSocialType(socialBudget, 6);
    }


    public void socialBackToMainButtonPressed(ActionEvent event) throws IOException {

        System.out.println("Going back to main budget page!");
        Parent mainScreen = FXMLLoader.load(getClass().getResource("GUI.fxml"));

        Scene main_screen = new Scene(mainScreen);


//         this line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(main_screen);
        window.show();

    }

    //REMOVE ENTRIES

    public void deleteButtonPushed() {

        ObservableList<SocialEntry> selectedRows, allEntries;
        allEntries = socialEntryTableView.getItems();

        // gives us the rows that were selected
        selectedRows = socialEntryTableView.getSelectionModel().getSelectedItems();

        for (SocialEntry entry : selectedRows) {
            allEntries.remove(entry);

            removeFromSocialType(entry, socialBudget);
            socialBudget.getEntryMap().remove(entry.getItemName());
            socialBudget.setTotalBudget(socialBudget.getTotalBudget() + entry.calculateTotalCost() );

            updateBudgetLeftText();
        }
    }


    public void loadBudgetClicked(ActionEvent actionEvent) throws IOException {

        List<List<String>> arrList = new ArrayList<>();



        for (int i = 0 ; i < socialEntryTableView.getItems().size() ; i++) {
            SocialEntry socialEntry = socialEntryTableView.getItems().get(i);
            arrList.add(new ArrayList<>());
            arrList.get(i).add(socialEntry.getItemName());
            arrList.get(i).add("The price for each unit is "  + socialEntry.getPrice());
            arrList.get(i).add("The number of units is " + socialEntry.getUnits());
            arrList.get(i).add("The type of unit is " + socialEntry.getType());

        }


        for (int i = 0; i < arrList.size(); i++) {
            for (int j = 0; j < arrList.get(i).size(); j++) {
                System.out.println(arrList.get(i).get(j));
            }
        }

        AlertBox alertBox = new AlertBox();
        alertBox.display("Items loaded!", "View your entries in the console." );


    }


    public void saveBudgetClicked() throws IOException{
        socialBudget.save("" + socialBudget.getBudgetName());
        AlertBox alertBox = new AlertBox();
        alertBox.display("Items saved to budget!", "Your file has been saved as" + " " + socialBudget.getBudgetName() );

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        // Set up columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<SocialEntry, String>("itemName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<SocialEntry, Integer>("price"));
        numUnitsColumn.setCellValueFactory(new PropertyValueFactory<SocialEntry, Integer>("units"));
        totalCostColumn.setCellValueFactory(new PropertyValueFactory<SocialEntry, Integer>("totalCost"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<SocialEntry, String>("type"));


        // makes fields editable
        socialEntryTableView.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        typeColumn.setCellFactory(TextFieldTableCell.forTableColumn());


        // select multiple rows of the table
        socialEntryTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        numUnitsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));



    }

}
