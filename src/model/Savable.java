package model;

import java.io.IOException;

public interface Savable {
    public void save(String fileName) throws IOException;
}
