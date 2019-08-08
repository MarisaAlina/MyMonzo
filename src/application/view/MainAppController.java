package application.view;

import java.io.IOException;

import application.CSVParser;
import application.MainApp;
import application.model.LineItem;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainAppController {

    // Imports the MainApp for data exchange to View
    private MainApp app;

    public void setApp(MainApp input) {
        this.app = input;
    }

    public void exitProgramme(ActionEvent event) throws IOException {
        this.app.exitProgramme();
    }

    @FXML
    private Button uploadNewFileButton;

    @FXML
    private Button exitButton;

    @FXML
    private TableView<LineItem> csvDataDisplayTable;

    @FXML
    private TableColumn<LineItem, ObservableValue<String>> dateColumn;

    @FXML
    private TableColumn<LineItem, String> descriptionColumn;
    // need getters and setter

    @FXML
    private TableColumn<LineItem, Double> valueColumn;

    @FXML
    private TableColumn<LineItem, String> categoryColumn;

    @FXML
    private void showCSVData() {
    }

    private final ObservableList<LineItem> lineItems = FXCollections.observableArrayList();

    private CSVParser csvParser;


    @FXML
    private void initialize() {
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDate());


    }


}
