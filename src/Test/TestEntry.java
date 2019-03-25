package Test;

import Exceptions.EmptyBudgetException;
import Exceptions.NegativeNumException;
import Exceptions.ZeroNumException;
import model.AbstractEntry;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


import ui.FoodEntry;
import ui.SchoolEntry;


public class TestEntry {

    // Test that a single Item does not have negative price
    @Test
    public void testNotNegativePrice() throws NegativeNumException, ZeroNumException, EmptyBudgetException {
        AbstractEntry e4 = new FoodEntry("Apple", 5,2, "Fruit",10);
        assertTrue(e4.validPrice(5));

        AbstractEntry e = new SchoolEntry("Pencil", 10, 1, "School Supply", 10);
        assertTrue(e.validPrice(10));

        try { e.validPrice(-1); }
        catch(NegativeNumException nne) { }
        catch(NumberFormatException nfe)   { fail("correct format!!!"); }


        try { e4.validPrice(0); }
        catch(NegativeNumException nne) { fail("not a negative number!"); }
        catch(ZeroNumException zne) {}


    }




}


