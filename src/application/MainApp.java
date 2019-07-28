package application;

import application.view.MainAppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class MainApp extends Application {

	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) { 	// invoked by launch() from main method
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MyMonzo");
		this.primaryStage.setResizable(false);

		this.showMainScene();
		//		this.showLogin();
	}

	public void showMainScene() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/MainAppView.fxml"));
			Parent root = loader.load();

			Scene mainScene = new Scene(root);
			mainScene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());

			//accessing controller for data exchange
			MainAppController mainCtrl = loader.getController();
			mainCtrl.setApp(this);

			primaryStage.setScene(mainScene);
			primaryStage.show();

		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public void exitProgramme() {
		this.primaryStage.close(); 	
	}


	public static void main(String[] args) {

		CSVParser csvParser = new CSVParser();
		csvParser.parseCSV();

		// launch(args);

	}
}
