package ru.nsu.amazyar.game_screen;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
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
    private final Snake playerSnake;
    private Direction playerDirectionBuffer;
    private final GamePainter painter;
    private final int rowCount;
    private final int columnCount;
    private final Entity[][] grid;
    private int foodNumber;

    public Game(Canvas gameCanvas, int rows, int columns, Color gridColorOne, Color gridColorTwo) {
        if(rows == 0 || columns == 0){
            throw new IllegalArgumentException("Can't create empty grid");
        }
        if(gridColorOne == null || gridColorTwo == null){
            throw new NullPointerException("Not enough colors provided");
        }
        this.rowCount = rows;
        this.columnCount = columns;
        grid = new Entity[columns][rows];

        playerSnake = new Snake(0, 0, rows, columns, Direction.DOWN);
        playerDirectionBuffer = playerSnake.getCurrentDirection();
        addEntity(playerSnake);

        foodNumber = 0;
        generateFood();

        painter = new GamePainter(this, gameCanvas, gridColorOne, gridColorTwo);
        painter.draw();

        gameLoopTimer = new CycleTimer(InGameConstants.DEFAULT_NANOS_PER_TILE, () -> {update(); painter.draw();});
        gameLoopTimer.start();
    }

    public void update(){
        // change players direction to last pressed arrow
        playerSnake.changeDirection(playerDirectionBuffer);

        getMovableEntitiesAsStream().forEach(MovableEntity::move);

        generateFood();
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
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
                if(grid[i][j] == null){
                    emptyTiles++;
                }
            }
        }
        return emptyTiles;
    }

    public boolean addEntity(Entity entity){
        if (getEntityAt(entity.getX(), entity.getY()) != null){
            return false;
        }

        grid[entity.getX()][entity.getY()] = entity;
        return true;
    }

    public Entity getEntityAt(int x, int y){
        return grid[x][y];
    }

    public Stream<Entity> getEntitiesAsStream(){
        return Arrays.stream(grid).flatMap(Arrays::stream).filter(Objects::nonNull);
    }

    public Stream<MovableEntity> getMovableEntitiesAsStream(){
        return getEntitiesAsStream().filter((cell) -> cell instanceof MovableEntity).map((e) -> (MovableEntity) e);
    }

    public void generateFood() {
        if(foodNumber >= InGameConstants.DEFAULT_MAX_FOOD_NUMBER) {
            return;
        }

        // try to place food until right spot is found
        // TODO replace pure randomness for fastness
        int foodx;
        int foody;
        do {
            foodx = ThreadLocalRandom.current().nextInt(0, columnCount);
            foody = ThreadLocalRandom.current().nextInt(0, rowCount);
        }while (grid[foodx][foody] != null);

        foodNumber++;
        grid[foodx][foody] = new SimpleEdible(foodx, foody, 1);
    }

    public void recalculateGridStatus(){
        getMovableEntitiesAsStream().forEach((e) -> {
            // if entity moved from a tile, a tile is empty now
            grid[e.getPrevx()][e.getPrevy()] = null;

            if(e instanceof Snake){
                Entity collidingEntity = grid[e.getX()][e.getY()];
                if(collidingEntity == null) {
                    grid[e.getX()][e.getY()] = (Snake) e;
                }
                else if(collidingEntity instanceof SimpleEdible){
                    ((Snake) e).growTail();
                    grid[e.getX()][e.getY()] = (Snake) e;
                    foodNumber--;
                }
            }
        });
    }
}
