package ru.nsu.amazyar.entities;

import ru.nsu.amazyar.bases.Direction;

public interface MovableEntity extends Entity{
    void changeDirection(Direction direction);

    Direction getCurrentDirection();

    void move();
}
