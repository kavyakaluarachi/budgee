package model;

import Exceptions.EmptyBudgetException;


import java.util.ArrayList;
import java.util.Collection;

public interface MainBudget {


    public void addEntry(AbstractEntry e) throws EmptyBudgetException;
    public Collection<AbstractEntry> getEntryList();
    public int entryListSize();
//

}

