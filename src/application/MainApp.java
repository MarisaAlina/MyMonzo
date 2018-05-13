package application;

import application.view.MainAppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class MainApp extends Application {


	/** ============================THE PRIMARY STAGE and SCENES============================
	 * Basic Stage Settings: Title, disable to resize Stages
	 * rather than having all scenes in start method, I've written dedicated methods that load different scenes 
	 * controlled by individual controller classes in view package
	 */

	private Stage primaryStage;

	@Override 
	// is invoked by launch() from the main method
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MyMonzo");
		this.primaryStage.setResizable(false);

		//		this.showLogin();
		this.showMainScene();
	}

	public void showMainScene() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/MainAppView.fxml"));
			Parent root = loader.load();

			Scene mainScene = new Scene(root);
			mainScene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());

			//accessing respective controller for data exchange
			MainAppController mainCtrl = loader.getController();
			mainCtrl.setApp(this);

			primaryStage.setScene(mainScene);
			primaryStage.show();

		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/** enable to exit application */
	public void exitProgramme() {
		this.primaryStage.close(); 	
	}


	/**
	 * The main method of the Application class
	 * setups the program as JavaFX app
	 * launch() calls start
	 */
	public static void main(String[] args) {

		CSVParser csvParser = new CSVParser();
		csvParser.parseCSV();

		launch(args);

	}
}
