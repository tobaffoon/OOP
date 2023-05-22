package ru.nsu.amazyar.game_screen;

import java.util.Iterator;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import ru.nsu.amazyar.SnakeApplication;
import ru.nsu.amazyar.constants.GameSceneConstants;
import ru.nsu.amazyar.constants.InGameConstants;
import ru.nsu.amazyar.entities.Entity;
import ru.nsu.amazyar.entities.MovableEntity;
import ru.nsu.amazyar.entities.food.SimpleEdible;
import ru.nsu.amazyar.entities.snake.Snake;
import ru.nsu.amazyar.entities.snake.SnakeLink;

public class GamePainter {
    private final Canvas drawingCanvas;
    private Game game;
    private Color gridColorOne;
    private Color gridColorTwo;
    private final double cellWidth;
    private final double cellHeight;
    private final Image snakeHeadImage;
    private final Image snakeBodyImage;
    private final Image snakeTailImage;
    private final Image foodImage;

    public GamePainter(Game game, Canvas canvas, Color gridColorOne, Color gridColorTwo){
        this.game = game;
        this.drawingCanvas = canvas;
        this.gridColorOne = gridColorOne;
        this.gridColorTwo = gridColorTwo;
        this.snakeHeadImage = new Image(
                SnakeApplication.class.getResourceAsStream(GameSceneConstants.SNAKE_HEAD_SPRITE));
        this.snakeBodyImage = new Image(
                SnakeApplication.class.getResourceAsStream(GameSceneConstants.SNAKE_BODY_SPRITE));
        this.snakeTailImage = new Image(
                SnakeApplication.class.getResourceAsStream(GameSceneConstants.SNAKE_TAIL_SPRITE));
        this.foodImage = new Image(
                SnakeApplication.class.getResourceAsStream(GameSceneConstants.FOOD_SPRITE));

        this.cellHeight = drawingCanvas.getHeight() / game.getRowCount();
        this.cellWidth = drawingCanvas.getWidth() / game.getColumnCount();
    }

    public void draw(){
        this.drawGameGrid();
        this.drawEntities();
    }

    private void drawEntities(){
        game.getEntitiesAsStream().forEach((entity) -> {
            if (entity instanceof Snake){
                drawSnake((Snake) entity);
            }
            else if (entity instanceof SimpleEdible){
                drawEntity(entity, foodImage);
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

        // draw body
        while(snakeLinks.hasNext()){
            tempLink = snakeLinks.next();
            drawEntity(tempLink, snakeBodyImage);
        }

        drawEntity(tempLink, snakeHeadImage);
    }

    private void drawEntity(Entity entity, Image sprite){
        double canvasx = entity.getX() * cellWidth;
        double canvasy = entity.getY() * cellHeight;

        GraphicsContext gc = drawingCanvas.getGraphicsContext2D();
        gc.drawImage(sprite, canvasx+2.5, canvasy+2.5, cellWidth-5, cellHeight-5);
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
