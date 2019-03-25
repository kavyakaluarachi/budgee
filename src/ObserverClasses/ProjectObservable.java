package ObserverClasses;

import model.AbstractEntry;
import ui.Budget;

import java.util.ArrayList;
import java.util.List;

public abstract class ProjectObservable {
    private List<Budget> observers = new ArrayList<>();

    public void addObserver(Budget budget) {
        if (!observers.contains(budget)) {
            observers.add(budget);
        }
    }

    public void notifyObservers(AbstractEntry entry, int operation) {

        for (ProjectObserver observer : observers) {
            observer.update(entry, operation);
        }
    }
}
