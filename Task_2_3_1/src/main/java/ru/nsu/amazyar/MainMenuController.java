package ru.nsu.amazyar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainMenuController {
    @FXML
    Label myLabel;

    @FXML
    Button startButton;

    @FXML
    public void onStartPressed(ActionEvent ae){
        myLabel.setText("Just");
    }
}
