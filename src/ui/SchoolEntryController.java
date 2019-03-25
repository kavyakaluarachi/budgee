package ui;

import Budgets.SchoolBudget;
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

public class SchoolEntryController implements  Initializable {

    private Budget schoolBudget;

    @FXML
    private TextField schoolEntryName;
    @FXML
    private TextField schoolEntryPrice;
    @FXML
    private TextField schoolNumberUnits;
    @FXML
    private TextField schoolEntryType;

    @FXML
    private TextArea schoolTextArea;
    @FXML
    private TextArea budgetLeftTextArea;

    @FXML
    private TableView<SchoolEntry> schoolEntryTableView;
    @FXML
    private TableColumn<SchoolEntry, String> nameColumn;
    @FXML
    private TableColumn<SchoolEntry, Integer> priceColumn;
    @FXML
    private TableColumn<SchoolEntry, String> entryTypeColumn;
    @FXML
    private TableColumn<SchoolEntry, Integer> numUnitsColumn;
    @FXML
    private TableColumn<SchoolEntry, Integer> totalCostColumn;

    @FXML
    private ImageView backgroundImage;

    public void initData(Budget b) {
        this.schoolBudget = b;
        budgetLeftTextArea.setText(Integer.toString(schoolBudget.getTotalBudget()));
        backgroundImage.setImage(new Image("https://i.ibb.co/ThtY3HP/Untitled-design-1.png"));

    }

    public String getSchoolEntryName() {
        System.out.println("You have entered the name as" + schoolEntryName.getText());
        return schoolEntryName.getText();
    }

    public Integer getSchoolEntryPrice() {
        System.out.println("You have entered the price as" + schoolEntryPrice.getText());
        return Integer.parseInt(schoolEntryPrice.getText());
    }

    public Integer getSchoolEntryUnits() {
        System.out.println("You have entered the units as" + schoolNumberUnits.getText());
        return Integer.parseInt(schoolNumberUnits.getText());
    }

    public String getSchoolType() {
        System.out.println("You have entered the type as" + schoolEntryType.getText());
        return schoolEntryType.getText();
    }

    public void updateBudgetLeftText() {
        budgetLeftTextArea.setText(Integer.toString(schoolBudget.getTotalBudget()));
    }

    public void createSchoolEntryButtonPressed() throws EmptyBudgetException {


        SchoolEntry schoolEntry = new SchoolEntry(getSchoolEntryName(), getSchoolEntryPrice(), getSchoolEntryUnits(), getSchoolType(),
                (getSchoolEntryPrice() * getSchoolEntryUnits()));



        if (!(schoolEntry.getType().equalsIgnoreCase("School Supply") ||  schoolEntry.getType().equalsIgnoreCase("Trip") ||
                schoolEntry.getType().equalsIgnoreCase("Club") || schoolEntry.getType().equalsIgnoreCase("Team"))) {

            AlertBox alertBox = new AlertBox();
            alertBox.display("Invalid type", "Sorry you have an invalid type. Please ONLY enter one of the types listed");

        }

        else {



            if (schoolBudget.getTotalBudget() < schoolEntry.getTotalCost()) {
                AlertBox alert = new AlertBox();
                alert.display("Not enough money!", "Sorry you do not have enough money left in this budget. Please choose" +
                        " an item that is less than " + schoolBudget.getTotalBudget() + " " + "dollars");
            }

            if (schoolBudget.getTotalBudget() == 0) {
                AlertBox alert = new AlertBox();
                alert.display("Empty Budget", "Sorry budget is now empty! Entries can no longer be added. You can modify, delete current entries.");
            }

            if (schoolBudget.getEntryMap().containsKey(schoolEntry.getItemName())) {

                try {
                    throw new DuplicateEntryException();

                } catch (DuplicateEntryException e) {
                    AlertBox alertBox = new AlertBox();
                    alertBox.display("Error", "You already have this entry.");
                }

            }

            if (!(schoolBudget.getEntryMap().containsKey(schoolEntry.getItemName())) && !(schoolBudget.getTotalBudget() < schoolEntry.getTotalCost())) {

                // adds the entry onto the foodBudget, updates the budget
                schoolBudget.addEntry(schoolEntry);
                addToSchoolType(schoolEntry, schoolBudget);


                // Pulls out the value of the totalBudget (after adding entry)
                Integer budgetLeft = schoolBudget.getTotalBudget();

                updateBudgetLeftText();

                this.schoolTextArea.appendText("You have just added " + schoolEntry.getUnits() + " " + "of the entry" + " " + schoolEntry.getItemName() + " " +
                        "with the total cost" + " " + schoolEntry.getTotalCost() + " " + "dollars" + " " + "to the budget " + schoolBudget.getBudgetName());

                schoolTextArea.appendText("\n");


                this.schoolTextArea.appendText("You now have" + " " + schoolBudget.getTotalBudget() + " " + "dollars left in the budget");


                // adds properties to the table
                schoolEntryTableView.getItems().add(schoolEntry);

                if (schoolBudget.getTotalBudget() == 0) {
                    AlertBox alert = new AlertBox();
                    alert.display("Empty budget", "Your budget is now empty with $0 left! Return to main screen or edit entries");
                }

            }

        }

    }

