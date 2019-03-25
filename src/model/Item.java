package model;


import Exceptions.EmptyBudgetException;
import Exceptions.ZeroNumException;
import Exceptions.NegativeNumException;

public interface Item {
    public void addItemName(String name);
    public void addUnitPrice(int price);
    public boolean validPrice(int price) throws NegativeNumException, ZeroNumException;
    public void numberOfUnits(int num);
    public int calculateTotalCost();
}
