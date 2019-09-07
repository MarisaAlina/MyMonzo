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

public class MyMonzoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyMonzoController.class);

    // Imports the Main for main exchange to view
    private Main mainApp = null;

    public void setMainApp(Main main) {
        this.mainApp = main;

        categoryChoiceBox.setItems(FXCollections.observableArrayList(Arrays.asList(Category.values())));
        dataDisplayTable.setItems(mainApp.getLineItems());
    }

    @FXML
    private Button exitButton;

    public void exitProgramme(ActionEvent event) throws IOException {
        this.mainApp.exitProgramme();
    }

    @FXML
    private Button uploadNewFileButton;

    @FXML
    private Button addCategoryButton;

    @FXML
    private Button updateCategoryButton;

    @FXML
    private Button resetAllCategoriesButton;

    @FXML
    private Button sumUpAllCategories;


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
//
//    // needs labels
//    @FXML
//    private CategoryAxis xAxis;
//
//    @FXML
//    private CategoryAxis yAxis;

    String category1 = "Category1";
    int yValue = 100;


    @FXML
    private void initialize() {
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty().asString());

        barChartTotalsPerCategory.setTitle("Totals per Category");
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("current year");

        dataSeries1.getData().add(new XYChart.Data(category1, yValue));
        dataSeries1.getData().add(new XYChart.Data("Category2", 50));
        barChartTotalsPerCategory.getData().add(dataSeries1);

        LOGGER.info("Initialised table with data from CSV");
    }

    @FXML
    private void updateCategory(ActionEvent event) {
        LineItem selectedItem = dataDisplayTable.getSelectionModel().getSelectedItem();
        LOGGER.info("selected LineItem to update: {}", selectedItem);

        Category selectedCategory = (Category) categoryChoiceBox.getValue();

        // this works because the underlying datastructure of the dataDisplayTable
        // is the observable List with lineItems
        if (selectedItem != null || selectedCategory != null) {
            selectedItem.setCategory(selectedCategory);
            LOGGER.info("Updated lineItem {} with category: {} ", selectedItem.getCategory());
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
        LOGGER.info("selected LineItem to update: {}", selectedItem);

        if (newCategory.getText() != null && !newCategory.getText().trim().isEmpty()) {
            selectedItem.setCategory(Category.valueOf(newCategory.getText()));
            LOGGER.info("Updated lineItem with new Category: {}", selectedItem.getCategory());
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
                lineItem.setCategory(Category.TO_BE_DEFINED);
                LOGGER.info("Reset all categories");
            }
        }
    }

    @FXML
    private void sumUpAllCategories(ActionEvent event) {
        List<LineItem> lineItems = mainApp.getLineItems();

        Double sumPerCategory = lineItems
                .stream()
                .filter(lineItem -> lineItem.getCategory().equals("TFL"))
                .mapToDouble(lineItem -> lineItem.getAmount())
                .sum();

        LOGGER.info("Result sum: {}", sumPerCategory.toString());

        List<Category> categories = lineItems
                .stream()
                .map(LineItem::getCategory)
                .collect(Collectors.toList());

        LOGGER.info("All Categories: {} ", categories);
//        categories.forEach(category->LOGGER.info("Categories {} ", category.name()));

        Double total = lineItems
                .stream()
                .map(LineItem::getAmount)
                .reduce(0.0, ((subtotal, element) -> subtotal + element));


        LOGGER.info(total.toString());

        Collector<LineItem, ?, Double> summingAmount = Collectors.summingDouble(LineItem::getAmount);
        Map<Category, Double> sumByCategory = lineItems
                .stream()
                .collect(Collectors.groupingBy(LineItem::getCategory, summingAmount));

        sumByCategory.forEach((category, sum) -> LOGGER.info("sumByCategory: {}, {}", category.name(), sum));
    }


}



