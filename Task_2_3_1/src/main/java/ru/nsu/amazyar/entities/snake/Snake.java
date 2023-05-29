package ru.nsu.amazyar.entities.snake;

import java.util.ArrayDeque;
import ru.nsu.amazyar.bases.Direction;
import ru.nsu.amazyar.entities.MovableEntity;

public class Snake extends MovableEntity {
    private SnakeLink head;
    private final ArrayDeque<SnakeLink> snakeBody = new ArrayDeque<>();
    private boolean growNextStep = false;

    public Snake(int x, int y, int gridRowCount, int gridColumnCount, Direction initialDirection) {
        super(x, y, gridRowCount, gridColumnCount, initialDirection);
        this.head = new SnakeLink(x, y);
        snakeBody.add(head);
    }

    public int getLength(){
        if(growNextStep){
            return snakeBody.size()+1;
        }

        return snakeBody.size();
    }
    public ArrayDeque<SnakeLink> getSnakeBody() {
        return snakeBody;
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

        setX(getNextX());
        setY(getNextY());

        this.head = new SnakeLink(getX(), getY());  // append head
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

    /**
     * Returns tail's x coordinate.
     */
    @Override
    public int getPrevX() {
        if(snakeBody.isEmpty()){
            throw new IllegalStateException("Snake wasn't initialized");
        }
        return snakeBody.peek().getX();
    }

    /**
     * Returns tail's y coordinate.
     */
    @Override
    public int getPrevY() {
        if(snakeBody.isEmpty()){
            throw new IllegalStateException("Snake wasn't initialized");
        }
        return snakeBody.peek().getY();
    }
}
