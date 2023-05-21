package ru.nsu.amazyar.entities.snake;

import ru.nsu.amazyar.constants.InGameConstants;
import ru.nsu.amazyar.entities.MovableEntityLinkedList;

public class Snake extends MovableEntityLinkedList {
    private SnakeLink tail;

    public Snake(int rowCount, int columnCount) {
        super(new SnakeLink(0, 0, rowCount, columnCount, InGameConstants.DEFAULT_PLAYER_DIRECTION, null));
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
