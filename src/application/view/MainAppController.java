package application.view;

import java.io.IOException;

import application.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainAppController {


    // Imports the MainApp for data exchange to View
    private MainApp app;

    public void setApp(MainApp input) {
        this.app = input;
    }

    @FXML
    private Button uploadNewFileButton;
    @FXML
    private Button exitButton;


    public void exitProgramme(ActionEvent event) throws IOException {
        this.app.exitProgramme();
    }

}
