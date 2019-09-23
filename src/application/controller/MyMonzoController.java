package application.controller;

import application.Main;
import application.model.CSVWriter;
import application.model.Category;
import application.model.LineItem;
import application.model.XLSWriter;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

// JavaFX uses fx:id and annotation-based initialization.
// onAction to identify method (same name)

public class MyMonzoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyMonzoController.class);

    // Link to Main for data exchange with fxml elements
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
    private BarChart barChart;

    @FXML
    private PieChart pieChart;

    XYChart.Series dataSeries1;


    @FXML
    private void initialize() {
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty().asString());
        LOGGER.info("Initialised table with data from CSV");

        barChart.setTitle("Totals per Category");
        pieChart.setTitle("Category of Total");
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
                pieChart.getData().clear();
            }
        }
    }

    @FXML
    private void showSumByCategories(ActionEvent event) {
        Map<Category, Double> sumByCategory = sumUpTotalAmountPerCategory();

        if (dataSeries1 == null) {
            dataSeries1 = new XYChart.Series();
            LOGGER.info("Initiliazed new dataseries: {}", dataSeries1);
            // can cause labels to squeeze to the left
        }

        if (dataSeries1.getData() != null && pieChart.getData() != null) {
            LOGGER.info("Clearing data from charts");
            dataSeries1.getData().clear();
            pieChart.getData().clear();
            barChart.getData().clear();
        }

        dataSeries1.setName("current year");

        sumByCategory.forEach((category, sum) -> {
            if (!category.equals(Category.UNDEFINED)) {
                dataSeries1.getData().add(new XYChart.Data(category.toString(), sum));
                pieChart.getData().add(new PieChart.Data(category.toString(), sum));
                LOGGER.info("sumByCategory: {}, {} \ndataseries: {} ", category.name(), sum, dataSeries1);
            }
        });

        barChart.getData().add(dataSeries1);
    }

    @FXML
    private void exportAsXLSX(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XLSX (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extensionFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        // end if no file is created
        if (file == null) {
            return;
        }
        // ensure correct extension is added
        if (!file.getPath().endsWith(".xlsx")) {
            file = new File(file.getPath() + ".xlsx");
        }

        try {
            XLSWriter.exportToXLS(mainApp.getCategorizedLineItems());
        } catch (Exception e) {
            showFileSaveErrorAlert(file);
            e.printStackTrace();
        }

        LOGGER.info("Successfully created XLSX file: {}", file.getPath());
    }

    @FXML
    private void importXLSX(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XLSX files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file == null) {
            return;
        }

        try {
            mainApp.loadCSVData(file.getPath());
        } catch (Exception e) {
            showFileImportErrorAlert(file);
            e.printStackTrace();
        }
    }


    @FXML
    private void exportAsCSV(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        if (file == null) {
            return;
        }

        if (!file.getPath().endsWith(".csv")) {
            file = new File(file.getPath() + ".csv");
        }

        String csv = CSVWriter.createCSVString(mainApp.getCategorizedLineItems());

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));

            bw.write(csv);
            bw.flush();
            bw.close();

        } catch (IOException e) {
            showFileSaveErrorAlert(file);
            e.printStackTrace();
        }

        LOGGER.info("Successfully written to file {}", file.getPath());
    }

    @FXML
    private void importCSV(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file == null) {
            return;
        }

        try {
            mainApp.loadCSVData(file.getPath());
        } catch (Exception e) {
            showFileImportErrorAlert(file);
            e.printStackTrace();
        }
    }


    private Map<Category, Double> sumUpTotalAmountPerCategory() {
        List<LineItem> lineItems = mainApp.getLineItems();

        Collector<LineItem, ?, Double> summingAmount = Collectors.summingDouble(LineItem::getAmount);
        Map<Category, Double> sumByCategory = lineItems
                .stream()
                .collect(Collectors.groupingBy(LineItem::getCategory, summingAmount));

        return sumByCategory;
    }

    private void showFileSaveErrorAlert(File file) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Could not save data");
        alert.setContentText("Could not save data to file:\n" + file.getPath());
        alert.showAndWait();
    }

    private void showFileImportErrorAlert(File file) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Could not load data");
        alert.setContentText("Could not load data from file:\n" + file.getPath());
        alert.showAndWait();
    }


}



