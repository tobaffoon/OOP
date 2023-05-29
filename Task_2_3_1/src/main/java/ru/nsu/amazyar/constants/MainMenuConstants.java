package ru.nsu.amazyar.constants;

import javafx.scene.text.Font;

/**
 * Constant class with main menu's sizes and fonts.
 */
public class MainMenuConstants {

    public static final double DEFAULT_NAME_HEIGHT = StageConstants.DEFAULT_STAGE_SIZE * 0.3;
    public static final String DEFAULT_NAME_TEXT = "SNAKE EYES";
    public static final Font DEFAULT_NAME_FONT = Font.font("Algerian", DEFAULT_NAME_HEIGHT * 0.5);
    public static final String DEFAULT_NAME_COLOR = "#71ae08";

    public static final double DEFAULT_VBOX_HEIGHT = StageConstants.DEFAULT_STAGE_SIZE * 0.6;
    public static final double DEFAULT_VBOX_WIDTH = StageConstants.DEFAULT_STAGE_SIZE;

    public static final double DEFAULT_BUTTONS_SPACING = 4;
    public static final double BUTTONS_COUNT = 4;
    public static final double DEFAULT_BUTTON_HEIGHT = (DEFAULT_VBOX_HEIGHT - DEFAULT_BUTTONS_SPACING * (BUTTONS_COUNT - 1)) / BUTTONS_COUNT;
    public static final Font DEFAULT_BUTTON_FONT = Font.font("Elephant", DEFAULT_BUTTON_HEIGHT * 0.5);
    public static final double DEFAULT_BUTTON_SIDE_INSET = DEFAULT_VBOX_WIDTH * 0.05;
    public static final double DEFAULT_BUTTON_WIDTH = DEFAULT_VBOX_WIDTH - 2 * DEFAULT_BUTTON_SIDE_INSET;
}
