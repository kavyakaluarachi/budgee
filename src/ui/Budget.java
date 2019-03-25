package ui;

import Exceptions.EmptyBudgetException;
import ObserverClasses.ProjectObserver;
import model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public abstract class Budget implements MainBudget, Savable, Loadable, ProjectObserver{
   private Map<String, AbstractEntry> entryMap = new HashMap<>();


    private String budgetName;
    private int totalBudget, initialBudget;
    private String type;



    public Budget(String name, int total) {

        this.budgetName = name;
        this.totalBudget = total;
        this.initialBudget = total;
    }

    public Map<String, AbstractEntry> getEntryMap() {
        return entryMap;
    }

    //MODIFIES: this
    //EFFECTS: subtracts the total cost of the entry Item from total budget, and adds entry to the list of entries
    @Override
    public void addEntry(AbstractEntry e) throws EmptyBudgetException{
        if(!entryMap.containsValue(e)) {
            entryMap.put(e.getItemName(),e);
            e.setB(this);
        }

    }

    public int updateBudget(int totalCost) throws EmptyBudgetException {
        if ((totalBudget - totalCost) < 0) {
            throw new EmptyBudgetException();
        }
        this.totalBudget = totalBudget - totalCost;
        return totalBudget;

    }

    // EFFECTS: returns the list of entries
    public Collection<AbstractEntry> getEntryList() {
        return entryMap.values();
    }


    //EFFECTS: returns the size of the list of entries
    public int entryListSize() {
        return entryMap.size();
    }

    //EFFECTS: returns the updated total budget
    public int getTotalBudget() {
        return totalBudget;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetType(String type) {
        this.type = type;
    }

    public String getBudgetType() {
        return type;
    }

    public abstract List<AbstractEntry> getLists(int listType);

    public void setTotalBudget(int totalBudget) {
        this.totalBudget = totalBudget;
    }

    @Override
    public List<String> load() throws IOException{
        List<String> lines = Files.readAllLines(Paths.get(budgetName+ ".txt"));
        return lines;
    }

    @Override
    public void save(String fileName) throws IOException{
        File file = new File(fileName);
        if(file.exists()) {
            file.delete();
        }
        Files.write(file.toPath(), Arrays.asList(budgetName+","+initialBudget));
        for(AbstractEntry e : entryMap.values()) {
            e.save(fileName);
        }
    }


    @Override
    public void update(AbstractEntry e, int operation) {
        if (operation ==1) {
            // replacing the key
            entryMap.put(e.getItemName(), e);

        }

        if (operation ==2) {
            entryMap.remove(e.getItemName());
            this.totalBudget = totalBudget + e.getTotalCost();
            System.out.println("The entry" + e.getTotalCost() + " has been successfully removed from the budget.");
        }

        if (operation ==3) {
            this.totalBudget = totalBudget + e.getTotalCost();

        }



    }
}

