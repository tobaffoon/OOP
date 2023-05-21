package ru.nsu.amazyar.game_screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import ru.nsu.amazyar.bases.CycleTimer;
import ru.nsu.amazyar.bases.Direction;
import ru.nsu.amazyar.constants.InGameConstants;
import ru.nsu.amazyar.entities.Entity;
import ru.nsu.amazyar.entities.MovableEntity;
import ru.nsu.amazyar.entities.food.SimpleEdible;
import ru.nsu.amazyar.entities.snake.Snake;

public class Game {
    private final CycleTimer gameLoopTimer;
    private final List<MovableEntity> movableEntities = new ArrayList<>();
    private final List<SimpleEdible> foodEntities = new ArrayList<>();
    private final Snake playerSnake;
    private Direction playerDirectionBuffer;
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
        playerSnake = new Snake(rows, columns);
        playerDirectionBuffer = playerSnake.getCurrentDirection();
        movableEntities.add(playerSnake);

        this.rowCount = rows;
        this.columnCount = columns;
        gridStatus = new TileStatus[columns][rows];
        initGridStatus();

        painter = new GamePainter(this, gameCanvas, gridColorOne, gridColorTwo);
        painter.draw();

        gameLoopTimer = new CycleTimer(InGameConstants.DEFAULT_NANOS_PER_TILE, () -> {update(); painter.draw();});
        gameLoopTimer.start();
    }

    private void initGridStatus(){
        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                gridStatus[i][j] = TileStatus.EMPTY;
            }
        }

        gridStatus[playerSnake.getX()][playerSnake.getY()] = TileStatus.SNAKE;
    }

    public void update(){
        playerSnake.changeDirection(playerDirectionBuffer);
        movableEntities.forEach(MovableEntity::move);
        tryGenerateFood();
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public List<MovableEntity> getMovableEntities(){
        return movableEntities;
    }

    public List<SimpleEdible> getFoodEntities(){
        return foodEntities;
    }

    public Snake getPlayerSnake() {
        return playerSnake;
    }

    public void changePlayerDirection(Direction direction){
        playerDirectionBuffer = direction;
    }

    private int emptyTiles(){
        int emptyTiles = 0;
        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                if(gridStatus[i][j] == TileStatus.EMPTY){
                    emptyTiles++;
                }
            }
        }
        return emptyTiles;
    }

    public void tryGenerateFood() {
        if(foodEntities.size() >= InGameConstants.DEFAULT_MAX_FOOD_NUMBER) {
            return;
        }

        foodEntities.add(new SimpleEdible(ThreadLocalRandom.current().nextInt(0, columnCount), ThreadLocalRandom.current().nextInt(0, rowCount), 1));
    }
}
