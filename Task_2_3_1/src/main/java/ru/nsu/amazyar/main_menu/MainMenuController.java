package ru.nsu.amazyar.main_menu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.nsu.amazyar.SceneDrawer;
import ru.nsu.amazyar.constants.MainMenuConstants;

public class MainMenuController implements Initializable {
    private Scene gameScene;
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
        gameScene = SceneDrawer.getGameScene();
    }

    @FXML
    public void onPlayPressed(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(gameScene);
    }
    @FXML
    public void onLeaderboardPressed(ActionEvent event){

    }
    @FXML
    public void onTutorialPressed(ActionEvent event){

    }
    @FXML
    public void onQuitPressed(ActionEvent event){

    }
}
