package ru.nsu.amazyar.entities.snake;

import java.util.ArrayDeque;
import java.util.Queue;
import ru.nsu.amazyar.bases.Direction;
import ru.nsu.amazyar.entities.SimpleMovableEntity;

public class Snake extends SimpleMovableEntity {
    private int prevx;
    private int prevy;
    private SnakeLink head;
    private final Queue<SnakeLink> snakeBody = new ArrayDeque<>();
    private boolean growNextStep = false;
    private Direction changeDirectionBuffer;

    public Snake(int x, int y, int gridRowCount, int gridColumnCount, Direction initialDirection) {
        super(x, y, gridRowCount, gridColumnCount, initialDirection);
        this.prevx = x;
        this.prevy = y;
        this.head = new SnakeLink(x, y);
        snakeBody.add(head);
        this.changeDirectionBuffer = getCurrentDirection();
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

        // prev pos is previous tail pos
        prevx = snakeBody.peek().getX();
        prevy = snakeBody.peek().getY();

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

    @Override
    public int getPrevx() {
        return prevx;
    }

    @Override
    public int getPrevy() {
        return prevy;
    }
}
