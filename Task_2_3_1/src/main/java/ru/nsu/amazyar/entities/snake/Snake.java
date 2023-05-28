package ru.nsu.amazyar.entities.snake;

import java.util.ArrayDeque;
import java.util.Queue;
import ru.nsu.amazyar.bases.Direction;
import ru.nsu.amazyar.entities.MovableEntity;

public class Snake extends MovableEntity {
    private int prevX;
    private int prevY;
    private SnakeLink head;
    private final Queue<SnakeLink> snakeBody = new ArrayDeque<>();
    private boolean growNextStep = false;
    private Direction changeDirectionBuffer;

    public Snake(int x, int y, int gridRowCount, int gridColumnCount, Direction initialDirection) {
        super(x, y, gridRowCount, gridColumnCount, initialDirection);
        this.prevX = x;
        this.prevY = y;
        this.head = new SnakeLink(x, y, this);
        snakeBody.add(head);
        this.changeDirectionBuffer = getCurrentDirection();
    }

    public int getLength(){
        if(growNextStep){
            return snakeBody.size()+1;
        }

        return snakeBody.size();
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
        prevX = snakeBody.peek().getX();
        prevY = snakeBody.peek().getY();

        if(growNextStep){
            growNextStep = false;
        }
        else{
            snakeBody.poll();   // delete tail
        }

        setX(getNextX());
        setY(getNextY());

        this.head = new SnakeLink(getX(), getY(), this);
        this.snakeBody.add(this.head);
    }

    @Override
    public String toString() {
        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (SnakeLink link : snakeBody) {
            stringBuilder.append("[");
            stringBuilder.append(i);
            stringBuilder.append("] ");
            stringBuilder.append(link);
            i++;
        }

        return stringBuilder.toString();
    }

    @Override
    public int getPrevX() {
        return prevX;
    }

    @Override
    public int getPrevY() {
        return prevY;
    }
}
