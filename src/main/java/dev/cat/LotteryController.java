package dev.cat;

import javafx.animation.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class LotteryController implements Initializable {

    @FXML
    private Label dataLabel;

    @FXML
    private Button presentButton;

    @FXML
    private Button repeatButton;

    private List<String> names = new ArrayList<>();
    private List<String> selectedNames = new ArrayList<>();

    StringProperty name = new SimpleStringProperty();
    int count = 0;

    private boolean needToMaskEmails;

    private static final String LABEL_FOR_WINNER = "-fx-text-fill: #56A458;";

    private static final String IDLE_REPEAT_BUTTON = "-fx-background-color: #C961B3;";
    private static final String HOVERED_REPEAT_BUTTON = "-fx-background-color: #994187;";

    private static final String IDLE_PRESENT_BUTTON = "-fx-background-color: #85D888;";
    private static final String HOVERED_PRESENT_BUTTON = "-fx-background-color: #56A458;";


    @FXML
    void congratulate(ActionEvent event) {
        name.setValue("Congratulations, " + name.getValue() + "!");
        addAnimationToLabel();

    }

    private void addAnimationToLabel() {
        dataLabel.setStyle(LABEL_FOR_WINNER);

        ScaleTransition scale = new ScaleTransition(Duration.seconds(1), dataLabel);
        scale.setByX(0.5);
        scale.setByY(0.5);
        scale.setCycleCount(4);
        scale.setAutoReverse(true);
        scale.play();
    }

    @FXML
    void removeCandidateAndRepeat() {
        name.setValue(names.getFirst());
        names.removeLast();
        count = 0;
        selectedNames.clear();
        shuffleAndDisplayNames();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataLabel.textProperty().bind(name);

        presentButton.setOnMouseEntered(e -> presentButton.setStyle(HOVERED_PRESENT_BUTTON));
        presentButton.setOnMouseExited(e -> presentButton.setStyle(IDLE_PRESENT_BUTTON));

        repeatButton.setOnMouseEntered(e -> repeatButton.setStyle(HOVERED_REPEAT_BUTTON));
        repeatButton.setOnMouseExited(e -> repeatButton.setStyle(IDLE_REPEAT_BUTTON));

    }

    public void shuffleAndDisplayNames() {
        shuffle(names);

        if (names.size() > 50) {
            for (int i = names.size() - 50; i < names.size(); i++) {
                selectedNames.add(names.get(i));
            }
        } else {
            selectedNames.addAll(names);
        }

        if (needToMaskEmails) {
            int i = 0;
            for (String email : selectedNames) {
                selectedNames.set(i, email.replaceAll(email.substring(
                        email.indexOf('@')), "*****"));
                i++;
            }
        }

        playAnimation();
    }


    private void playAnimation() {

        Interpolator.EASE_OUT.interpolate(100, 1000, 0.98);


//        KeyValue x = new KeyValue(circle.translateXProperty(), 200, Interpolator.DISCRETE);
//        KeyFrame frame = new KeyFrame(Duration.seconds(5), x);
//        Timeline timeline = new Timeline(frame);


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.03),
                e -> getNextName()));
        timeline.setCycleCount(selectedNames.size());
        //timeline.setCycleCount(50);
        //timeline.setCycleCount(selectedNames.size() * 3);
        timeline.play();
    }

    private void getNextName() {
        if (count >= selectedNames.size()) {
            count = 0;
        }
        name.setValue(selectedNames.get(count));
        count++;
    }

    private void shuffle(List<String> names) {
        Collections.shuffle(names);
    }

    public void extractData(Set<String> list, boolean needMasking) {
        names.addAll(list);
        needToMaskEmails = needMasking;
    }
}
