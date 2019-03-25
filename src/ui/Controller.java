package ui;

import Budgets.FoodBudget;
import Budgets.SchoolBudget;
import Budgets.SocialBudget;
import Exceptions.InvalidTypeException;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import jdk.internal.util.xml.impl.Input;
import model.AbstractEntry;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    Budget b;



    public TextArea consolearea;

    public TextField name;

    public TextField type;

    public TextField amount;

    public ImageView backgroundImage;

    public String getBudgetName() {
        System.out.println("The name entered was " + name.getText());
        return name.getText();
    }

    public String getBudgetType()  {
        System.out.println("The budget type entered was " + type.getText());

        return type.getText();

    }

    public Integer getBudgetAmount() {
        System.out.println("The budget amount entered was " + amount.getText());
        return Integer.parseInt(amount.getText());
    }


    public void createButtonClicked() {
        System.out.println("User clicked 'Create Budget' button!");
        getBudgetName();
        getBudgetAmount();
//
//        if (!(getBudgetName().equalsIgnoreCase("Food")) && !(getBudgetName().equalsIgnoreCase("Social")) && (getBudgetName().equalsIgnoreCase("School"))) {
//
//            AlertBox alert = new AlertBox();
//            alert.display("Invalid information", "Hmm! Make sure you have only entered 'food', 'social' or 'school' as your budget type");
//
//        }


            if (getBudgetType().equalsIgnoreCase("Food")) {
                b = new FoodBudget(getBudgetName(), getBudgetAmount());
            }

            if (getBudgetType().equalsIgnoreCase("School")) {
                b = new SchoolBudget(getBudgetName(), getBudgetAmount());
            }

            if (getBudgetType().equalsIgnoreCase("Social")) {
                b = new SocialBudget(getBudgetName(), getBudgetAmount());
            }


            b.setBudgetType(getBudgetType());
            this.consolearea.appendText("You have successfully created a new budget called" + " " + b.getBudgetName() + " " +
                    "of type " + " " + b.getBudgetType() + " " + "with the total amount " + " " + b.getTotalBudget() + " " + "dollars");

        }



    public void changeSceneButtonPushed(ActionEvent event) throws IOException {

        if (b==null) {
            System.out.println("budget type was null");
        }

        if (b.getBudgetType()==null) {
            System.out.println("flag");
        }

        if (b.getBudgetType().equalsIgnoreCase("Social")) {
            b.setBudgetType("Social");

            System.out.println("Creating a new School Entry page!");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("GUISocialEntry.fxml"));
            Parent mainScreen = loader.load();

            System.out.println("Create entry button pushed");

            Scene social_entry_scene = new Scene(mainScreen);

            SocialEntryController controller = loader.getController();
            controller.initData(b);


//         this line gets the stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(social_entry_scene);
            window.show();
        }


        if (b.getBudgetType().equalsIgnoreCase("School")) {
            b.setBudgetType("School");

            System.out.println("Creating a new School Entry page!");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("GUISchoolEntry.fxml"));
            Parent mainScreen = loader.load();

            System.out.println("Create Entry Button pushed");
            Scene school_entry_scene = new Scene(mainScreen);

            SchoolEntryController controller = loader.getController();
            controller.initData(b);

////         this line gets the stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(school_entry_scene);
            window.show();
        }


        if (b.getBudgetType().equalsIgnoreCase("Food")) {
            b.setBudgetType("Food");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("GUIFoodEntry.fxml"));
            Parent mainScreen = loader.load();


            System.out.println("Create Entry button pushed");
            Scene food_entry_scene = new Scene(mainScreen);

            // Access the controller and call a method

            FoodEntryController controller = loader.getController();

            controller.initData(b);


//          this line gets the stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(food_entry_scene);
            window.show();


        }
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        backgroundImage.setImage(new Image("https://i.ibb.co/ypWMKB4/Budgee-5.png"));

    }
}



