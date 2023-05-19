package ru.nsu.amazyar.game_screen;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import ru.nsu.amazyar.SceneDrawer;
import ru.nsu.amazyar.bases.AutoScalingStackPane;

public class GameScreenController implements Initializable {
    @FXML
    AutoScalingStackPane gamePane;
    @FXML
    Canvas gameBoard;
    @FXML
    TreeView<String> questView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SceneDrawer.drawGameGrid(gameBoard, 20, 20, Color.RED, Color.BLACK);
//        gameBoard.setMaxHeight(Double.MAX_VALUE);
//        gameBoard.setMaxWidth(Double.MAX_VALUE);
    }

    @FXML
    public void onKeyPressed(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.P){
            GraphicsContext gc = gameBoard.getGraphicsContext2D();
            gc.setFill(Color.valueOf("rgba(100,0,100,0.1)"));
            gc.fillRect(gameBoard.getLayoutX(), gameBoard.getLayoutY(), gameBoard.getWidth(), gameBoard.getHeight());
        }
    }
}
