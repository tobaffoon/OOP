package ru.nsu.amazyar.game_screen;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import ru.nsu.amazyar.bases.Direction;
import ru.nsu.amazyar.entities.Brick;
import ru.nsu.amazyar.entities.Entity;
import ru.nsu.amazyar.entities.MovableEntity;
import ru.nsu.amazyar.entities.food.SimpleEdible;
import ru.nsu.amazyar.entities.snake.Snake;

public class Game {
    private Snake playerSnake;
    private Direction playerDirectionBuffer;
    private final int rowCount;
    private final int columnCount;
    private final Entity[][] grid;
    private final int maxFoodNumber;
    private final int lengthGoal;
    private int foodNumber;
    private final int brickNumber;
    private boolean gameLost = false;
    private boolean gameWon = false;
    private final SimpleIntegerProperty score;

    public Game(int rows, int columns, int maxFoodNumber, int lengthGoal, int brickNumber) {
        this.rowCount = rows;
        this.columnCount = columns;
        grid = new Entity[columns][rows];
        this.maxFoodNumber = maxFoodNumber;
        this.lengthGoal = lengthGoal;
        this.brickNumber = brickNumber;
        this.score = new SimpleIntegerProperty();

        initializeInnerStructures();
    }

    private void initializeInnerStructures(){
        score.set(1);
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
    }

    private void emptyGrid(){
        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                grid[i][j] = null;
            }
        }
    }

    public void update(){
        // change players direction to last pressed arrow
        playerSnake.changeDirection(playerDirectionBuffer);

        recalculateGridStatus();
        getSnakesAsStream().forEach(MovableEntity::move);

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

    public int getScore() {
        return this.score.get();
    }

    public IntegerProperty getScoreProperty() {
        return this.score;
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
            grid[e.getPrevX()][e.getPrevY()] = null;

            Entity collidingEntity = grid[e.getNextX()][e.getNextY()];
            if(collidingEntity == null) {
                grid[e.getNextX()][e.getNextY()] = e;
            }
            else if(collidingEntity instanceof SimpleEdible){
                e.growTail();
                grid[e.getPrevX()][e.getPrevY()] = e;   // account for new tail
                grid[e.getNextX()][e.getNextY()] = e;   // account for new head
                score.set(score.get()+1);
                foodNumber--;
            }
            else if(collidingEntity instanceof Snake || collidingEntity instanceof Brick){
                gameLost = true;
            }
        });
    }

    public boolean isGameLost() {
        return gameLost;
    }
    public boolean isGameWon() {
        return gameWon;
    }
}
