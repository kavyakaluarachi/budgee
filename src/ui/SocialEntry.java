package ui;

import model.AbstractEntry;

public class SocialEntry extends AbstractEntry {

    public SocialEntry(String name, Integer price, Integer units, String type, Integer totalCost) {
        this.itemName = name;
        this.price = price;
        this.units = units;
        this.type = type;
        this.totalCost = totalCost;
    }

    private Boolean eatingOut;
    private Boolean nightOut;
    private Boolean trip;
    private Boolean gift;
    private Boolean other;
    private Boolean shopping;

    public void setEatingOut() {
        this.eatingOut = true;
        this.type = "Eating out";

    }

    public void setNightOut() {
        this.nightOut = true;
        this.type = "Night out";
    }

    public void setTrip() {
        this.trip = true;
        this.type = "Trip";
    }

    public void setGift() {
        this.gift = true;
        this.type = "Gift";
    }

    public void setOther() {
        this.other = true;
        this.type = "Other";
    }

    public void setShopping() {
        this.shopping = true;
        this.type = "Shopping";
    }
    @Override
    public boolean isImportant() {
        return false;
    }
}
