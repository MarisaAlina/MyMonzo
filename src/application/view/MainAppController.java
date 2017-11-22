package application.view;

import java.io.IOException;

import application.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainAppController {

	/**
	 * ====MAINAPP REFERENCE====
	 */
	/** Imports the MainApp for data exchange. The app. Reset to 0 to launch when started */
	private MainApp app = null;

	public void setApp(MainApp input) {
		this.app = input;
	}
	
	/**
	 * ====FXML ELEMENTS====
	 */
	
	//Add FXML elements
	@FXML private Button uploadNewFileButton;
	@FXML private Button exitButton;
	
	/**
	 * ====EXIT====
	 */
	
	public void exitProgramme(ActionEvent event) throws IOException {
		this.app.exitProgramme();
	}
	
}
