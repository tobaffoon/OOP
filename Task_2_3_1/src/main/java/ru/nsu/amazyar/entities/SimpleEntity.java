package ru.nsu.amazyar.entities;

import ru.nsu.amazyar.bases.Direction;

public class SimpleEntity implements Entity {
    private int x;
    private int y;
    private final int maxRow;
    private final int maxColumn;
    private Direction currentDirection;

    public SimpleEntity(int x, int y, int gridRowCount, int gridColumnCount, Direction initialDirection) {
        this.x = x;
        this.y = y;
        this.maxRow = gridRowCount;
        this.maxColumn = gridColumnCount;
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

    public int getMaxRow() {
        return maxRow;
    }

    public int getMaxColumn() {
        return maxColumn;
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
            case UP -> y = Math.floorMod(y - 1, maxRow);
            case LEFT -> x = Math.floorMod(x - 1, maxColumn);
            case DOWN -> y = Math.floorMod(y + 1, maxRow);
            case RIGHT -> x = Math.floorMod(x + 1, maxColumn);
        }
    }
}
