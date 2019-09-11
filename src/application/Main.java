package application;

import application.controller.MyMonzoController;
import application.model.CSVParser;
import application.model.LineItem;
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

    private ObservableList<LineItem> itemsObservableList = FXCollections.observableArrayList();

    public ObservableList<LineItem> getLineItems() {
        return itemsObservableList;
    }

    @Override
    public void start(Stage primaryStage) {    // invoked by launch() from main method

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MyMonzo");
        this.primaryStage.setResizable(false);

        loadData(getPATH());

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

    public void loadData(String PATH) {
        setPATH(PATH);
        LOGGER.info("File Path: {}", PATH);

        CSVParser csvParser = new CSVParser();
        List<LineItem> lineItemsFromCSV = csvParser.parseCSV(PATH);

        // TODO currently not working as expected
        categorizedLineItems = csvParser.processLineItems(lineItemsFromCSV);

        for (LineItem lineItem : categorizedLineItems) {
            itemsObservableList.add(lineItem);
        }

        LOGGER.info("Added categorized LineItems to observableList for display");
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
