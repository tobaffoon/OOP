package ru.nsu.amazyar.entities;

/**
 * Brick into which snake can crush.
 */
public class Brick implements Entity {

    private final int x, y;

    /**
     * Convenient constructor for {@link Entity}.
     */
    public Brick(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
    }
}
