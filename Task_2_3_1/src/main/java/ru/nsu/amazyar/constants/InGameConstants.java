package ru.nsu.amazyar.constants;

import ru.nsu.amazyar.bases.Direction;

public class InGameConstants {
    public static final double MIN_ROW_NUMBER = 5;
    public static final double MIN_COLUMN_NUMBER = 5;
    public static final double MAX_ROW_NUMBER = 100;
    public static final double MAX_COLUMN_NUMBER = 100;
    public static final String SNAKE_HEAD_SPRITE = "images/snake_head.png";
    public static final String SNAKE_BODY_SPRITE = "images/snake_body.png";
    public static final String SNAKE_TAIL_SPRITE = "images/snake_tail.png";
    public static final String FOOD_SPRITE = "images/egg.png";

    public static final Direction DEFAULT_PLAYER_DIRECTION = Direction.DOWN;
    public static final long DEFAULT_NANOS_PER_TILE = 100000000;
    public static final int DEFAULT_MAX_FOOD_NUMBER = 1;
}
