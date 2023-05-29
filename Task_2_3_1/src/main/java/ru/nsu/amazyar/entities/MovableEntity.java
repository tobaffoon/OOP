package ru.nsu.amazyar.entities;

import ru.nsu.amazyar.bases.Direction;

/**
 * Entity that can move on 2d grid. When a boarder of the grid is reached, entity moves to the
 * opposite side
 */
public abstract class MovableEntity implements Entity {

    private int x;
    private int y;
    public final int maxRow;
    public final int maxColumn;
    private Direction currentDirection;

    /**
     * Create movable entity.
     * Note grid sizes are required to move through edges to the opposite side.
     * @param x initial x coordinate
     * @param y initial y coordinate
     * @param gridRowCount number of rows in the grid
     * @param gridColumnCount number of columns in the grid
     * @param initialDirection initial direction in which entity moves
     */
    public MovableEntity(int x, int y, int gridRowCount, int gridColumnCount,
        Direction initialDirection) {
        this.x = x;
        this.y = y;
        this.maxRow = gridRowCount;
        this.maxColumn = gridColumnCount;
        this.currentDirection = initialDirection;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Current direction in which entity moves getter.
     */
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    /**
     * Current direction in which entity moves setter.
     */
    public void changeDirection(Direction direction) {
        if (changeDirectionAllowed(direction)) {
            currentDirection = direction;
        }
    }

    /**
     * Check if entity can change its direction to suggested one.
     *
     * @param direction suggested direction
     * @return Return false on UP <-> DOWN and LEFT <-> RIGHT direction change. Otherwise, returns
     * true.
     */
    public boolean changeDirectionAllowed(Direction direction) {
        switch (direction) {
            case UP:
                if (currentDirection == Direction.DOWN) {
                    return false;
                }
                break;
            case DOWN:
                if (currentDirection == Direction.UP) {
                    return false;
                }
                break;
            case LEFT:
                if(currentDirection == Direction.RIGHT)
                    return false;
                break;
            case RIGHT:
                if (currentDirection == Direction.LEFT) {
                    return false;
                }
                break;
        }

        return true;
    }

    /**
     * Returns next x coordinate according to current direction.
     */
    public int getNextX() {
        switch (getCurrentDirection()) {
            case DOWN:
            case UP:
                return getX();
            case LEFT:
                return Math.floorMod(getX() - 1, maxColumn);
            case RIGHT:
                return Math.floorMod(getX() + 1, maxColumn);
            default:
                return -1;
        }
    }

    /**
     * Returns next y coordinate according to current direction.
     */
    public int getNextY() {
        switch (getCurrentDirection()) {
            case LEFT:
            case RIGHT:
                return getY();
            case UP:
                return Math.floorMod(getY() - 1, maxColumn);
            case DOWN:
                return Math.floorMod(getY() + 1, maxColumn);
            default:
                return -1;
        }
    }

    /**
     * Changes current position.
     */
    abstract public void move();

    /**
     * Return last visited x coordinate.
     */
    abstract public int getPrevX();

    /**
     * Return last visited x coordinate.
     */
    abstract public int getPrevY();
}
