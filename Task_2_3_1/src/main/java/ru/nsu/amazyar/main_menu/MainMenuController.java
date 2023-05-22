package ru.nsu.amazyar.main_menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.nsu.amazyar.SceneDrawer;
import ru.nsu.amazyar.SnakeApplication;
import ru.nsu.amazyar.game_screen.GameScreenController;

public class MainMenuController implements Initializable {
    Scene gameScene;
    GameScreenController gameScreenController;
    @FXML
    VBox buttonBox;
    @FXML
    Button playButton;
    @FXML
    Button leaderboardButton;
    @FXML
    Button tutorialButton;
    @FXML
    Button quitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader fxmlLoader =
            new FXMLLoader(SnakeApplication.class.getResource("fxmls/game_screen.fxml"));

        try {
            gameScene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            // TODO add dialog handler
            throw new RuntimeException(e);
        }

        gameScreenController = fxmlLoader.getController();
    }

    @FXML
    public void onPlayPressed(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        gameScreenController.startNewGame(stage, 10, 10, 30, 1, Color.BEIGE, Color.WHITE);
    }
    @FXML
    public void onLeaderboardPressed(ActionEvent event){

    }
    @FXML
    public void onTutorialPressed(ActionEvent event){

    }
    @FXML
    public void onQuitPressed(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
