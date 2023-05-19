package ru.nsu.amazyar;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SceneDrawer {
    static public Scene getMainScene(){
        return getSceneFromFxml("fxmls/main_menu.fxml");
    }

    static public Scene getGameScene(){
        return getSceneFromFxml("fxmls/game_screen.fxml");
    }

    static private Scene getSceneFromFxml(String path){
        FXMLLoader fxmlLoader =
            new FXMLLoader(SnakeApplication.class.getResource(path));

        Scene scene;

        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            // TODO add dialog handler
            throw new RuntimeException(e);
        }

        return scene;
    }

    public static void drawGameGrid(Canvas canvas, int rows, int columns, Color color1, Color color2){
        if(rows == 0 || columns == 0){
            throw new IllegalArgumentException("Can't create empty grid");
        }
        if(color1 == null || color2 == null){
            throw new NullPointerException("Not enough colors provided");
        }

        GraphicsContext gc = canvas.getGraphicsContext2D();
        double cellHeight = canvas.getHeight() / rows;
        double cellWidth = canvas.getWidth() / columns;

        // Draw the cells of the grid
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                // Alternate the fill color of the cells to create a checked pattern
                if ((row + col) % 2 == 0) {
                    gc.setFill(color1);
                } else {
                    gc.setFill(color2);
                }

                gc.fillRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
            }
        }
    }
}
