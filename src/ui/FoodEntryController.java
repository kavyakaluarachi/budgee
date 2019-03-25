package ui;

import Budgets.FoodBudget;
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

public class FoodEntryController implements Initializable {


   private Budget foodBudget;


   public ImageView backgroundImage;

   @FXML
    private TableView<FoodEntry> foodEntryTableView;

   @FXML
    private TableColumn<FoodEntry, String> nameColumn;

   @FXML
    private TableColumn<FoodEntry, Integer> priceColumn;

   @FXML
    private TableColumn<FoodEntry, Integer> totalCostColumn;

   @FXML
    private TableColumn<FoodEntry, String> foodTypeColumn;

   @FXML
    private TableColumn<FoodEntry, Integer> numUnitsColumn;


   @FXML
    private TextArea budgetLeftArea;

   @FXML
    private TextArea foodTextArea;

   @FXML
    private TextField foodEntryName;

   @FXML
    private TextField foodEntryUnits;

   @FXML
    private TextField foodEntryPrice;

   @FXML
    private TextField foodType;

    public void initData(Budget b) {
        this.foodBudget = b;
        budgetLeftArea.setText(Integer.toString(foodBudget.getTotalBudget()));

//        URL url = getClass().getResource("FOODBackground.png");
//
        backgroundImage.setImage(new Image("https://image.ibb.co/j1Ae9q/Background-Image-Food.png"));

//        URL url = getClass().getResource("/FOODBackground.png");
//        backgroundImage.setImage(new Image(url.toString()));
    }


    public String getEntryName() {
        System.out.println("The entry name entered was " + foodEntryName.getText());
        return foodEntryName.getText();
    }

    public Integer getEntryPrice() {
        System.out.println("The entry price entered was " + foodEntryPrice.getText());
        return Integer.parseInt(foodEntryPrice.getText());
    }

    public Integer getEntryUnits() {
        System.out.println("The entry units entered was " + foodEntryUnits.getText());
        return Integer.parseInt(foodEntryUnits.getText());
    }

    public String getFoodType() {
        System.out.println("The food type entered was " + foodType.getText());
        return foodType.getText();
    }


    public void updateBudgetLeftText() {
        budgetLeftArea.setText(Integer.toString(foodBudget.getTotalBudget()));
    }


    // READS DATA FROM THE TEXT FIELDS, CREATES NEW FOOD ENTRY, AND ADDS TO TABLE
    public void createFoodEntryButtonPressed() throws EmptyBudgetException {


        // Creates a new food entry with the text field properties
        FoodEntry foodEntry = new FoodEntry(getEntryName(), getEntryPrice(), getEntryUnits(), getFoodType(), (getEntryPrice() * getEntryUnits()));
        // prints to text area so user can review

        if (!(foodEntry.getType().equalsIgnoreCase("Vegetable") || foodEntry.getType().equalsIgnoreCase("Fruit") ||
                foodEntry.getType().equalsIgnoreCase("Snack") || foodEntry.getType().equalsIgnoreCase("Dairy") ||
                foodEntry.getType().equalsIgnoreCase("Grain") || foodEntry.getType().equalsIgnoreCase("Meat"))) {
            AlertBox alertBox = new AlertBox();
            alertBox.display("Invalid type", "Sorry you have an invalid type. Please ONLY enter one of the types listed");
        }


        else {



        foodTextArea.textFormatterProperty();

        if (foodBudget.getTotalBudget() < foodEntry.getTotalCost()) {
            AlertBox alert = new AlertBox();
            alert.display("Not enough money!", "Sorry you do not have enough money left in this budget. Please choose" +
                    " an item that is less than "+ foodBudget.getTotalBudget() + " " + "dollars");
        }



        if (foodBudget.getTotalBudget() ==0) {
            AlertBox alert = new AlertBox();
            alert.display("Empty Budget", "Sorry budget is now empty! Entries can no longer be added. You can modify, delete current entries.");
        }


        if (foodBudget.getEntryMap().containsKey(foodEntry.getItemName())) {

            try {
                throw new DuplicateEntryException();

            } catch (DuplicateEntryException e) {
                AlertBox alertBox = new AlertBox();
                alertBox.display("Error", "You already have this entry.");
            }

        }



        if (!(foodBudget.getEntryMap().containsKey(foodEntry.getItemName())) && !(foodBudget.getTotalBudget() < foodEntry.getTotalCost())) {

            // adds the entry onto the foodBudget, updates the budget
            foodBudget.addEntry(foodEntry);
            addToFoodType(foodEntry, foodBudget);


            // Pulls out the value of the totalBudget (after adding entry)
            Integer budgetLeft = foodBudget.getTotalBudget();

            updateBudgetLeftText();


            this.foodTextArea.setText("You have just added " + foodEntry.getUnits() + " " + "of the entry" + " " + foodEntry.getItemName() + " " +
                    "with the total cost" + " " + foodEntry.getTotalCost() + " " + "into the budget" + " " +  foodBudget.getBudgetName());

            foodTextArea.appendText("\n");



            // creates new FoodEntryRowWrapper

            this.foodTextArea.appendText("You now have" + " " + foodBudget.getTotalBudget() + " " + "dollars left in the budget");


            // adds properties to the table
            foodEntryTableView.getItems().add(foodEntry);

            if (foodBudget.getTotalBudget()==0) {
                AlertBox alert = new AlertBox();
                alert.display("Empty budget", "Your budget is now empty with $0 left! Return to main screen or edit entries");
            }

        }

        }

    }

