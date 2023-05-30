package ru.nsu.amazyar.game_screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import ru.nsu.amazyar.bases.Direction;
import ru.nsu.amazyar.bases.Vector2;
import ru.nsu.amazyar.entities.Brick;
import ru.nsu.amazyar.entities.Entity;
import ru.nsu.amazyar.entities.MovableEntity;
import ru.nsu.amazyar.entities.food.SimpleEdible;
import ru.nsu.amazyar.entities.snake.Snake;

/**
 * Model class for snake game.
 */
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
    private boolean gameLost;
    private boolean gameWon;
    private final SimpleIntegerProperty score;

    /**
     * Create new game.
     *
     * @param rows          number of rows of the grid
     * @param columns       number of columns of the grid
     * @param maxFoodNumber maximal number of food simultaneously present on the grid
     * @param lengthGoal    snake's length that counts as the game's main goal
     * @param brickNumber   number of bricks in the grid
     */
    public Game(int rows, int columns, int maxFoodNumber, int lengthGoal, int brickNumber) {
        this.rowCount = rows;
        this.columnCount = columns;
        this.grid = new Entity[columns][rows];
        this.maxFoodNumber = maxFoodNumber;
        this.lengthGoal = lengthGoal;
        this.brickNumber = brickNumber;
        this.score = new SimpleIntegerProperty();

        restart();
    }

    private void initializeInnerStructures() {
        generateBricks();

        score.set(1);
        playerSnake = new Snake(0, 0, rowCount, columnCount, Direction.DOWN);
        playerDirectionBuffer = playerSnake.getCurrentDirection();
        addEntity(playerSnake);

        foodNumber = 0;
        generateFood();
    }

    private void generateBricks() {
        for (int i = 0; i < brickNumber; i++) {
            int brickx = ThreadLocalRandom.current().nextInt(0, columnCount);
            int lower_y = brickx >= 3 ? 0 : 3;    // don't spawn bricks in 3x3 area near the start
            int bricky = ThreadLocalRandom.current().nextInt(lower_y, rowCount);

            addEntity(new Brick(brickx, bricky));
        }

    }

    /**
     * Starts new game with the parameters passed in constructor.
     */
    public void restart() {
        gameLost = false;
        gameWon = false;
        emptyGrid();
        initializeInnerStructures();
    }

    private void emptyGrid() {
        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                grid[i][j] = null;
            }
        }
    }

    /**
     * Main method - advance game's state.
     */
    public void update() {
        // change players direction to last pressed arrow
        playerSnake.changeDirection(playerDirectionBuffer);

        recalculateGridStatus();
        getSnakesAsStream().forEach(MovableEntity::move);

        generateFood();
    }

    /**
     * Returns number of rows in the grid.
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Returns number of columns in the grid.
     */
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * Returns current score.
     */
    public int getScore() {
        return this.score.get();
    }

    /**
     * Returns current score's property.
     */
    public IntegerProperty getScoreProperty() {
        return this.score;
    }

    /**
     * Attempts to change player's snake's direction. Doesn't change it if the change isn't allowed
     * by {@link MovableEntity}
     */
    public void changePlayerDirection(Direction direction) {
        if (playerSnake.changeDirectionAllowed(direction)) {
            playerDirectionBuffer = direction;
        }
    }

    private List<Vector2> emptyTilesCoordinates() {
        List<Vector2> emptyTiles = new ArrayList<>();

        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                if(grid[i][j] == null){
                    emptyTiles.add(new Vector2(i, j));
                }
            }
        }

        return emptyTiles;
    }

    private void addEntity(Entity entity) {
        grid[entity.getX()][entity.getY()] = entity;
    }

    /**
     * Returns stream of all entities in the game.
     */
    public Stream<Entity> getEntitiesAsStream() {
        return Arrays.stream(grid).flatMap(Arrays::stream).filter(Objects::nonNull);
    }

    /**
     * Returns stream of all snakes in the game.
     */
    public Stream<Snake> getSnakesAsStream() {
        return getEntitiesAsStream().filter((e) -> e instanceof Snake).map((e) -> (Snake) e)
            .distinct();
    }

    /**
     * Returns game's goal as final length.
     */
    public int getLengthGoal() {
        return lengthGoal;
    }

    private void generateFood() {
        List<Vector2> emptyTilesCoordinates = emptyTilesCoordinates();
        if (foodNumber >= Math.min(emptyTilesCoordinates.size(), maxFoodNumber)) {
            return;
        }

        Vector2 foodPos = emptyTilesCoordinates.get(
            ThreadLocalRandom.current().nextInt(0, emptyTilesCoordinates.size()));

        foodNumber++;
        addEntity(new SimpleEdible(foodPos));
    }

    private void recalculateGridStatus() {
        getSnakesAsStream().forEach((e) -> {
            // if entity moved from a tile, a tile is empty now
            grid[e.getPrevX()][e.getPrevY()] = null;

            Entity collidingEntity = grid[e.getNextX()][e.getNextY()];
            if (collidingEntity == null) {
                grid[e.getNextX()][e.getNextY()] = e;
            } else if (collidingEntity instanceof SimpleEdible) {
                e.growTail();
                grid[e.getPrevX()][e.getPrevY()] = e;   // account for new tail
                grid[e.getNextX()][e.getNextY()] = e;   // account for new head
                score.set(score.get() + 1);
                if (score.get() >= lengthGoal) {
                    gameWon = true;
                }
                foodNumber--;
            } else if (collidingEntity instanceof Snake || collidingEntity instanceof Brick) {
                gameLost = true;
            }
        });
    }

    /**
     * Check if game is lost.
     */
    public boolean isGameLost() {
        return gameLost;
    }

    /**
     * Check if game is won.
     */
    public boolean isGameWon() {
        return gameWon;
    }
}
