package model;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;

public interface Loadable {

    public List<String> load() throws IOException;
}
