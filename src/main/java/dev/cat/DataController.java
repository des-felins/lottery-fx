package dev.cat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataController {

    @FXML
    private TextArea text;

    @FXML
    void startLottery(ActionEvent event) throws Exception {

        String input = text.getText();
        writeToFile(input);
        LotteryApp.switchScene();


    }

    private void writeToFile(String input) throws IOException {


        Path path = Paths.get("src/main/resources/data", "participants.txt");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        try (Writer fileWriter = new FileWriter("src/main/resources/data/participants.txt")) {
            fileWriter.write(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


}
