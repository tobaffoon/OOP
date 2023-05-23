package ru.nsu.amazyar.game_screen;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.nsu.amazyar.SceneDrawer;
import ru.nsu.amazyar.bases.AutoScalingStackPane;
import ru.nsu.amazyar.bases.CycleTimer;
import ru.nsu.amazyar.bases.Direction;
import ru.nsu.amazyar.constants.InGameConstants;

public class GameScreenController implements Initializable {
    private boolean gameActive = false;
    private Game game;
    private Stage stage;
    private CycleTimer gameLoopTimer;
    private boolean gamePaused;

    @FXML
    AutoScalingStackPane gamePane;
    @FXML
    Canvas gameBoard;
    @FXML
    VBox loseBox;
    @FXML
    Label scoreLabel;
    @FXML
    Button replayButton;
    @FXML
    Button mainMenuButton;
    @FXML
    Label pauseLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void startNewGame(Stage stage, int rowCount, int columnCount, int maxFoodNumber, int lengthGoal, int speed, Color gridColorOne, Color gridColorTwo){
        if(gameActive){
            throw new IllegalStateException("New game cannot be started while there is an active game");
        }
        if(columnCount < InGameConstants.MIN_COLUMN_NUMBER || columnCount > InGameConstants.MAX_COLUMN_NUMBER){
            throw new IllegalArgumentException("ColumnCount is not in range (" + InGameConstants.MIN_COLUMN_NUMBER + ", " + InGameConstants.MAX_COLUMN_NUMBER + ")");
        }
        if(rowCount < InGameConstants.MIN_ROW_NUMBER || rowCount > InGameConstants.MAX_ROW_NUMBER){
            throw new IllegalArgumentException("RowCount is not in range (" + InGameConstants.MIN_ROW_NUMBER + ", " + InGameConstants.MAX_ROW_NUMBER + ")");
        }
        if(gridColorOne == null || gridColorTwo == null){
            throw new NullPointerException();
        }
        this.stage = stage;
        stage.setScene(gamePane.getScene());

        gamePane.getScene().setOnKeyPressed(new ControlHandler(this));
        gameActive = true;
        game = new Game(gameBoard, rowCount, columnCount, maxFoodNumber, lengthGoal, gridColorOne, gridColorTwo);

        this.gameLoopTimer = new CycleTimer(InGameConstants.DEFAULT_NANOS_PER_TILE / speed, this::step);
        gameLoopTimer.start();
        gamePaused = false;
    }

    public void playerChangeDirection(Direction direction){
        game.changePlayerDirection(direction);
    }

    public void pauseAndUnpause(){
        if(gamePaused) {
            pauseLabel.setVisible(false);
            gameLoopTimer.turnOn();
            gamePaused = false;
        }
        else {
            pauseLabel.setVisible(true);
            gameLoopTimer.turnOff();
            gamePaused = true;
        }
    }

    public void step(){
        game.update();
        if(game.isGameLost()){
            loseBox.setVisible(true);
            scoreLabel.setText("SCORE: " + game.getPlayerSnake().getLength() + "/" + game.getLengthGoal());
            gameLoopTimer.stop();
            gameActive = false;
        }
        game.draw();
    }

    public void onReplayButtonPressed(){
        game.restart();
        loseBox.setVisible(false);
        gameLoopTimer = new CycleTimer(gameLoopTimer.getNanosInterval(), this::step);
        gameLoopTimer.start();
    }

    public void onMainMenuButtonPressed(){
        stage.setScene(SceneDrawer.getMainScene());
    }
}
