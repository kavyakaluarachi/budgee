package ui;

import model.AbstractEntry;

public class SchoolEntry extends AbstractEntry {

    // name, price, units, type, totalcost

    public SchoolEntry(String name, Integer price, Integer units, String type, Integer totalCost) {
        this.itemName= name;
        this.price = price;
        this.units = units;
        this.type = type;
        this.totalCost = totalCost;
    }

    Boolean schoolSupply;
    Boolean trip;
    Boolean club;
    Boolean team;

    @Override
    public boolean isImportant() {
        return false;
    }

    public void setSchoolSupply() {
        this.schoolSupply = true;
        this.type= "School Supply";
    }

    public void setTrip() {
        this.trip = true;
        this.type = "Trip";
    }

    public void setClub() {
        this.club = true;
        this.type = "Club";
    }

    public void setTeam() {
        this.team = true;
        this.type = "Team";
    }

    @Override
    public String getType() {
//        if (schoolSupply) {
//            return "School Supply";
//        }
//        if (trip) {
//            return "Trip";
//        }
//
//        if (club){
//            return "Club";
//        }
//
//        else return "Team";

        return type;
    }

}