    public void changeEntryNameCellEvent(TableColumn.CellEditEvent editedCell) {
        FoodEntry foodSelected = foodEntryTableView.getSelectionModel().getSelectedItem();

        if (foodBudget.getEntryMap().containsKey(editedCell.getNewValue().toString())) {
            AlertBox alertBox = new AlertBox();
            alertBox.display("Duplicate Name", "You already have an entry with that name. Choose a different name, or edit the existing entry with that name.");
        }

        if (!(foodBudget.getEntryMap().containsKey(editedCell.getNewValue().toString()))) {
            foodSelected.setItemName(editedCell.getNewValue().toString());
            foodSelected.getItemName();
        }

    }

    public void changeEntryPriceCellEvent(TableColumn.CellEditEvent editedCell) {
        FoodEntry foodSelected = foodEntryTableView.getSelectionModel().getSelectedItem();
        int oldCost = foodSelected.getTotalCost();
        foodBudget.setTotalBudget(foodBudget.getTotalBudget() + oldCost);

        foodSelected.setPrice(Integer.parseInt(editedCell.getNewValue().toString()));
        int newCost = foodSelected.calculateTotalCost();
        foodBudget.setTotalBudget(foodBudget.getTotalBudget() - newCost);
        updateBudgetLeftText();

    }

    public void changeEntryUnitsCellEvent(TableColumn.CellEditEvent editedCell) {
        FoodEntry foodSelected = foodEntryTableView.getSelectionModel().getSelectedItem();
        int oldCost = foodSelected.getTotalCost();
        foodBudget.setTotalBudget(foodBudget.getTotalBudget() + oldCost);

        foodSelected.setUnits(Integer.parseInt(editedCell.getNewValue().toString()));
        int newCost = foodSelected.calculateTotalCost();
        foodBudget.setTotalBudget(foodBudget.getTotalBudget() - newCost);
        updateBudgetLeftText();
    }

    public void changeEntryTypeCellEvent(TableColumn.CellEditEvent editedCell) throws EmptyBudgetException {
        FoodEntry foodSelected = foodEntryTableView.getSelectionModel().getSelectedItem();
        removeFromFoodType(foodSelected,foodBudget);

        if (!(editedCell.getNewValue().toString().equalsIgnoreCase("Vegetable" )|| editedCell.getNewValue().toString().equalsIgnoreCase("Fruit" )||
                editedCell.getNewValue().toString().equalsIgnoreCase("Snack" ) || editedCell.getNewValue().toString().equalsIgnoreCase("Dairy") ||
                editedCell.getNewValue().toString().equalsIgnoreCase("Grain" )|| editedCell.getNewValue().toString().equalsIgnoreCase("Meat" ))) {

            AlertBox alertBox = new AlertBox();
            alertBox.display("Invalid Type!", "Please ensure that you change the type to ONLY one of the types listed");
        }

        else {

            foodSelected.setType(editedCell.getNewValue().toString());

            addToFoodType(foodSelected, foodBudget);
        }


    }

    public void removeFromFoodType(FoodEntry oldType, Budget b) {

        if (oldType.getType().equals("Vegetable")) {
            List<AbstractEntry> vegetableList = foodBudget.getLists(1);
            vegetableList.remove(oldType);
        }

        if (oldType.getType().equals("Fruit")) {
            List<AbstractEntry> fruitList = foodBudget.getLists(2);
            fruitList.remove(oldType);
        }

        if (oldType.getType().equals("Meat")) {
            List<AbstractEntry> meatList = foodBudget.getLists(3);
            meatList.remove(oldType);
        }

        if (oldType.getType().equals("Grain")) {
            List<AbstractEntry> grainList = foodBudget.getLists(4);
            grainList.remove(oldType);
        }

        if (oldType.getType().equals("Dairy")) {
            List<AbstractEntry> dairyList = foodBudget.getLists(5);
            dairyList.remove(oldType);
        }

        if (oldType.getType().equals("Snack")) {
            List<AbstractEntry> snackList = foodBudget.getLists(6);
            snackList.remove(oldType);
        }


    }

