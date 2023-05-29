package ru.nsu.amazyar.constants;

import javafx.stage.Screen;

/**
 * Constant class with main window's sizes.
 */
public class StageConstants {

    private static final double STAGE_INSET = 200;
    public static final double DEFAULT_STAGE_SIZE = Math.min(Screen.getPrimary().getBounds().getHeight() - STAGE_INSET, Screen.getPrimary().getBounds().getWidth() - STAGE_INSET);
}
