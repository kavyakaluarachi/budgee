package Test;

import Exceptions.EmptyBudgetException;
import Exceptions.NegativeNumException;
import Exceptions.ZeroNumException;
import org.junit.jupiter.api.Test;
import ui.Budget;
import ui.Entry;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;


public class TestSavable {
//
//    @Test
//    public void testSave() throws IOException, NegativeNumException, ZeroNumException, EmptyBudgetException{
//
//
//        Budget b = new Budget("testBudget", 100);
//        b.save("testBudget.txt");
//
//        Entry testEntry = new Entry();
//        testEntry.addItemName("Name");
//        testEntry.validPrice(5);
//        testEntry.numberOfUnits(2);
//        testEntry.calculateTotalCost();
//
//        b.addEntry(testEntry);
//        testEntry.save("testBudget.txt");
//
//
//        Entry testEntry2 = new Entry();
//        testEntry2.addItemName("Name2");
//        testEntry2.validPrice(5);
//        testEntry2.numberOfUnits(2);
//        testEntry2.calculateTotalCost();
//
//
//        b.addEntry(testEntry2);
//        testEntry2.save("testBudget.txt");
//
//        List<String> testList = b.load();
//        assertEquals(testList.get(1), "Name,2,5,10");
//        assertEquals(testList.get(2), "Name2,2,5,10");
//
//    }


}
