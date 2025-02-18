package dev.cat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DataController implements Initializable {

    @FXML
    private TextArea text;

    @FXML
    private CheckBox checkBox;

    @FXML
    private Button startButton;

    @FXML
    private Button fullScreenButton;

    private static final String IDLE_START_BUTTON = "-fx-background-color: #5BD5EF;";
    private static final String HOVERED_START_BUTTON = "-fx-background-color: #2FBEDD;";

    private static final String IDLE_FULL_SCREEN_BUTTON = "-fx-background-color: #D5D8DD;";
    private static final String HOVERED_FULL_SCREEN_BUTTON = "-fx-background-color: #8F949B;";


    @FXML
    void startLottery(ActionEvent event) throws Exception {

        if (!text.getText().isEmpty()) {
            LotteryApp.switchToLotteryScene(
                    text.getText().lines().collect(Collectors.toSet()),
                    checkBox.isSelected());
        }

    }

    @FXML
    void goFullScreen() {
        LotteryApp.switchToFullScreenMode();
//        Stage stage = (Stage) fullScreenButton.getScene().getWindow();
//        stage.setFullScreen(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startButton.setOnMouseEntered(e -> startButton.setStyle(HOVERED_START_BUTTON));
        startButton.setOnMouseExited(e -> startButton.setStyle(IDLE_START_BUTTON));

        fullScreenButton.setOnMouseEntered(e -> fullScreenButton.setStyle(HOVERED_FULL_SCREEN_BUTTON));
        fullScreenButton.setOnMouseExited(e -> fullScreenButton.setStyle(IDLE_FULL_SCREEN_BUTTON));

    }
}
