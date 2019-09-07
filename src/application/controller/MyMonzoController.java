package application.controller;

import application.Main;
import application.model.Category;
import application.model.LineItem;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

// JavaFX uses fx:id and annotation-based initialization.
// onAction to identify method (same name)

public class MyMonzoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyMonzoController.class);

    // Imports the Main for main exchange to view
    private Main mainApp = null;

    public void setMainApp(Main main) {
        this.mainApp = main;

        categoryChoiceBox.setItems(FXCollections.observableArrayList(Arrays.asList(Category.values())));
        dataDisplayTable.setItems(mainApp.getLineItems());
    }

    public void exitProgramme(ActionEvent event) throws IOException {
        this.mainApp.exitProgramme();
    }

    @FXML
    private TextField newCategory;

    @FXML
    private TableView<LineItem> dataDisplayTable;

    @FXML
    private TableColumn<LineItem, String> dateColumn;

    @FXML
    private TableColumn<LineItem, String> descriptionColumn;

    @FXML
    private TableColumn<LineItem, Double> amountColumn;

    @FXML
    private TableColumn<LineItem, String> categoryColumn;

    @FXML
    private ChoiceBox categoryChoiceBox;

    @FXML
    private BarChart barChartTotalsPerCategory;

    XYChart.Series dataSeries1;

    // Add pie chart


    @FXML
    private void initialize() {
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty().asString());

        barChartTotalsPerCategory.setTitle("Totals per Category");
        dataSeries1 = new XYChart.Series();

        LOGGER.info("Initialised table with data from CSV");
    }

    // this works because the underlying datastructure of the dataDisplayTable
    // is the observable List with lineItems
    @FXML
    private void updateCategory(ActionEvent event) {
        LineItem selectedItem = dataDisplayTable.getSelectionModel().getSelectedItem();
        Category selectedCategory = (Category) categoryChoiceBox.getValue();

        if (selectedItem != null || selectedCategory != null) {
            selectedItem.setCategory(selectedCategory);
            LOGGER.info("Updated: {} ", selectedItem);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Valid Input");
            alert.setHeaderText("Nothing Selected");
            alert.setContentText("Please select an item from the table");
            alert.showAndWait();
        }
    }

    // TODO currently not implemented as would require change of ENUM to String
    @FXML
    private void addCategoryAndUpdateLineItem(ActionEvent event) {
        LineItem selectedItem = dataDisplayTable.getSelectionModel().getSelectedItem();

        if (newCategory.getText() != null && !newCategory.getText().trim().isEmpty()) {
            selectedItem.setCategory(Category.valueOf(newCategory.getText()));
            LOGGER.info("Updated lineItem with new Category: {} ", selectedItem.getCategory());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Valid Input");
            alert.setHeaderText("Nothing Entered");
            alert.setContentText("Please enter a letter, number or punctuation mark.");
            alert.showAndWait();
        }
    }

    @FXML
    private void resetAllCategories(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Reset");
        alert.setHeaderText("Resetting all Categories");
        alert.setContentText("Are you sure you want to reset all categories?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            for (LineItem lineItem : mainApp.getLineItems()) {
                lineItem.setCategory(Category.UNDEFINED);
                LOGGER.info("Reset all categories");
                dataSeries1.getData().clear();
            }
        }
    }

    @FXML
    private void showSumByCategories(ActionEvent event) {
        Map<Category, Double> sumByCategory = sumUpTotalAmountPerCategory();

        dataSeries1.setName("current year");
//        dataSeries1.getData().clear();

        sumByCategory.forEach((category, sum) -> {
            if (!category.equals(Category.UNDEFINED)) {
                dataSeries1.getData().add(new XYChart.Data(category.toString(), sum));
                LOGGER.info("sumByCategory: {}, {}", category.name(), sum);
            }
        });

        barChartTotalsPerCategory.getData().add(dataSeries1);
    }

    private Map<Category, Double> sumUpTotalAmountPerCategory() {
        List<LineItem> lineItems = mainApp.getLineItems();

        Collector<LineItem, ?, Double> summingAmount = Collectors.summingDouble(LineItem::getAmount);
        Map<Category, Double> sumByCategory = lineItems
                .stream()
                .collect(Collectors.groupingBy(LineItem::getCategory, summingAmount));

        return sumByCategory;
    }


}



