package application;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

public class MainTest extends ApplicationTest {

    private Button exitButton;

    @Override
    public void start (Stage stage) throws Exception {
//        Parent myMonzo = FXMLLoader.load(Main.class.getResource("MyMonzoView.fxml"));
//        stage.setScene(new Scene(myMonzo));
//        Scene myMonzoStartup = new Scene(new DesktopPane(), 800, 600);
        stage.show();
        stage.toFront();
    }

    @After
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void whenClickingExitButton() {
        FxAssert.verifyThat(".exitButton", LabeledMatchers.hasText("exit"));
    }


}