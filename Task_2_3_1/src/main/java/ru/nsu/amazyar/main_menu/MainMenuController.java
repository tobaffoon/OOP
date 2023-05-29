package ru.nsu.amazyar.main_menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import ru.nsu.amazyar.leaderboard.LeaderboardManager;
import ru.nsu.amazyar.utils.ErrorAlerter;
import ru.nsu.amazyar.utils.SceneDrawer;

/**
 * Controller for VMC structure of the main menu.
 */
public class MainMenuController implements Initializable {

    @FXML
    Button playButton;
    @FXML
    Button leaderboardButton;
    @FXML
    Button tutorialButton;
    @FXML
    Button quitButton;
    Dialog<?> leaderBoardDialog;
    Dialog<?> tutorialDialog;
    private LeaderboardManager leaderboardManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.leaderboardManager = new LeaderboardManager();

        this.leaderBoardDialog = new Dialog<>();
        this.leaderBoardDialog.setHeaderText("LEADERBOARD");
        this.leaderBoardDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        this.tutorialDialog = new Dialog<>();
        this.tutorialDialog.setHeaderText("TUTORIAL");
        this.tutorialDialog.setContentText(
            " SNAKE moves on it's own\n Press ARROW KEYS to change direction\n Press P to pause\n Collect eggs to get bigger\n Repeat until WIN\n PS. Avoid BRICKS and your TAIL");
        this.tutorialDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
    }

    /**
     * Action on play button pressed.
     */
    @FXML
    public void onPlayPressed(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(SceneDrawer.getSettingsScene());
    }

    /**
     * Action on leaderboard button pressed.
     */
    @FXML
    public void onLeaderboardPressed() {
        try {
            leaderboardManager.loadLeaderboardFromFile();
            leaderBoardDialog.setContentText(leaderboardManager.getLeaderboard());
            leaderBoardDialog.show();
        } catch (IOException e) {
            ErrorAlerter.alert(e);
        }
    }

    /**
     * Action on tutorial button pressed.
     */
    @FXML
    public void onTutorialPressed() {
        tutorialDialog.show();
    }

    /**
     * Action on quit button pressed.
     */
    @FXML
    public void onQuitPressed(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
