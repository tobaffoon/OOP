package ru.nsu.amazyar.game_screen;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.nsu.amazyar.SceneDrawer;
import ru.nsu.amazyar.bases.AutoScalingStackPane;
import ru.nsu.amazyar.constants.InGameConstants;

public class GameScreenController implements Initializable {
    public enum TileStatus{
        EMPTY,
        SNAKE,
        BRICK,
        FOOD
    }
    private boolean gameActive = false;
    private List<List<TileStatus>> gridStatus;

    @FXML
    AutoScalingStackPane gamePane;
    @FXML
    Canvas gameBoard;
    @FXML
    TreeView<String> questView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void startNewGame(Stage stage, int rowCount, int columnCount, Color colorOne, Color colorTwo){
        if(gameActive){
            throw new IllegalStateException("New game cannot be started while there is an active game");
        }
        if(rowCount < InGameConstants.MIN_COLUMN_NUMBER || rowCount > InGameConstants.MAX_COLUMN_NUMBER){
            throw new IllegalArgumentException("ColumnCount is not in range (" + InGameConstants.MIN_COLUMN_NUMBER + ", " + InGameConstants.MAX_COLUMN_NUMBER + ")");
        }
        if(rowCount < InGameConstants.MIN_ROW_NUMBER || rowCount > InGameConstants.MAX_ROW_NUMBER){
            throw new IllegalArgumentException("RowCount is not in range (" + InGameConstants.MIN_ROW_NUMBER + ", " + InGameConstants.MAX_ROW_NUMBER + ")");
        }
        if(colorOne == null || colorTwo == null){
            throw new NullPointerException();
        }

        stage.setScene(gamePane.getScene());
        gameActive = true;
        SceneDrawer.drawGameGrid(gameBoard, rowCount, columnCount, colorOne, colorTwo);
    }
    @FXML
    public void onKeyPressed(KeyEvent keyEvent){

    }
}
