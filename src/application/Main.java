package application;

import application.model.CSVParser;
import application.model.Category;
import application.model.LineItem;
import application.controller.MyMonzoController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.util.Arrays;
import java.util.List;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

public class Main extends Application {

    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private Stage primaryStage;

    private MyMonzoController myMonzoController;

    List<LineItem> categorizedLineItems;

    private static String PATH;

    public static String getPATH() {
        return PATH;
    }

    public static void setPATH(String PATH) {
        Main.PATH = PATH;
    }

    private ObservableList<LineItem> itemsObservableList = FXCollections.observableArrayList();

    public ObservableList<LineItem> getLineItems() {
        return itemsObservableList;
    }

    @Override
    public void start(Stage primaryStage) {    // invoked by launch() from main method

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MyMonzo");
        this.primaryStage.setResizable(false);

        CSVParser csvParser = new CSVParser();
        List<LineItem> lineItemsFromCSV = csvParser.parseCSV(getPATH());
        categorizedLineItems = csvParser.processLineItems(lineItemsFromCSV);

        for (LineItem lineItem : categorizedLineItems) {
            LOGGER.info("processedCSVFile: {}\nAdding item to observableList for display", lineItem);
            itemsObservableList.add(lineItem);
        }

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


    public static void main(String[] args) {
        if (args.length == 0) {
            LOGGER.info("Please provide file location");
            System.exit(0);
        }
        setPATH(args[0]);
        launch(args);
    }
}
