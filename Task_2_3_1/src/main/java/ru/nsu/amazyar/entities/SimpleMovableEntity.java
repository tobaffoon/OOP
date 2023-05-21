package ru.nsu.amazyar.entities;

import ru.nsu.amazyar.bases.Direction;

public abstract class SimpleMovableEntity implements MovableEntity {
    private int x;
    private int y;
    public final int maxRow;
    public final int maxColumn;
    private Direction currentDirection;

    public SimpleMovableEntity(int x, int y, int gridRowCount, int gridColumnCount, Direction initialDirection) {
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

    abstract public void move();
    abstract public int getPrevx();
    abstract public int getPrevy();
}
