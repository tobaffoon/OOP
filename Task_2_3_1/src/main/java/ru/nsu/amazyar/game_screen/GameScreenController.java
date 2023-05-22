package ru.nsu.amazyar.game_screen;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    @FXML
    AutoScalingStackPane gamePane;
    @FXML
    Canvas gameBoard;
    @FXML
    TreeView<String> questView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void startNewGame(Stage stage, int rowCount, int columnCount, Color gridColorOne, Color gridColorTwo){
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
        game = new Game(gameBoard, rowCount, columnCount, gridColorOne, gridColorTwo);

        this.gameLoopTimer = new CycleTimer(InGameConstants.DEFAULT_NANOS_PER_TILE, this::step);
        gameLoopTimer.start();
    }

    public void playerChangeDirection(Direction direction){
        game.changePlayerDirection(direction);
    }

    public void step(){
        if(game.isGameLost()){
            stage.setScene(SceneDrawer.getLoseScene());
            gameLoopTimer.stop();
        }
        game.step();
    }

    public void debugInfo(){
        game.debugInfo();
    }
}