    public void changeEntryNameCellEvent(TableColumn.CellEditEvent editedCell) {
        SchoolEntry schoolSelected = schoolEntryTableView.getSelectionModel().getSelectedItem();

        if (schoolBudget.getEntryMap().containsKey(editedCell.getNewValue().toString())) {
            AlertBox alertBox = new AlertBox();
            alertBox.display("Duplicate Name", "You already have an entry with that name. Choose a different name, or edit the existing entry with that name.");
        }

        if (!(schoolBudget.getEntryMap().containsKey(editedCell.getNewValue().toString()))) {
            schoolSelected.setItemName(editedCell.getNewValue().toString());
            schoolSelected.getItemName();
        }

    }

    public void changeEntryPriceCellEvent(TableColumn.CellEditEvent editedCell) {
        SchoolEntry schoolSelected = schoolEntryTableView.getSelectionModel().getSelectedItem();
        int oldCost = schoolSelected.getTotalCost();
        schoolBudget.setTotalBudget(schoolBudget.getTotalBudget() + oldCost);

        schoolSelected.setPrice(Integer.parseInt(editedCell.getNewValue().toString()));
        int newCost = schoolSelected.calculateTotalCost();
        schoolBudget.setTotalBudget(schoolBudget.getTotalBudget() - newCost);
        updateBudgetLeftText();

    }

    public void changeEntryUnitsCellEvent(TableColumn.CellEditEvent editedCell) {
        SchoolEntry schoolSelected = schoolEntryTableView.getSelectionModel().getSelectedItem();
        int oldCost = schoolSelected.getTotalCost();
        schoolBudget.setTotalBudget(schoolBudget.getTotalBudget() + oldCost);

        schoolSelected.setUnits(Integer.parseInt(editedCell.getNewValue().toString()));
        int newCost = schoolSelected.calculateTotalCost();
        schoolBudget.setTotalBudget(schoolBudget.getTotalBudget() - newCost);
        updateBudgetLeftText();
    }

    public void changeEntryTypeCellEvent(TableColumn.CellEditEvent editedCell) throws EmptyBudgetException {
        SchoolEntry schoolSelected = schoolEntryTableView.getSelectionModel().getSelectedItem();
        removeFromSchoolType(schoolSelected, schoolBudget);


        if (!(editedCell.getNewValue().toString().equalsIgnoreCase("School Supply" )|| editedCell.getNewValue().toString().equalsIgnoreCase("Trip" )||
                editedCell.getNewValue().toString().equalsIgnoreCase("Club" ) || editedCell.getNewValue().toString().equalsIgnoreCase("Team"))) {

            AlertBox alertBox = new AlertBox();
            alertBox.display("Invalid Type!", "Please ensure that you change the type to ONLY one of the types listed");

        }

        else {
            schoolSelected.setType(editedCell.getNewValue().toString());

            addToSchoolType(schoolSelected, schoolBudget);

        }


    }


    public void removeFromSchoolType(SchoolEntry schoolEntry, Budget b) {
        if (schoolEntry.getType().equals("School Supply")) {
            b.getLists(1).remove(schoolEntry);

        }
        if (schoolEntry.getType().equals("Club")) {
            b.getLists(2).remove(schoolEntry);
        }

        if (schoolEntry.getType().equals("Team")) {
            b.getLists(3).remove(schoolEntry);
        }

        if (schoolEntry.getType().equals("Trip")) {
            b.getLists(4).remove(schoolEntry);
        }
    }


