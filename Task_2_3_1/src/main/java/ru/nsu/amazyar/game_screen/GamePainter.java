package ru.nsu.amazyar.game_screen;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.amazyar.SceneDrawer;

public class GamePainter {
    private final Canvas drawingCanvas;
    private Game game;
    private Color gridColorOne;
    private Color gridColorTwo;

    public GamePainter(Game game, Canvas canvas, Color gridColorOne, Color gridColorTwo) {
        this.game = game;
        this.drawingCanvas = canvas;
        this.gridColorOne = gridColorOne;
        this.gridColorTwo = gridColorTwo;
    }

    public void draw(){
        this.drawGameGrid();
    }

    private void drawGameGrid(){

        GraphicsContext gc = drawingCanvas.getGraphicsContext2D();
        double cellHeight = drawingCanvas.getHeight() / game.getRowCount();
        double cellWidth = drawingCanvas.getWidth() / game.getColumnCount();

        // Draw the cells of the grid
        for (int row = 0; row < game.getRowCount(); row++) {
            for (int col = 0; col < game.getColumnCount(); col++) {
                // Alternate the fill color of the cells to create a checked pattern
                if ((row + col) % 2 == 0) {
                    gc.setFill(gridColorOne);
                } else {
                    gc.setFill(gridColorTwo);
                }

                gc.fillRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
            }
        }
    }
}
