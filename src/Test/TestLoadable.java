package Test;

import org.junit.jupiter.api.Test;
import ui.Budget;
import ui.Entry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestLoadable {

    @Test
    public void testLoad() throws IOException {
        List<String> testItems = Files.readAllLines(Paths.get("inputfile.txt"));
        assertEquals(testItems.get(1), "Item1");
        assertEquals(testItems.get(2), "Item2");
        assertEquals(testItems.get(3), "Item3");
    }

}