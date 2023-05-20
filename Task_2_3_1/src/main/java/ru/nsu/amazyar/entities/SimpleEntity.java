package ru.nsu.amazyar.entities;

import ru.nsu.amazyar.bases.Direction;

public class SimpleEntity implements Entity {
    private int x;
    private int y;
    private Direction currentDirection;

    public SimpleEntity(int x, int y, Direction initialDirection) {
        this.x = x;
        this.y = y;
        this.currentDirection = initialDirection;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getCurrentDirection(){
        return currentDirection;
    }

    public void turnLeft() {
        currentDirection = Direction.values()[(currentDirection.ordinal() + 1) % Direction.values().length];
    }

    public void turnRight() {
        currentDirection = Direction.values()[(currentDirection.ordinal() - 1) % Direction.values().length];
    }

    public void move(){
        switch (currentDirection) {
            case UP -> y--;
            case LEFT -> x--;
            case DOWN -> y++;
            case RIGHT -> x++;
        }
    }
}
