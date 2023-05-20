package ru.nsu.amazyar.entities;

import ru.nsu.amazyar.bases.Direction;

public interface Entity {
    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    Direction getCurrentDirection();

    void move();
}
