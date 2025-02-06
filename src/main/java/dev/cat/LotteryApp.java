package dev.cat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class LotteryApp extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:src/main/resources/data-view.fxml"));
        BorderPane pane = loader.load();
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void switchScene() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:src/main/resources/randomizer.fxml"));
        BorderPane pane = loader.load();

        Scene sceneTwo = new Scene(pane);
        stage.setScene(sceneTwo);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}