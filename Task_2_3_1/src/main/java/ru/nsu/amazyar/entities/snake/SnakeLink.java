package ru.nsu.amazyar.entities.snake;

import ru.nsu.amazyar.bases.Direction;
import ru.nsu.amazyar.entities.Entity;
import ru.nsu.amazyar.entities.MovableEntity;

public class SnakeLink implements Entity {
    private int x;
    private int y;
    private final Snake snake;

    public SnakeLink(int x, int y, Snake snake) {
        this.x = x;
        this.y = y;
        this.snake = snake;
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

    public Snake getSnake() {
        return snake;
    }

    public boolean isHead(){
        System.out.println(this.equals(snake.getHead()));
        return this.equals(snake.getHead());
    }

    @Override
    public String toString() {
        return "Link " +
            "(" + x +
            ", " + y +
            ')';
    }
}
