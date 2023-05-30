package ru.nsu.amazyar.entities.snake;

import ru.nsu.amazyar.entities.Entity;

/**
 * Component of a {@link Snake}. As snake moves by creating and deleting <i>SnakeLinks</i>, this
 * class does not have to be Movable.
 */
public class SnakeLink implements Entity {

    private int x;
    private int y;

    /**
     * Convenient constructor for {@link Entity}.
     */
    public SnakeLink(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Link " +
            "(" + x +
            ", " + y +
            ')';
    }
}
