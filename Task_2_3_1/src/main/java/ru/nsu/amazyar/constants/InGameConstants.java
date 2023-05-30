package ru.nsu.amazyar.constants;

import ru.nsu.amazyar.bases.Direction;

/**
 * Constant class with game's logic.
 */
public class InGameConstants {

    public static final double MIN_ROW_NUMBER = 5;
    public static final double MIN_COLUMN_NUMBER = 5;
    public static final double MAX_ROW_NUMBER = 100;
    public static final double MAX_COLUMN_NUMBER = 100;

    public static final Direction DEFAULT_PLAYER_DIRECTION = Direction.DOWN;
    public static final long DEFAULT_NANOS_PER_TILE = 400000000;
}
