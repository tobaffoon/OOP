package ru.nsu.amazyar.entities.snake;

import ru.nsu.amazyar.bases.Direction;
import ru.nsu.amazyar.entities.SimpleMovableEntity;

// Update order: move -> addTail (or other connections) -> changeDirection
public class SnakeLink extends SimpleMovableEntity {

    private final SnakeLink nextLink;
    private SnakeLink prevLink;
    private int prevx;
    private int prevy;

    public SnakeLink(int x, int y, int rowCount, int columnCount, Direction initialDirection, SnakeLink nextLink) {
        super(x, y, rowCount, columnCount, initialDirection);
        this.nextLink = nextLink;
        this.prevLink = null;
    }

    // use to enlarge the snake
    public SnakeLink addPrevLink() {
        if (prevLink != null) {
            return prevLink.addPrevLink();
        }

        this.prevLink = new SnakeLink(prevx, prevy, getMaxRow(), getMaxColumn(), getCurrentDirection(), this);
        return this.prevLink;
    }

    // use to cut the snake when it's bitten
    public SnakeLink getNextLink() {
        return nextLink;
    }

    public SnakeLink getPrevLink() {
        return prevLink;
    }

    @Override
    public void move() {
        prevx = getX();
        prevy = getY();
        super.move();
    }
}