    public void addToSchoolType(SchoolEntry schoolEntry, Budget b) throws EmptyBudgetException {
        SchoolBudget sb = (SchoolBudget) b;

        if (schoolEntry.getType().equalsIgnoreCase("School Supply")) {
            schoolEntry.setSchoolSupply();
            sb.addSupplyToList(schoolEntry);
        }
        if (schoolEntry.getType().equalsIgnoreCase("Team")) {
            schoolEntry.setTeam();
            sb.addTeamToList(schoolEntry);

        }
        if (schoolEntry.getType().equalsIgnoreCase("Club")) {
            schoolEntry.setClub();
            sb.addClubToList(schoolEntry);
        }
        if (schoolEntry.getType().equalsIgnoreCase("Trip")) {
            schoolEntry.setTrip();
            sb.addTripTolist(schoolEntry);
        }
    }

    public void printListsOfFoodType(Budget b, Integer listType) {
        schoolTextArea.appendText("\n");
        for (AbstractEntry schoolEntry : b.getLists(listType)) {
            this.schoolTextArea.appendText(schoolEntry.getItemName() + " " + "with the total cost" + " " + schoolEntry.getTotalCost() + " " + "dollars");
            schoolTextArea.appendText("\n");
        }
    }



    public void viewSchoolSuppliesEntriesButtonPressed() {
        printListsOfFoodType(schoolBudget, 1);
    }

    public void viewClubsEntriesButtonPressed() {
        printListsOfFoodType(schoolBudget, 2);
    }

    public void viewTeamsEntriesButtonPressed() {
        printListsOfFoodType(schoolBudget, 3);
    }

    public void viewTripsEntriesButtonPressed() {
        printListsOfFoodType(schoolBudget, 4);
    }

    public void schoolBackToMainButtonPressed(ActionEvent event) throws IOException {

        System.out.println("Coing back to main budget page!");
        Parent mainScreen = FXMLLoader.load(getClass().getResource("GUI.fxml"));


        Scene main_screen = new Scene(mainScreen);


//         this line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(main_screen);
        window.show();

    }

    public void deleteButtonPushed() {
        ObservableList<SchoolEntry> selectedRows, allEntries;
        allEntries = schoolEntryTableView.getItems();

        // gives us the rows we have selected
        selectedRows = schoolEntryTableView.getSelectionModel().getSelectedItems();

        for (SchoolEntry entry : selectedRows) {
            allEntries.remove(entry);
            schoolBudget.getEntryMap().remove(entry.getItemName());
            removeFromSchoolType(entry, schoolBudget);

            schoolBudget.setTotalBudget(schoolBudget.getTotalBudget() + entry.calculateTotalCost());
            updateBudgetLeftText();
        }
    }


    public void loadBudgetClicked(ActionEvent actionEvent) throws IOException {

        List<List<String>> arrList = new ArrayList<>();



        for (int i = 0 ; i < schoolEntryTableView.getItems().size() ; i++) {
            SchoolEntry schoolEntry = schoolEntryTableView.getItems().get(i);
            arrList.add(new ArrayList<>());
            arrList.get(i).add(schoolEntry.getItemName());
            arrList.get(i).add("The price for each unit is "  + schoolEntry.getPrice());
            arrList.get(i).add("The number of units is " + schoolEntry.getUnits());
            arrList.get(i).add("The type of unit is " + schoolEntry.getType());

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
        schoolBudget.save("" + schoolBudget.getBudgetName());
        AlertBox alertBox = new AlertBox();
        alertBox.display("Items saved to budget!", "Your file has been saved as" + " " + schoolBudget.getBudgetName() );

    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set up columns


        // Set up columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<SchoolEntry, String>("itemName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<SchoolEntry, Integer>("price"));
        numUnitsColumn.setCellValueFactory(new PropertyValueFactory<SchoolEntry, Integer>("units"));
        totalCostColumn.setCellValueFactory(new PropertyValueFactory<SchoolEntry, Integer>("totalCost"));
        entryTypeColumn.setCellValueFactory(new PropertyValueFactory<SchoolEntry, String>("type"));


        // makes fields editable
        schoolEntryTableView.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        entryTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());


        // select multiple rows of the table
        schoolEntryTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        numUnitsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

    }
}
