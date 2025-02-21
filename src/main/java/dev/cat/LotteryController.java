package dev.cat;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class LotteryController implements Initializable {

    public static final int POTENTIAL_WINNERS_LIST = 300;
    public static final int TARGET_DURATION_MS = 2000;
    @FXML
    private Label dataLabel;

    @FXML
    private Label happyLabelLeft;

    @FXML
    private Label happyLabelCenter;

    @FXML
    private Label happyLabelRight;

    @FXML
    private Button presentButton;

    @FXML
    private Button repeatButton;

    private Set<String> names = new HashSet<>();

    StringProperty name = new SimpleStringProperty();

    private static final String LABEL_FOR_WINNER = "-fx-text-fill:  #C961B3;";

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

        Image confetti = new Image(String.valueOf(LotteryController.class
                .getClassLoader()
                .getResource("confetti.gif")));
        ImageView viewLeft = new ImageView(confetti);
        ImageView viewCenter = new ImageView(confetti);
        ImageView viewRight = new ImageView(confetti);


        happyLabelLeft.setGraphic(viewLeft);
        happyLabelLeft.setAlignment(Pos.TOP_LEFT);

        happyLabelCenter.setGraphic(viewCenter);
        happyLabelCenter.setAlignment(Pos.TOP_CENTER);

        happyLabelRight.setGraphic(viewRight);
        happyLabelRight.setAlignment(Pos.TOP_RIGHT);


        ScaleTransition scale = new ScaleTransition(Duration.seconds(1), dataLabel);
        scale.setByX(0.5);
        scale.setByY(0.5);
        scale.setCycleCount(4);
        scale.setAutoReverse(true);
        scale.play();
    }

    @FXML
    void removeCandidateAndRepeat() {
        names.remove(name.getValue());
        shuffleAndDisplayNames();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataLabel.textProperty().bind(name);

        presentButton.setOnMouseEntered(_ -> presentButton.setStyle(HOVERED_PRESENT_BUTTON));
        presentButton.setOnMouseExited(_ -> presentButton.setStyle(IDLE_PRESENT_BUTTON));

        repeatButton.setOnMouseEntered(_ -> repeatButton.setStyle(HOVERED_REPEAT_BUTTON));
        repeatButton.setOnMouseExited(_ -> repeatButton.setStyle(IDLE_REPEAT_BUTTON));

    }

    public void shuffleAndDisplayNames() {
        List<String> intermediateNameHolder = new ArrayList<>(names);
        shuffle(intermediateNameHolder);

        while (intermediateNameHolder.size() < POTENTIAL_WINNERS_LIST) {
            List<String> tmp = new ArrayList<>(intermediateNameHolder);
            shuffle(tmp);
            intermediateNameHolder.addAll(tmp);
        }

        if (intermediateNameHolder.size() > POTENTIAL_WINNERS_LIST) {
            intermediateNameHolder = intermediateNameHolder.subList(0, POTENTIAL_WINNERS_LIST);
        }

        playAnimation(intermediateNameHolder);
    }


    private void playAnimation(List<String> shuffledNames) {

        double totalDuration = 0.0;

        Timeline timeline = new Timeline();

        Interpolator interpolator = new Interpolator() {
            @Override
            protected double curve(double t) {
                return Math.sin((t * Math.PI) / 2);
            }
        };

        for (int i = 0; i < shuffledNames.size(); i++) {

            double dur = interpolator.interpolate(1, 1000, (double) i / shuffledNames.size());
            totalDuration += dur;
            var index = i;
            KeyFrame frame = new KeyFrame(Duration.millis(totalDuration), _ -> name.setValue(shuffledNames.get(index)));
            timeline.getKeyFrames().add(frame);
            if (totalDuration > TARGET_DURATION_MS) {
                break;
            }
        }

        timeline.play();
    }


    private void shuffle(List<String> names) {
        Collections.shuffle(names);
    }

    public void extractData(List<String> list, boolean needMasking) {

        if (needMasking) {
            int i = 0;
            for (String email : list) {
                list.set(i, email.replaceAll(email.substring(
                        email.indexOf('@')), "*****"));
                i++;
            }
        }

        names.addAll(list);
    }
}