    public void addToFoodType(FoodEntry food, Budget b) throws EmptyBudgetException {

        FoodBudget fb = (FoodBudget) b;

        if (food.getType().equals("Vegetable") || food.getType().equals("vegetable")) {
            food.setVegetable(true);
            fb.addVegetableToList(food);
        }

        if (food.getType().equals("Fruit") || food.getType().equals("fruit")) {
            food.setFruit(true);
            fb.addFruitsToList(food);
        }

        if (food.getType().equals("Meat") || food.getType().equals("meat")) {
            food.setMeat(true);
            fb.addMeatToList(food);
        }
        if (food.getType().equals("Grain") || food.getType().equals("grain")) {
            food.setGrain(true);
            fb.addGrainsToList(food);
        }
        if (food.getType().equals("Dairy") || food.getType().equals("dairy")) {
            food.setDairy(true);
            fb.addDairyToList(food);
        }
        if (food.getType().equals("Snack") || food.getType().equals("snack")) {
            food.setSnack(true);
            fb.addSnacksToList(food);
        }
    }


    public void printListsOfFoodType(Budget b, Integer listType) {
        foodTextArea.appendText("\n");
        for (AbstractEntry foodEntry : b.getLists(listType)) {
            this.foodTextArea.appendText(foodEntry.getItemName() + " " + "with the total cost " + " " + foodEntry.getTotalCost() + " " + "dollars");
            foodTextArea.appendText("\n");

        }

    }


    public void viewVegetableEntriesButtonPressed() {
        printListsOfFoodType(foodBudget, 1);
    }

    public void viewFruitEntriesButtonPressed() {
        printListsOfFoodType(foodBudget, 2);
    }

    public void viewMeatEntriesButtonPressed() {
        printListsOfFoodType(foodBudget, 3);
    }

    public void viewGrainEntriesButtonPressed() {
        printListsOfFoodType(foodBudget, 4);
    }

    public void viewDairyEntriesButtonPressed() {
        printListsOfFoodType(foodBudget, 5);
    }

    public void viewSnackEntriesButtonPressed() {
        printListsOfFoodType(foodBudget, 6);
    }


    public void foodBackToMainButtonPressed(ActionEvent event) throws IOException {

        System.out.println("Going back to main budget page!");
        Parent mainScreen = FXMLLoader.load(getClass().getResource("GUI.fxml"));


        Scene main_screen = new Scene(mainScreen);


//         this line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(main_screen);
        window.show();

    }

    //REMOVE ENTRIES

   


    public void showButtonPressed(ActionEvent actionEvent) throws IOException {

        List<List<String>> arrList = new ArrayList<>();



        for (int i = 0 ; i < foodEntryTableView.getItems().size() ; i++) {
           FoodEntry foodEntry = foodEntryTableView.getItems().get(i);
            arrList.add(new ArrayList<>());
            arrList.get(i).add(foodEntry.getItemName());
            arrList.get(i).add("The price for each unit is "  + foodEntry.getPrice());
            arrList.get(i).add("The number of units is " + foodEntry.getUnits());
            arrList.get(i).add("The type of unit is " + foodEntry.getType());

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
        foodBudget.save("" + foodBudget.getBudgetName());
        AlertBox alertBox = new AlertBox();
        alertBox.display("Items saved to budget!", "Your file has been saved as" + " " + foodBudget.getBudgetName() );

    }

    public void deleteButtonPushed() {

        ObservableList<FoodEntry> selectedRows, allEntries;
        allEntries = foodEntryTableView.getItems();

        // gives us the rows that were selected
        selectedRows = foodEntryTableView.getSelectionModel().getSelectedItems();

        for (FoodEntry entry : selectedRows) {
            allEntries.remove(entry);
            removeFromFoodType(entry, foodBudget);
            foodBudget.getEntryMap().remove(entry.getItemName());
            foodBudget.setTotalBudget(foodBudget.getTotalBudget() + entry.calculateTotalCost() );
            updateBudgetLeftText();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


            // Set up columns
            nameColumn.setCellValueFactory(new PropertyValueFactory<FoodEntry, String>("itemName"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<FoodEntry, Integer>("price"));
            numUnitsColumn.setCellValueFactory(new PropertyValueFactory<FoodEntry, Integer>("units"));
            totalCostColumn.setCellValueFactory(new PropertyValueFactory<FoodEntry, Integer>("totalCost"));
            foodTypeColumn.setCellValueFactory(new PropertyValueFactory<FoodEntry, String>("type"));


            // makes fields editable
            foodEntryTableView.setEditable(true);
            nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            foodTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());


            // select multiple rows of the table
            foodEntryTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        numUnitsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));



    }

}
