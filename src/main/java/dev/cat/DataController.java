package dev.cat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class DataController {

    @FXML
    private TextArea text;

    DataHolder data = DataHolder.getInstance();

    @FXML
    void startLottery(ActionEvent event) throws Exception {

        data.setParticipants(text.getText());
        LotteryApp.switchScene();


    }

}
