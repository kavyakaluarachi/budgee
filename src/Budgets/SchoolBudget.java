package Budgets;


import Exceptions.EmptyBudgetException;
import model.AbstractEntry;
import sun.invoke.empty.Empty;
import ui.Budget;

import ui.SchoolEntry;

import java.util.ArrayList;
import java.util.List;


public class SchoolBudget extends Budget {

    List<AbstractEntry> supplies = new ArrayList<>();
    List<AbstractEntry> clubs = new ArrayList<>();
    List<AbstractEntry> teams = new ArrayList<>();
    List<AbstractEntry> trips = new ArrayList<>();



    public SchoolBudget(String name, int total) {
        super(name, total);
    }

    public void addSupplyToList(SchoolEntry supply)  throws EmptyBudgetException{
        if (!supplies.contains(supply)) {
            supplies.add(supply);
            addEntry(supply);
        }
    }

    public void addClubToList(SchoolEntry club) throws EmptyBudgetException{
        if (!clubs.contains(club)) {
            clubs.add(club);
            addEntry(club);
        }
    }

    public void addTeamToList(SchoolEntry team) throws EmptyBudgetException {
        if (!teams.contains(team)) {
            teams.add(team);
            addEntry(team);
        }
    }

    public void addTripTolist(SchoolEntry trip) throws EmptyBudgetException {
        if (!trips.contains(trip)) {
            trips.add(trip);
            addEntry(trip);
        }
    }

    public List<AbstractEntry> getLists(int listType) {
        if (listType ==1) {
            return supplies;
        }
        if (listType ==2) {
            return clubs;
        }

        if (listType ==3) {
            return teams;
        }

        else return trips;
    }


}
