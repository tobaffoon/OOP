package ru.nsu.amazyar.game_screen;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import ru.nsu.amazyar.bases.Direction;
import ru.nsu.amazyar.entities.Brick;
import ru.nsu.amazyar.entities.Entity;
import ru.nsu.amazyar.entities.MovableEntity;
import ru.nsu.amazyar.entities.food.SimpleEdible;
import ru.nsu.amazyar.entities.snake.Snake;

public class Game {
    private Snake playerSnake;
    private Direction playerDirectionBuffer;
    private final GamePainter painter;
    private final int rowCount;
    private final int columnCount;
    private final Entity[][] grid;
    private final int maxFoodNumber;
    private final int lengthGoal;
    private int foodNumber;
    private final int brickNumber;
    private boolean gameLost = false;
    private boolean gameWon = false;

    public Game(Canvas gameCanvas, int rows, int columns, int maxFoodNumber, int lengthGoal, int brickNumber, Color gridColorOne, Color gridColorTwo) {
        if(rows == 0 || columns == 0){
            throw new IllegalArgumentException("Can't create empty grid");
        }
        if(gridColorOne == null || gridColorTwo == null){
            throw new NullPointerException("Not enough colors provided");
        }
        this.rowCount = rows;
        this.columnCount = columns;
        grid = new Entity[columns][rows];
        this.maxFoodNumber = maxFoodNumber;
        this.lengthGoal = lengthGoal;
        this.brickNumber = brickNumber;

        initializeInnerStructures();
        painter = new GamePainter(this, gameCanvas, gridColorOne, gridColorTwo);
        painter.draw();
    }

    private void initializeInnerStructures(){
        playerSnake = new Snake(0, 0, rowCount, columnCount, Direction.DOWN);
        playerDirectionBuffer = playerSnake.getCurrentDirection();
        addEntity(playerSnake);

        generateBricks();

        foodNumber = 0;
        generateFood();
    }

    private void generateBricks(){
        for (int i = 0; i < brickNumber; i++) {
            int brickx;
            int bricky;
            do {
                brickx = ThreadLocalRandom.current().nextInt(0, columnCount);
                int lowery = brickx>=3?0:3;
                bricky = ThreadLocalRandom.current().nextInt(lowery, rowCount);
            }while (grid[brickx][bricky] != null);

            addEntity(new Brick(brickx, bricky));
        }

    }

    public void restart(){
        gameLost = false;
        gameWon = false;
        emptyGrid();
        initializeInnerStructures();
        painter.draw();
    }

    private void emptyGrid(){
        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                grid[i][j] = null;
            }
        }
    }

    public void draw(){
        painter.draw();
    }

    public void update(){
        // change players direction to last pressed arrow
        playerSnake.changeDirection(playerDirectionBuffer);

        getSnakesAsStream().forEach(MovableEntity::move);
        recalculateGridStatus();

        if(playerSnake.getLength() >= lengthGoal){
            gameWon = true;
        }

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
        if(playerSnake.changeDirectionAllowed(direction))
            playerDirectionBuffer = direction;
    }

    private int emptyTilesCount(){
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

    public Stream<Snake> getSnakesAsStream(){
        return getEntitiesAsStream().filter((e) -> e instanceof Snake).map((e) -> (Snake) e).distinct();
    }

    public int getLengthGoal() {
        return lengthGoal;
    }

    public void generateFood() {
        if(foodNumber >= Math.min(emptyTilesCount(), maxFoodNumber)) {
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
        addEntity(new SimpleEdible(foodx, foody, 1));
    }

    public void recalculateGridStatus(){
        getSnakesAsStream().forEach((e) -> {
            // if entity moved from a tile, a tile is empty now
            grid[e.getPrevx()][e.getPrevy()] = null;

            Entity collidingEntity = grid[e.getX()][e.getY()];
            if(collidingEntity == null) {
                grid[e.getX()][e.getY()] = e;
            }
            else if(collidingEntity instanceof SimpleEdible){
                ((Snake) e).growTail();
                grid[e.getX()][e.getY()] = e;
                foodNumber--;
            }
            else if(collidingEntity instanceof Snake || collidingEntity instanceof Brick){
                gameLost = true;
            }
        });
    }

    public int getMaxFoodNumber() {
        return maxFoodNumber;
    }

    public boolean isGameLost() {
        return gameLost;
    }
    public boolean isGameWon() {
        return gameWon;
    }
}
