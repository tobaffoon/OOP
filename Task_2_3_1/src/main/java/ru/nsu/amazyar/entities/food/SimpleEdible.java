package ru.nsu.amazyar.entities.food;

import ru.nsu.amazyar.bases.Vector2;
import ru.nsu.amazyar.entities.Entity;

public class SimpleEdible implements Entity {
    private final int x, y;
    public final int growValue;

    public SimpleEdible(Vector2 pos, int growValue) {
        this.x = pos.getX();
        this.y = pos.getY();
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
