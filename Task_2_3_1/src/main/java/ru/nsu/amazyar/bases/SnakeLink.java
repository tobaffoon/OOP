package ru.nsu.amazyar.bases;

public abstract class SnakeLink {
    public enum Direction{
        UP,
        LEFT,
        DOWN,
        RIGHT
    }
    private final SnakeLink head;
    private SnakeLink tail;
    private int X;
    private int Y;
    private Direction currentDirection;

    public SnakeLink(SnakeLink head, SnakeLink tail, Direction currentDirection) {
        this.head = head;
        this.tail = tail;
        this.currentDirection = currentDirection;
    }

    // use to check for collision
    public int getX() {
        return X;
    }

    // use to check for collision
    public int getY() {
        return Y;
    }

    // use to enlarge the snake
    public void setTail(SnakeLink tail) {
        this.tail = tail;
    }

    // use to cut the snake when it's bitten
    public SnakeLink getHead() {
        return head;
    }

    // use to predict where snake will be next
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    // use to change direction
    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void move(){
        switch (currentDirection){
        }
    }
}
