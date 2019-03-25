package ObserverClasses;

import model.AbstractEntry;

public interface ProjectObserver {
    void update(AbstractEntry entry, int operation);
}
