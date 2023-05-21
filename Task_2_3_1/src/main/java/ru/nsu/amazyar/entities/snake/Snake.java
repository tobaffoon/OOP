package ru.nsu.amazyar.entities.snake;

import java.util.ArrayDeque;
import java.util.Queue;
import ru.nsu.amazyar.bases.Direction;
import ru.nsu.amazyar.constants.InGameConstants;
import ru.nsu.amazyar.entities.MovableEntity;
import ru.nsu.amazyar.entities.SimpleMovableEntity;

public class Snake extends SimpleMovableEntity {
    private SnakeLink head;
    private final Queue<SnakeLink> snakeBody = new ArrayDeque<>();
    private boolean growNextStep = false;

    public Snake(int x, int y, int gridRowCount, int gridColumnCount, Direction initialDirection) {
        super(x, y, gridRowCount, gridColumnCount, initialDirection);
        this.head = new SnakeLink(x, y);
        snakeBody.add(head);
    }

    public Queue<SnakeLink> getSnakeBody() {
        return snakeBody;
    }

    public SnakeLink getHead() {
        return head;
    }

    public void growTail() {
        growNextStep = true;
    }

    @Override
    public void move() {
        if(growNextStep){
            growNextStep = false;
        }
        else{
            snakeBody.poll();   // delete tail
        }

        switch (getCurrentDirection()) {
            case UP:
                setY(Math.floorMod(getY() - 1, maxRow));
                break;
            case LEFT:
                setX(Math.floorMod(getX() - 1, maxColumn));
                break;
            case DOWN:
                setY(Math.floorMod(getY() + 1, maxRow));
                break;
            case RIGHT:
                setX(Math.floorMod(getX() + 1, maxColumn));
                break;
        }

        this.head = new SnakeLink(getX(), getY());
        this.snakeBody.add(this.head);
    }
}
