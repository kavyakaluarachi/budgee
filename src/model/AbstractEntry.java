package model;
import Exceptions.EmptyBudgetException;
import Exceptions.ZeroNumException;
import Exceptions.NegativeNumException;
import ui.Budget;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public abstract class AbstractEntry implements Item, Savable {
    protected String itemName;
    protected int units;
    protected int price;
    protected int totalCost;
    protected String type;


    Budget b;
    Map<String, List<AbstractEntry>> budgetMap = new HashMap<>();

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Budget getB() {
        return b;
    }

    public void setB(Budget b) throws EmptyBudgetException{
        if(b != this.b) {
            this.b = b;
            b.addEntry(this);
            this.runChecks();
        }
    }

    private void runChecks() throws EmptyBudgetException{
        this.b.updateBudget(calculateTotalCost());
    }


    // MODIFIES: this
    // EFFECTS: sets the name of the Item to the parameter passed
    @Override
    public void addItemName(String name) {
        this.itemName = name;

    }

    //EFFECTS: Checks to see if the price entered is valid or not
    @Override
    public boolean validPrice(int price) throws NegativeNumException, ZeroNumException {
        if (price >=0) {
            addUnitPrice(price);
            return true;
        }

        else {
            return false;
        }
    }

    //MODIFIES: this
    //EFFECTS: calculates the total cost by multiplying rice by number of units
    @Override
    public int calculateTotalCost() {
        this.totalCost = price * units;
        return totalCost;
    }


    //REQUIRES: price is not negative
    //MODIFIES: this
    //EFFECTS: sets the price of the Item to the parameter passed
    @Override
    public void addUnitPrice(int price) {
        this.price = price;

    }

    //MODIFIES: this
    //EFFECTS: sets the number of units to parameter passed
    @Override
    public void numberOfUnits(int num) {
        this.units = num;

    }

    //EFFECTS: saves entry into a file
    public void save(String fileName) throws IOException {
        Files.write(Paths.get(fileName), Arrays.asList(itemName+","+units+","+price+","+totalCost), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public abstract boolean isImportant();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntry that = (AbstractEntry) o;
        return Objects.equals(itemName, that.itemName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(itemName);
    }


    public String getItemName() {

        return itemName;
    }

    public int getUnits() {

        return units;
    }

    public int getPrice() {

        return price;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public String getType() {

        return type;
    }
}
