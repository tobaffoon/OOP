package ru.nsu.amazyar.game_screen;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import ru.nsu.amazyar.bases.CycleTimer;
import ru.nsu.amazyar.entities.Entity;
import ru.nsu.amazyar.entities.snake.Snake;

public class Game {
    private final CycleTimer gameLoopTimer;
    private final List<Entity> entities = new ArrayList<>();
    private final Snake playerSnake;
    private final GamePainter painter;
    private final int rowCount;
    private final int columnCount;
    private final TileStatus[][] gridStatus;

    public Game(Canvas gameCanvas, int rows, int columns, Color gridColorOne, Color gridColorTwo) {
        if(rows == 0 || columns == 0){
            throw new IllegalArgumentException("Can't create empty grid");
        }
        if(gridColorOne == null || gridColorTwo == null){
            throw new NullPointerException("Not enough colors provided");
        }

        this.rowCount = rows;
        this.columnCount = columns;
        gridStatus = new TileStatus[rows][columns];

        gameLoopTimer = new CycleTimer(2, () -> {update(); draw();});
        painter = new GamePainter(this, gameCanvas, gridColorOne, gridColorTwo);
        playerSnake = new Snake();
        entities.add(playerSnake);

        painter.draw();
        gameLoopTimer.start();
    }

    public void update(){

    }

    public void draw(){
        painter.draw();
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }
}
