package ru.nsu.amazyar.game_screen;

import java.util.ArrayList;
import java.util.List;
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

        playerSnake = new Snake(rows, columns);
        entities.add(playerSnake);

        painter = new GamePainter(this, gameCanvas, gridColorOne, gridColorTwo);
        painter.draw();

        gameLoopTimer = new CycleTimer(2, () -> {update(); painter.draw();});
        gameLoopTimer.start();
    }

    public void update(){
        entities.forEach(Entity::move);
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public List<Entity> getEntities(){
        return entities;
    }
}
