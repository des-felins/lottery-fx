package dev.cat;

import atlantafx.base.theme.Styles;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.kordamp.ikonli.fluentui.FluentUiFilledAL;
import org.kordamp.ikonli.javafx.FontIcon;

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

    @FXML
    void startLottery() throws Exception {

        if (!text.getText().isEmpty()) {
            LotteryApp.switchToLotteryScene(
                    text.getText().lines().collect(Collectors.toList()),
                    checkBox.isSelected());
        }

    }

    @FXML
    void goFullScreen() {
        LotteryApp.switchToFullScreenMode();
        fullScreenButton.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startButton.getStyleClass().addAll(Styles.LARGE, Styles.ROUNDED, Styles.ACCENT);
        fullScreenButton.setGraphic(new FontIcon(FluentUiFilledAL.FULL_SCREEN_ZOOM_24));
        fullScreenButton.getStyleClass().addAll(Styles.BUTTON_ICON, Styles.BUTTON_OUTLINED);
    }   
}
