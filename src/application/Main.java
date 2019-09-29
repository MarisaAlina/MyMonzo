package application;

import application.controller.MyMonzoController;
import application.model.CSVParser;
import application.model.LineItem;
import application.model.XLSParser;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private MyMonzoController myMonzoController;

    List<LineItem> categorizedLineItems;

    public List<LineItem> getCategorizedLineItems() {
        return categorizedLineItems;
    }

    private static String PATH;

    public static String getPATH() {
        return PATH;
    }

    public static void setPATH(String PATH) {
        Main.PATH = PATH;
    }

    private ObservableList<LineItem> itemsObservableList;

    public ObservableList<LineItem> getLineItems() {
        return itemsObservableList;
    }

    @Override
    public void start(Stage primaryStage) {    // invoked by launch() from main method

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MyMonzo");
        this.primaryStage.setResizable(false);

        loadCSVData(getPATH());

        this.showMyMonzoScene();
    }

    public void showMyMonzoScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/MyMonzoView.fxml"));
            Parent root = loader.load();

            Scene mainScene = new Scene(root);
            mainScene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());

            myMonzoController = loader.getController();
            myMonzoController.setMainApp(this);

            primaryStage.setScene(mainScene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exitProgramme() {
        this.primaryStage.close();
    }

    public void loadCSVData(String PATH) {
        setPATH(PATH);
        LOGGER.info("CSV file path: {}", PATH);

        CSVParser csvParser = new CSVParser();
        List<LineItem> lineItemsFromCSV = csvParser.parseCSV(PATH);

        // TODO currently not working as expected
        categorizedLineItems = csvParser.processLineItems(lineItemsFromCSV);

        itemsObservableList = FXCollections.observableArrayList();

        for (LineItem lineItem : categorizedLineItems) {
            itemsObservableList.add(lineItem);
        }

        LOGGER.info("Added categorized LineItems to observableList for display");
    }

    public void loadXLSXData(String PATH) {
        setPATH(PATH);
        LOGGER.info("XLSX file path: {}", PATH);

        itemsObservableList.clear();

        XLSParser xlsParser = new XLSParser();
        List<LineItem> lineItemsFromXLS = xlsParser.parseXLS(PATH);

        for (LineItem lineItem : lineItemsFromXLS) {
            itemsObservableList.add(lineItem);
        }

        LOGGER.info("Added LineItems from XLSX to new observableList for display");
    }


    public static void main(String[] args) {
        if (args.length == 0) {
            LOGGER.info("Please provide file location");
            System.exit(0);
        }
        setPATH(args[0]);
        launch(args);
    }
}
