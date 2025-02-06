package dev.cat;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class LotteryController implements Initializable {

    @FXML
    private Label dataLabel;

    private List<String> names = new ArrayList<>();

    DataHolder dataHolder = DataHolder.getInstance();

    StringProperty name = new SimpleStringProperty();
    int count = 0;


    @FXML
    void congratulate(ActionEvent event) {
        name.setValue("Congratulations, " + name.getValue() + "!");
    }

    @FXML
    void removeCandidateAndRepeat() {
        name.setValue(names.getFirst());
        names.removeLast();
        count = 0;
        shuffle(names);

        playAnimation();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataLabel.textProperty().bind(name);

        getData();
        shuffle(names);
        playAnimation();


    }

    private void getData() {
        List<String> data = Arrays.stream(
                        dataHolder.getParticipants()
                                .split(System.lineSeparator()))
                .toList();

        names.addAll(data);
    }

    private void playAnimation() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), e -> getNewName()));
        timeline.setCycleCount(names.size());
        timeline.play();
    }

    private void getNewName() {
        name.setValue(names.get(count));
        count++;
    }

    private void shuffle(List<String> names) {
        Collections.shuffle(names);
    }

}
