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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class LotteryController implements Initializable {

    public static final int POTENTIAL_WINNERS_LIST = 300;
    public static final int TARGET_DURATION_MS = 15000;
    @FXML
    private Label dataLabel;

    @FXML
    private Button presentButton;

    @FXML
    private ImageView topImage;

    @FXML
    private ImageView bottomImage;

    @FXML
    private Button repeatButton;

    @FXML
    private Button fsButton;

    @FXML
    private Button exitButton;

    private Set<String> names = new HashSet<>();

    StringProperty name = new SimpleStringProperty();

    private static final String LABEL_FOR_WINNER = "-fx-text-fill:  #C961B3;";

    private static final String IDLE_REPEAT_BUTTON = "-fx-background-color: #C961B3;";
    private static final String HOVERED_REPEAT_BUTTON = "-fx-background-color: #994187;";

    private static final String IDLE_PRESENT_BUTTON = "-fx-background-color: #85D888;";
    private static final String HOVERED_PRESENT_BUTTON = "-fx-background-color: #56A458;";

    private static final String HOVERED_FS_BUTTON = "-fx-background-color:  #1E1E1E;";
    private static final String IDLE_FS_BUTTON = "-fx-background-color: #2C2C2C;";

    private static final String IDLE_EXIT_BUTTON = "-fx-background-color:  #7F38D8;";
    private static final String HOVERED_EXIT_BUTTON = "-fx-background-color: #63329F;";


    @FXML
    void congratulate(ActionEvent event) {
        name.setValue("Congratulations, " + name.getValue() + "!");
        addAnimationToLabel();
        presentButton.setVisible(false);
        repeatButton.setVisible(false);

    }

    private void addAnimationToLabel() {
        dataLabel.setStyle(LABEL_FOR_WINNER);

        Image fireworks = new Image(String.valueOf(LotteryController.class
                .getClassLoader()
                .getResource("fireworks1.gif")));
        topImage.setImage(fireworks);
        topImage.setFitHeight(250);
        topImage.setPreserveRatio(true);
        bottomImage.setImage(fireworks);
        bottomImage.setFitHeight(250);
        bottomImage.setPreserveRatio(true);


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

        presentButton.setOnMouseEntered(e -> presentButton.setStyle(HOVERED_PRESENT_BUTTON));
        presentButton.setOnMouseExited(e -> presentButton.setStyle(IDLE_PRESENT_BUTTON));

        repeatButton.setOnMouseEntered(e -> repeatButton.setStyle(HOVERED_REPEAT_BUTTON));
        repeatButton.setOnMouseExited(e -> repeatButton.setStyle(IDLE_REPEAT_BUTTON));


        fsButton.setOnMouseEntered(e -> fsButton.setStyle(HOVERED_FS_BUTTON));
        fsButton.setOnMouseExited(e -> fsButton.setStyle(IDLE_FS_BUTTON));

        exitButton.setOnMouseEntered(e_ -> exitButton.setStyle(HOVERED_EXIT_BUTTON));
        exitButton.setOnMouseExited(e -> exitButton.setStyle(IDLE_EXIT_BUTTON));

        setFullScreenGraphicsAndAction();

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
            KeyFrame frame = new KeyFrame(Duration.millis(totalDuration), e -> name.setValue(shuffledNames.get(index)));
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

    @FXML
    void goFullScreen() {
        LotteryApp.switchToFullScreenMode();
        setWindowedGraphicsAndAction();
    }

    public void goWindowed() {
        LotteryApp.switchToWindowedMode();
        setFullScreenGraphicsAndAction();

    }

    void setFullScreenGraphicsAndAction() {

        Image imageFullScreen = new Image(String.valueOf(DataController.class
                .getClassLoader()
                .getResource("fs.png")));
        ImageView view = new ImageView(imageFullScreen);
        view.setFitHeight(15);
        view.setPreserveRatio(true);
        fsButton.setGraphic(view);
        fsButton.setPrefSize(15, 15);

        fsButton.setOnAction(e -> goFullScreen());
    }

    void setWindowedGraphicsAndAction() {

        Image imageWindowed = new Image(String.valueOf(DataController.class
                .getClassLoader()
                .getResource("fs_reverse.png")));
        ImageView view = new ImageView(imageWindowed);
        view.setFitHeight(15);
        view.setPreserveRatio(true);
        fsButton.setGraphic(view);
        fsButton.setPrefSize(15, 15);

        fsButton.setOnAction(e -> goWindowed());
    }

    @FXML
    public void quitApp(ActionEvent event) {
        LotteryApp.exit();
    }

}
