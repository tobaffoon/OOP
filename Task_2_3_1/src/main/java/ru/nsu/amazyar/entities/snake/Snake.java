package ru.nsu.amazyar.entities.snake;

import ru.nsu.amazyar.bases.Direction;
import ru.nsu.amazyar.entities.EntityGroup;

public class Snake extends EntityGroup {
    private SnakeLink tail;

    public Snake(int rowCount, int columnCount) {
        super(new SnakeLink(0, 0, rowCount, columnCount, Direction.DOWN, null));
        this.tail = (SnakeLink) this.getMainEntity();
    }

    public SnakeLink getTail() {
        return tail;
    }

    public SnakeLink getHead() {
        return (SnakeLink) this.getMainEntity();
    }

    public void growTail() {
        this.tail = this.tail.addPrevLink();
        this.addChild(this.tail);
    }
}
