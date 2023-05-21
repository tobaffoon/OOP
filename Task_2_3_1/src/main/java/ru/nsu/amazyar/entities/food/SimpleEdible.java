package ru.nsu.amazyar.entities.food;

import ru.nsu.amazyar.bases.Direction;
import ru.nsu.amazyar.entities.Entity;
import ru.nsu.amazyar.entities.MovableEntity;

public class SimpleEdible implements Entity {
    private final int x, y;
    public final int growValue;

    public SimpleEdible(int x, int y, int growValue) {
        this.x = x;
        this.y = y;
        this.growValue = growValue;
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
