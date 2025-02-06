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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class LotteryController implements Initializable {

    @FXML
    private Label dataLabel;

    private List<String> names = new ArrayList<>();

    StringProperty name = new SimpleStringProperty();
    int count = 0;


    @FXML
    void congratulate(ActionEvent event) {
        name.setValue("Congratulations, " + name.getValue() + "!");
    }

    @FXML
    void removeCandidateAndRepeat() {
        names.removeLast();
        name.setValue(names.getFirst());
        count = 0;
        shuffle(names);

        playAnimation();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataLabel.textProperty().bind(name);

        getText();
        shuffle(names);
        playAnimation();


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


    public void getText() {

        List<String> data;

        Path path = Paths.get("src/main/resources/data", "participants.txt");
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Path doesn't exist");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/participants.txt"))) {

            data = reader.lines()
                    .map(it -> it.split(System.lineSeparator())[0])
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        names.addAll(data);

    }
}
