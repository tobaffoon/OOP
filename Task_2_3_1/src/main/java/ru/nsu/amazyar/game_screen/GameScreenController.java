package ru.nsu.amazyar.game_screen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.nsu.amazyar.bases.AutoScalingStackPane;
import ru.nsu.amazyar.bases.CycleTimer;
import ru.nsu.amazyar.bases.Direction;
import ru.nsu.amazyar.constants.GameSceneConstants;
import ru.nsu.amazyar.constants.InGameConstants;
import ru.nsu.amazyar.leaderboard.LeaderboardEntry;
import ru.nsu.amazyar.leaderboard.LeaderboardManager;
import ru.nsu.amazyar.utils.ErrorAlerter;
import ru.nsu.amazyar.utils.SceneDrawer;

/**
 * Controller for VMC structure of the game.
 */
public class GameScreenController implements Initializable {

    private boolean gameActive;
    private Game game;
    private Stage stage;
    private CycleTimer gameLoopTimer;
    private boolean gamePaused;

    @FXML
    AutoScalingStackPane gamePane;
    @FXML
    Canvas gameBoard;
    @FXML
    VBox gameResultBox;
    @FXML
    Label resultLabel;
    @FXML
    Label finalScoreLabel;
    @FXML
    Button replayButton;
    @FXML
    Button mainMenuButton;
    @FXML
    Button leaderboardButton;
    @FXML
    VBox pauseBox;
    @FXML
    Label currentScoreLabel;

    private GamePainter painter;
    private TextInputDialog leaderboardDialog;
    private static final LeaderboardManager leaderboardManager = new LeaderboardManager();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameActive = false;
        try {
            leaderboardManager.loadLeaderboardFromFile();
        } catch (IOException ignored) { // couldn't find a file -> it will be created

        }

