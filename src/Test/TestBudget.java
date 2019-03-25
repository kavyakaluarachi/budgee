package Test;

import Budgets.FoodBudget;
import Budgets.SchoolBudget;
import Budgets.SocialBudget;
import Exceptions.EmptyBudgetException;
import Exceptions.ZeroNumException;
import Exceptions.NegativeNumException;
import model.AbstractEntry;
import org.junit.jupiter.api.Test;
import sun.invoke.empty.Empty;
import ui.*;
import model.MainBudget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBudget {

    //Tests to see if entry items are being added to budget
    @Test
    public void testEntriesAdded() throws EmptyBudgetException{

        Budget b = new SchoolBudget("TestSchool", 100);

        AbstractEntry e = new FoodEntry("Pencil", 10, 1, "School Supply", 10);
        b.addEntry(e);

        assertEquals(b.entryListSize(), 1);

        Budget c = new SocialBudget("TestSocial", 100);

        AbstractEntry e3 = new SocialEntry("Dinner", 10, 1, "Eating Out", 10);

        c.addEntry(e3);

        assertEquals(c.getEntryList().size(), 1);


        Budget d = new FoodBudget("TestFood", 100);

        AbstractEntry e4 = new FoodEntry("Apple", 5,2, "Fruit",10);

        d.addEntry(e4);

        assertEquals(d.getEntryList().size(), 1);

    }

    //Tests to see if entry prices are being deducted from budget total properly
    @Test
    public void testBudgetDeduction() throws NegativeNumException, ZeroNumException, EmptyBudgetException {

        Budget b = new SchoolBudget("TestSchool", 100);

        AbstractEntry e = new SchoolEntry("Pencil", 10, 1, "School Supply", 10);
        b.addEntry(e);


        assertEquals(b.getTotalBudget(), 90);


        Budget d = new FoodBudget("TestFood", 100);

        AbstractEntry e4 = new FoodEntry("Apple", 5,2, "Fruit",10);

        d.addEntry(e4);

        assertEquals(b.getTotalBudget(), 90);



        Budget c = new SocialBudget("TestSocial", 100);

        AbstractEntry e3 = new SocialEntry("Dinner", 10, 1, "Eating Out", 10);

        c.addEntry(e3);

        assertEquals(c.getTotalBudget(), 90);




    }


}
