package ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AllBudgets implements Initializable{

    @FXML
    private TableView<Budget> budgetTableView;
    @FXML
    private TableColumn<Budget, String> budgetNameColumn;
    @FXML
    private TableColumn<Budget, Integer> totalBudgetAmountColumn;
    @FXML
    private TableColumn<Budget, Integer> moneyLeftColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        budgetNameColumn.setCellValueFactory(new PropertyValueFactory<Budget, String>("budgetName"));
        totalBudgetAmountColumn.setCellValueFactory(new PropertyValueFactory<Budget, Integer>("initialBudget"));
        moneyLeftColumn.setCellValueFactory(new PropertyValueFactory<Budget, Integer>("totalBudget"));

    }







}
