package ru.nsu.amazyar.entities;

public class Brick implements Entity {

    private final int x, y;

    public Brick(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
    }

    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
    }
}
