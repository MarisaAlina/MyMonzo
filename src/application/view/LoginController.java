package application.view;

import java.io.IOException;

import application.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

public class LoginController {

	/** The app. Reset to 0 to launch when started */
	private MainApp app = null;

	public void setApp(MainApp input) {
		this.app = input;
	}

	@FXML private PasswordField passwordTextField;
	@FXML private Button loginButton;

	@FXML
	public void performLogin (ActionEvent event) throws IOException {

		String password = "Banking3000";
		String passwordInput = passwordTextField.getText();

		if (password.equals(passwordInput)) {
			this.app.showMainScene();
			passwordTextField.clear();
		} else {
			passwordTextField.clear();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Status: NOT CLEARED");
			alert.setContentText("You are not allowed to access this app. A report will be sent to the admin.");
			alert.showAndWait();
		}	
	}

}
