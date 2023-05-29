package ru.nsu.amazyar.game_screen;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import ru.nsu.amazyar.SnakeApplication;
import ru.nsu.amazyar.constants.GameSceneConstants;
import ru.nsu.amazyar.entities.Brick;
import ru.nsu.amazyar.entities.Entity;
import ru.nsu.amazyar.entities.food.SimpleEdible;
import ru.nsu.amazyar.entities.snake.Snake;
import ru.nsu.amazyar.entities.snake.SnakeLink;

/**
 * Painter of the snake game grid.
 */
public class GamePainter {

    private final Canvas drawingCanvas;
    private final Game game;
    private final Color gridColorOne;
    private final Color gridColorTwo;
    private final double cellWidth;
    private final double cellHeight;
    private final Image snakeHeadImage;
    private final Image snakeBodyImage;
    private final Image snakeTailImage;
    private final Image foodImage;
    private final Image brickImage;

    /**
     * Create new painter.
     *
     * @param game         game's model
     * @param canvas       canvas to draw game on
     * @param gridColorOne first color of the game's grid
     * @param gridColorTwo second color of the game's grid
     * @throws IOException if problem with opening sprites has occurred
     */
    public GamePainter(Game game, Canvas canvas, Color gridColorOne, Color gridColorTwo)
        throws IOException {
        this.game = game;
        this.drawingCanvas = canvas;
        this.gridColorOne = gridColorOne;
        this.gridColorTwo = gridColorTwo;
        try (InputStream snakeHeadStream = SnakeApplication.class.getResourceAsStream(
            GameSceneConstants.SNAKE_HEAD_SPRITE);
            InputStream snakeBodyStream = SnakeApplication.class.getResourceAsStream(
                GameSceneConstants.SNAKE_BODY_SPRITE);
            InputStream snakeTailStream = SnakeApplication.class.getResourceAsStream(
                GameSceneConstants.SNAKE_TAIL_SPRITE);
            InputStream foodStream = SnakeApplication.class.getResourceAsStream(
                GameSceneConstants.FOOD_SPRITE);
            InputStream brickStream = SnakeApplication.class.getResourceAsStream(
                GameSceneConstants.BRICK_SPRITE)) {
            if (snakeHeadStream == null) {
                throw new NullPointerException(
                    "Couldn't find " + GameSceneConstants.SNAKE_HEAD_SPRITE + " resource");
            }
            if (snakeBodyStream == null) {
                throw new NullPointerException(
                    "Couldn't find " + GameSceneConstants.SNAKE_BODY_SPRITE + " resource");
            }
            if (snakeTailStream == null) {
                throw new NullPointerException(
                    "Couldn't find " + GameSceneConstants.SNAKE_TAIL_SPRITE + " resource");
            }
            if (foodStream == null) {
                throw new NullPointerException(
                    "Couldn't find " + GameSceneConstants.FOOD_SPRITE + " resource");
            }
            if (brickStream == null) {
                throw new NullPointerException(
                    "Couldn't find " + GameSceneConstants.BRICK_SPRITE + " resource");
            }
            this.snakeHeadImage = new Image(snakeHeadStream);
            this.snakeBodyImage = new Image(snakeBodyStream);
            this.snakeTailImage = new Image(snakeTailStream);
            this.foodImage = new Image(foodStream);
            this.brickImage = new Image(brickStream);
        }

        this.cellHeight = drawingCanvas.getHeight() / game.getRowCount();
        this.cellWidth = drawingCanvas.getWidth() / game.getColumnCount();
    }

    /**
     * Draw the game in its current state.
     */
    public void draw() {
        this.drawGameGrid();
        this.drawEntities();
    }

    private void drawEntities() {
        game.getEntitiesAsStream().forEach((entity) -> {
            if (entity instanceof Snake) {
                drawSnake((Snake) entity);
            } else if (entity instanceof SimpleEdible){
                drawEntity(entity, foodImage);
            } else if (entity instanceof Brick){
                drawEntity(entity, brickImage);
            }
        });
    }


    private void drawSnake(Snake snake){
        Iterator<SnakeLink> snakeLinks = snake.getSnakeBody().iterator();
        SnakeLink tempLink = snakeLinks.next();

        // only head present
        if(!snakeLinks.hasNext()){
            drawEntity(tempLink, snakeHeadImage);
            return;
        }

        // draw tail
        drawEntity(tempLink, snakeTailImage);
        tempLink = snakeLinks.next();

        // draw body
        while(snakeLinks.hasNext()){
            drawEntity(tempLink, snakeBodyImage);
            tempLink = snakeLinks.next();
        }

        // draw head
        drawEntity(tempLink, snakeHeadImage);
    }

    private void drawEntity(Entity entity, Image sprite){
        double canvasx = entity.getX() * cellWidth;
        double canvasy = entity.getY() * cellHeight;

        GraphicsContext gc = drawingCanvas.getGraphicsContext2D();
        double vertInset = cellHeight * 0.1;
        double horInset = cellHeight * 0.1;
        gc.drawImage(sprite, canvasx+horInset, canvasy+vertInset, cellWidth-2*horInset, cellHeight-2*vertInset);
    }

    private void drawGameGrid(){
        GraphicsContext gc = drawingCanvas.getGraphicsContext2D();

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
