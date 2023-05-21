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

    public void changeDirection(Direction direction){
        switch (direction){
            case UP:
                if(currentDirection != Direction.DOWN)
                    currentDirection = Direction.UP;
                break;
            case DOWN:
                if(currentDirection != Direction.UP)
                    currentDirection = Direction.DOWN;
                break;
            case LEFT:
                if(currentDirection != Direction.RIGHT)
                    currentDirection = Direction.LEFT;
                break;
            case RIGHT:
                if(currentDirection != Direction.LEFT)
                    currentDirection = Direction.RIGHT;
                break;
        }
    }

    public void move(){
        switch (currentDirection) {
            case UP:
                y = Math.floorMod(y - 1, maxRow);
                break;
            case LEFT:
                x = Math.floorMod(x - 1, maxColumn);
                break;
            case DOWN:
                y = Math.floorMod(y + 1, maxRow);
                break;
            case RIGHT:
                x = Math.floorMod(x + 1, maxColumn);
                break;
        }
    }
}
