package Budgets;

import Exceptions.EmptyBudgetException;
import model.AbstractEntry;
import ui.Budget;
import ui.FoodEntry;

import java.util.ArrayList;
import java.util.List;


public class FoodBudget extends Budget {
    List<AbstractEntry> vegetables = new ArrayList<>();
    List<AbstractEntry> fruits = new ArrayList<>();
    List<AbstractEntry> meats = new ArrayList<>();
    List<AbstractEntry> grains = new ArrayList<>();
    List<AbstractEntry> dairyList = new ArrayList<>();
    List<AbstractEntry> snacks = new ArrayList<>();

    public FoodBudget(String name, int total) {
        super(name, total);
    }

    public void addVegetableToList(FoodEntry food) throws EmptyBudgetException{
        if (!vegetables.contains(food)) {
            vegetables.add(food);
            addEntry(food);
        }
    }

    public void addFruitsToList(FoodEntry food) throws EmptyBudgetException {
        if (!fruits.contains(food)) {
            fruits.add(food);
            addEntry(food);
        }
    }

    public void addMeatToList(FoodEntry food) throws EmptyBudgetException {
        if (!meats.contains(food)) {
            meats.add(food);
            addEntry(food);
        }
    }

    public void addSnacksToList(FoodEntry food) throws EmptyBudgetException {
        if (!snacks.contains(food)) {
            snacks.add(food);
            addEntry(food);
        }
    }

    public void addGrainsToList(FoodEntry food) throws EmptyBudgetException {
        if (!grains.contains(food)) {
            grains.add(food);
            addEntry(food);
        }
    }

    public void addDairyToList(FoodEntry food) throws EmptyBudgetException{
        if (!dairyList.contains(food)) {
            dairyList.add(food);
            addEntry(food);
        }
    }

    public List<AbstractEntry> getLists(int listType) {
        if (listType ==1) {
            return vegetables;
        }
        if (listType ==2) {
            return fruits;
        }

        if (listType ==3) {
            return meats;
        }

        if (listType ==4) {
            return grains;
        }

        if (listType ==5){
            return dairyList;
        }

        else return snacks;
    }

}
