package application;

import application.view.LoginController;
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

//	public void showLogin() {
//		try {
//			// the loader class does all the file handling, you only need to give it the path to the file with the styles
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(getClass().getResource("view/LoginView.fxml"));
//			// Javafx models a scene like a tree/ graph structure: http://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm
//			Parent root = loader.load();
//			// instantiating the Login window as node from Parent root tree with the stylesheet
//			Scene loginScene = new Scene(root);
//			loginScene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());
//
//			//accessing respective controller for data exchange - imports class
//			LoginController lgCtr = loader.getController();
//			lgCtr.setApp(this);
//
//			//show Login scene
//			primaryStage.setScene(loginScene);
//			primaryStage.show();
//
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//	}

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
		launch(args);
	}
}
