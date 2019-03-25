package Budgets;


import Exceptions.EmptyBudgetException;
import model.AbstractEntry;
import ui.Budget;
import ui.SocialEntry;

import java.util.ArrayList;
import java.util.List;

public class SocialBudget extends Budget{
    List<AbstractEntry> eatingOutList = new ArrayList<>();
    List<AbstractEntry> nightOuts = new ArrayList<>();
    List<AbstractEntry> gifts = new ArrayList<>();
    List<AbstractEntry> shoppingList = new ArrayList<>();
    List<AbstractEntry> tripList = new ArrayList<>();
    List<AbstractEntry> otherList = new ArrayList<>();


    public SocialBudget(String name, int total) {
        super(name, total);
    }

    public void addToTripList(SocialEntry trip) throws EmptyBudgetException {
        if (!tripList.contains(trip)) {
            tripList.add(trip);
            addEntry(trip);
        }
    }

    public void addToEatingOutList(SocialEntry eatOut)  throws EmptyBudgetException{
        if (!eatingOutList.contains(eatOut)) {
            eatingOutList.add(eatOut);
            addEntry(eatOut);
        }
    }

    public void addToNightOuts(SocialEntry nightOut) throws EmptyBudgetException{
        if (!nightOuts.contains(nightOut)) {
            nightOuts.add(nightOut);
            addEntry(nightOut);
        }
    }

    public void addToGifts(SocialEntry gift) throws EmptyBudgetException {
        if (!gifts.contains(gift)) {
            gifts.add(gift);
            addEntry(gift);
        }
    }

    public void addToShoppingList(SocialEntry shoppingItem) throws EmptyBudgetException {
        if (!shoppingList.contains(shoppingItem)) {
            shoppingList.add(shoppingItem);
            addEntry(shoppingItem);
        }
    }

    public void addToOtherList(SocialEntry otherItem) throws EmptyBudgetException {
        if (!otherList.contains(otherItem)) {
            otherList.add(otherItem);
            addEntry(otherItem);
        }
    }


    public List<AbstractEntry> getLists(int listType) {
        if (listType ==1) {
            return eatingOutList;
        }
        if (listType ==2) {
            return nightOuts;
        }

        if (listType ==3) {
            return gifts;
        }
        if (listType ==4) {
            return shoppingList;
        }

        if (listType==5) {
            return tripList;
        }

        else return otherList;
    }



}
