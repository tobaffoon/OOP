package ru.nsu.amazyar.constants;

import javafx.scene.text.Font;

/**
 * Constant class with settings screen's sizes and fonts.
 */
public class SettingsSceneConstants {

    public static final double DEFAULT_VBOX_HEIGHT = StageConstants.DEFAULT_STAGE_SIZE;
    public static final double DEFAULT_VBOX_WIDTH = StageConstants.DEFAULT_STAGE_SIZE;
    public static final double DEFAULT_SPACING = 6;

    public static final double DEFAULT_LABEL_HEIGHT = StageConstants.DEFAULT_STAGE_SIZE * 0.2;
    public static final Font
        DEFAULT_LABEL_FONT = Font.font("Georgia Bold", DEFAULT_LABEL_HEIGHT * 0.5);
    public static final String DEFAULT_LABEL_COLOR = "#71ae08";

    public static final double DEFAULT_BUTTON_HEIGHT = StageConstants.DEFAULT_STAGE_SIZE * 0.2;
    public static final double DEFAULT_BUTTON_INSET = DEFAULT_VBOX_WIDTH * 0.2;
    public static final double DEFAULT_BUTTON_WIDTH = DEFAULT_VBOX_WIDTH - 2 * DEFAULT_BUTTON_INSET;
    public static final Font DEFAULT_BUTTON_FONT = Font.font("Elephant", DEFAULT_BUTTON_HEIGHT * 0.3);

    public static final int PARAMS_NUMBER = 5;
    public static final double DEFAULT_PARAMS_BOX_HEIGHT = DEFAULT_VBOX_WIDTH * 5;
    public static final double DEFAULT_PARAMS_BOX_INSET = DEFAULT_VBOX_WIDTH * 0.1;
    public static final double DEFAULT_PARAMS_BOX_WIDTH = DEFAULT_VBOX_WIDTH - 2 * DEFAULT_PARAMS_BOX_INSET;
    public static final double DEFAULT_PARAMS_SPACING = 8;
}