        leaderboardDialog = new TextInputDialog();
        leaderboardDialog.setTitle("Game Over");
        leaderboardDialog.setHeaderText("Enter your name:");
    }

    private void inputNameToLeaderboard(String name){
        LeaderboardEntry newEntry = new LeaderboardEntry(name, game.getScore());
        try {
            leaderboardManager.addEntry(newEntry);

            // Show alert that entry was added
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("LEADERBOARD");
            alert.setHeaderText(null);
            alert.setContentText("Score added to leaderboard.");
            alert.showAndWait();
        } catch (IOException e) {
            ErrorAlerter.alert(e);
        }
    }

    /**
     * Create new {@link Game} model and connected {@link GamePainter}.
     *
     * @param stage         main window. Used to set the game's scene
     * @param rowCount      see {@link Game}
     * @param columnCount   see {@link Game}
     * @param maxFoodNumber see {@link Game}
     * @param lengthGoal    see {@link Game}
     * @param brickNumber   see {@link Game}
     * @param gridColorOne  see {@link Game}
     * @param gridColorTwo  see {@link Game}
     * @param speed         speed at which the game works. Each frame takes
     *                      InGameConstants.DEFAULT_NANOS_PER_TILE / speed nanoseconds
     */
    public void startNewGame(Stage stage, int rowCount, int columnCount, int maxFoodNumber,
        int lengthGoal, int brickNumber, Color gridColorOne, Color gridColorTwo, int speed) {
        if (gameActive) {
            throw new IllegalStateException(
                "New game cannot be started while there is an active game");
        }
        if (columnCount < InGameConstants.MIN_COLUMN_NUMBER
            || columnCount > InGameConstants.MAX_COLUMN_NUMBER) {
            throw new IllegalArgumentException(
                "ColumnCount is not in range (" + InGameConstants.MIN_COLUMN_NUMBER + ", "
                    + InGameConstants.MAX_COLUMN_NUMBER + ")");
        }
        if (rowCount < InGameConstants.MIN_ROW_NUMBER
            || rowCount > InGameConstants.MAX_ROW_NUMBER) {
            throw new IllegalArgumentException(
                "RowCount is not in range (" + InGameConstants.MIN_ROW_NUMBER + ", "
                    + InGameConstants.MAX_ROW_NUMBER + ")");
        }
        if (gridColorOne == null || gridColorTwo == null) {
            throw new NullPointerException();
        }
        // create game model
        game = new Game(rowCount, columnCount, maxFoodNumber, lengthGoal, brickNumber);
        gameActive = true;

        // configure stage and scene
        this.stage = stage;
        stage.setScene(gamePane.getScene());
        gamePane.getScene().setOnKeyPressed(new ControlHandler(this));
        game.getScoreProperty()
            .addListener((b, o, n) -> currentScoreLabel.setText(n + "/" + lengthGoal));
        currentScoreLabel.setText(game.getScore() + "/" + lengthGoal);

        // create painter
        try {
            painter = new GamePainter(game, gameBoard, gridColorOne, gridColorTwo);
            painter.draw();
        } catch (Exception e){      // if painter couldn't be initialized, no game can proceed
            ErrorAlerter.alert(e);
            stage.close();
        }

        // create game cycle timer
        this.gameLoopTimer =
            new CycleTimer(InGameConstants.DEFAULT_NANOS_PER_TILE / speed, this::step);
        gameLoopTimer.start();
        gamePaused = false;
    }

    /**
     * See {@link Game}.
     */
    public void playerChangeDirection(Direction direction) {
        game.changePlayerDirection(direction);
    }

    /**
     * Pauses or unpauses the game.
     */
    public void pauseAndUnpause() {
        if (gamePaused) {
            pauseBox.setVisible(false);
            gameLoopTimer.start();
            gamePaused = false;
        } else {
            pauseBox.setVisible(true);
            gameLoopTimer.stop();
            gamePaused = true;
        }
    }

    /**
     * Updates game's model and redraws it accordingly.
     */
    public void step() {
        game.update();
        if (game.isGameLost()) {
            // Show lose screen
            resultLabel.setText("YOU LOSE");
            resultLabel.setTextFill(GameSceneConstants.DEFAULT_LOSE_LABEL_COLOR);
            gameResultBox.setVisible(true);
            finalScoreLabel.setText("SCORE: " + game.getScore() + "/" + game.getLengthGoal());

            gameLoopTimer.stop();
            gameActive = false;
        } else if(game.isGameWon()){
            // Show win screen
            gameResultBox.setVisible(true);
            resultLabel.setText("YOU WON");
            resultLabel.setTextFill(GameSceneConstants.DEFAULT_WIN_LABEL_COLOR);
            finalScoreLabel.setText("SCORE: " + game.getScore() + "/" + game.getLengthGoal());

            gameLoopTimer.stop();
            gameActive = false;
        }
        painter.draw();
    }

    /**
     * Starts new game with old parameters. Resets timer and redraws canvas accordingly.
     */
    public void restartGame() {
        game.restart();
        painter.draw();
        gameLoopTimer = new CycleTimer(gameLoopTimer.getNanosInterval(), this::step);
        gameLoopTimer.start();
    }

    /**
     * Action on restart button in pause pressed.
     */
    @FXML
    public void onRestartButtonPressed() {
        pauseBox.setVisible(false);
        restartGame();
    }

    /**
     * Action on replay button pressed.
     */
    @FXML
    public void onReplayButtonPressed() {
        gameResultBox.setVisible(false);
        restartGame();
    }

    /**
     * Action on add entry to leaderboard button pressed.
     */
    @FXML
    public void onLeaderboardButtonPressed() {
        leaderboardDialog.showAndWait().ifPresent(this::inputNameToLeaderboard);
    }

    /**
     * Action on back to main menu button pressed.
     */
    @FXML
    public void onMainMenuButtonPressed(){
        stage.setScene(SceneDrawer.getMainScene());
    }
}
