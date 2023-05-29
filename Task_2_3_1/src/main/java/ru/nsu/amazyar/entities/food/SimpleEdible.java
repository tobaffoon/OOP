package ru.nsu.amazyar.entities.food;

import ru.nsu.amazyar.bases.Vector2;
import ru.nsu.amazyar.entities.Entity;

/**
 * Basic food entity.
 */
public class SimpleEdible implements Entity {

    private final int x, y;

    /**
     * Construct new edible.
     *
     * @param pos initial position
     */
    public SimpleEdible(Vector2 pos) {
        this.x = pos.getX();
        this.y = pos.getY();
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
