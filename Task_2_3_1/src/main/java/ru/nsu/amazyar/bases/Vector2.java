package ru.nsu.amazyar.bases;

/**
 * Simple class for coordinates pair.
 */
public class Vector2 {

    private int x;
    private int y;

    /**
     * Simple constructor.
     */
    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * X coordinate getter.
     */
    public int getX() {
        return x;
    }

    /**
     * X coordinate setter.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Y coordinate getter.
     */
    public int getY() {
        return y;
    }

    /**
     * Y coordinate setter.
     */
    public void setY(int y) {
        this.y = y;
    }
}
