package dev.cat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.stream.Collectors;

public class DataController {

    @FXML
    private TextArea text;

    @FXML
    void startLottery(ActionEvent event) throws Exception {
        if (!text.getText().isEmpty()) {
            LotteryApp.switchToLotteryScene(text.getText().lines().collect(Collectors.toSet()));
        }

    }

}
