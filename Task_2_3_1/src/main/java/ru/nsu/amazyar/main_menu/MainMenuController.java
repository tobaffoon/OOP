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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import ru.nsu.amazyar.SceneDrawer;
import ru.nsu.amazyar.SnakeApplication;
import ru.nsu.amazyar.game_screen.GameScreenController;
import ru.nsu.amazyar.leaderboard.LeaderboardManager;

public class MainMenuController implements Initializable {
    Scene gameScene;
    GameScreenController gameScreenController;
    @FXML
    Button playButton;
    @FXML
    Button leaderboardButton;
    @FXML
    Button tutorialButton;
    @FXML
    Button quitButton;
    Dialog<?> leaderBoardDialog;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.leaderBoardDialog = new Dialog<>();
        this.leaderBoardDialog.setHeaderText("LEADERBOARD");
        this.leaderBoardDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

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
        stage.setScene(SceneDrawer.getSettingsScene());
    }
    @FXML
    public void onLeaderboardPressed(ActionEvent event) {
        LeaderboardManager manager = new LeaderboardManager();
        manager.loadLeaderboardFromFile();
        leaderBoardDialog.setContentText(manager.getLeaderboard());
        leaderBoardDialog.show();
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
