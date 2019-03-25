package ui;

import model.AbstractEntry;

public class FoodEntry extends AbstractEntry {


    public FoodEntry(String name, Integer price, Integer units, String type, Integer totalCost) {
        this.itemName = name;
        this.price = price;
        this.units = units;
        this.type = type;
        this.totalCost = totalCost;
    }



    private Boolean vegetable;
    private Boolean fruit;
    private Boolean meat;
    private Boolean snack;
    private Boolean grain;
    private Boolean dairy;

    @Override
    public boolean isImportant() {
        return false;
    }

    public void setVegetable(Boolean bool) {
        this.vegetable = bool;
        this.type = "Vegetable";
    }

    public void setFruit(Boolean bool) {
        this.fruit = bool;
        this.type = "Fruit";
    }

    public void setMeat(Boolean bool) {
        this.meat = bool;
        this.type = "Meat";
    }

    public void setSnack(Boolean bool) {
        this.snack = bool;
        this.type = "Snack";
    }

    public void setGrain(Boolean bool) {
        this.grain = bool;
        this.type = "Grain";
    }

    public void setDairy(Boolean bool) {
        this.dairy= bool;
        this.type = "Dairy";
    }

}
