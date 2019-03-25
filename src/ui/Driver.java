package ui;





import Exceptions.EmptyBudgetException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Driver extends Application{



    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        System.out.println("Triggered");
        primaryStage.setTitle("Budgee The Budget App");
        primaryStage.setScene(new Scene(root, 600,577));
        primaryStage.show();




    }



    public static void main(String[] args) throws MalformedURLException, IOException, EmptyBudgetException {

        launch(args);



        BufferedReader br = null;

        try {
            String webURL = "https://www.ugrad.cs.ubc.ca/~cs210/2018w1/welcomemsg.html";

            URL url = new URL(webURL);

            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            StringBuilder sb = new StringBuilder();

            ArrayList<String> words;

            line = br.readLine();

            words = splitOnSpace(line);

            words.set(2, "Kavya's");
            words.set(5, "Budget Project");


            for (String string: words) {
                sb.append(string);
                sb.append(" ");
            }

            System.out.println(sb);

        } finally {

            if (br != null) {
                br.close();
            }

        }

        Driver driver = new Driver();

    }


    public static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split(" ");
        return new ArrayList<>(Arrays.asList(splits));
    }

}
