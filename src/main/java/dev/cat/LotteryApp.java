package dev.cat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class LotteryApp extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:src/main/resources/data-view.fxml"));
        VBox vbox = loader.load();

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void switchScene() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:src/main/resources/randomizer.fxml"));
        VBox vbox = loader.load();

        Scene sceneTwo = new Scene(vbox);
        stage.setScene(sceneTwo);
        stage.show();

    }

}