package ru.nsu.amazyar.constants;

import javafx.scene.text.Font;

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

    public static final double DEFAULT_PARAMS_UP_DOWN_INSET = DEFAULT_PARAMS_BOX_HEIGHT * 0.05;
    public static final double DEFAULT_PARAMS_SIDE_INSET = DEFAULT_PARAMS_BOX_WIDTH * 0.05;
    public static final double DEFAULT_PARAM_HEIGHT = (DEFAULT_PARAMS_BOX_HEIGHT - DEFAULT_PARAMS_SPACING * (PARAMS_NUMBER - 1)) / PARAMS_NUMBER - 2 * DEFAULT_PARAMS_UP_DOWN_INSET;
    public static final double DEFAULT_PARAM_WIDTH = DEFAULT_PARAMS_BOX_WIDTH - 2 * DEFAULT_PARAMS_SIDE_INSET;

    // Grid sizes
    public static final double DEFAULT_GRID_SIZE_SIDE_INSET = DEFAULT_PARAM_WIDTH * 0.05;
    public static final double DEFAULT_GRID_SIZE_UP_DOWN_INSET = DEFAULT_PARAM_HEIGHT * 0.05;
    public static final double DEFAULT_GRID_SIZE_SPACER_WIDTH = DEFAULT_PARAM_WIDTH * 0.1;

    public static final double DEFAULT_GRID_SIZE_HBOX_HEIGHT = DEFAULT_PARAM_HEIGHT - 2 * DEFAULT_GRID_SIZE_UP_DOWN_INSET;
    public static final double DEFAULT_GRID_SIZE_HBOX_WIDTH = (DEFAULT_PARAM_WIDTH - 2 * DEFAULT_GRID_SIZE_SIDE_INSET - DEFAULT_GRID_SIZE_SPACER_WIDTH) / 2;
    public static final double DEFAULT_GRID_SIZE_HBOX_SPACING =  DEFAULT_GRID_SIZE_HBOX_HEIGHT * 0.1;

    public static final double DEFAULT_GRID_SIZE_LABEL_WIDTH = DEFAULT_GRID_SIZE_HBOX_WIDTH;
    public static final double DEFAULT_GRID_SIZE_LABEL_HEIGHT = DEFAULT_GRID_SIZE_HBOX_HEIGHT * 0.45;
    public static final double DEFAULT_GRID_SIZE_SLIDER_HEIGHT = DEFAULT_GRID_SIZE_HBOX_HEIGHT * 0.45;
}
