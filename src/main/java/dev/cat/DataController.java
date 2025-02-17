package dev.cat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

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
    private Button scaleButton;

    private static final String IDLE_START_BUTTON = "-fx-background-color: #5BD5EF;";
    private static final String HOVERED_START_BUTTON = "-fx-background-color: #2FBEDD;";

    private static final String IDLE_SCALE_BUTTON = "-fx-background-color: #D5D8DD;";
    private static final String HOVERED_SCALE_BUTTON = "-fx-background-color: #8F949B;";


    @FXML
    void startLottery(ActionEvent event) throws Exception {

        if (!text.getText().isEmpty()) {
            LotteryApp.switchToLotteryScene(
                    text.getText().lines().collect(Collectors.toSet()),
                    checkBox.isSelected());
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startButton.setOnMouseEntered(e -> startButton.setStyle(HOVERED_START_BUTTON));
        startButton.setOnMouseExited(e -> startButton.setStyle(IDLE_START_BUTTON));

        scaleButton.setOnMouseEntered(e -> scaleButton.setStyle(HOVERED_SCALE_BUTTON));
        scaleButton.setOnMouseExited(e -> scaleButton.setStyle(IDLE_SCALE_BUTTON));

    }
}
