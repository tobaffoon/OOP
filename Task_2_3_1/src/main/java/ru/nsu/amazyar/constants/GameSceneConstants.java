package ru.nsu.amazyar.constants;

import javafx.scene.text.Font;

public class GameSceneConstants {
    public static final String SNAKE_HEAD_SPRITE = "images/snake_head.png";
    public static final String SNAKE_BODY_SPRITE = "images/snake_body.png";
    public static final String SNAKE_TAIL_SPRITE = "images/snake_tail.png";
    public static final String FOOD_SPRITE = "images/egg.png";
    public static final double DEFAULT_LOSE_LABEL_HEIGHT = StageConstants.DEFAULT_STAGE_SIZE * 0.3;
    public static final double DEFAULT_WIN_LABEL_HEIGHT = StageConstants.DEFAULT_STAGE_SIZE * 0.3;
    public static final Font DEFAULT_LABEL_FONT = Font.font("Georgia Bold", DEFAULT_LOSE_LABEL_HEIGHT * 0.5);
    public static final String DEFAULT_LOSE_LABEL_COLOR = "#ff0000";

    public static final double DEFAULT_VBOX_HEIGHT = StageConstants.DEFAULT_STAGE_SIZE;
    public static final double DEFAULT_VBOX_WIDTH = StageConstants.DEFAULT_STAGE_SIZE;
    public static final double DEFAULT_TOP_MARGIN = DEFAULT_VBOX_HEIGHT * 0.2;

    public static final double DEFAULT_SPACING = 4;
    public static final double DEFAULT_SCORE_LABEL_HEIGHT = StageConstants.DEFAULT_STAGE_SIZE * 0.1;
    public static final double DEFAULT_BUTTON_HEIGHT = DEFAULT_SCORE_LABEL_HEIGHT;
    public static final Font DEFAULT_SCORE_FONT = Font.font("Georgia Bold", DEFAULT_SCORE_LABEL_HEIGHT * 0.5);
    public static final Font DEFAULT_BUTTON_FONT = Font.font("Elephant", DEFAULT_BUTTON_HEIGHT * 0.3);
    public static final double DEFAULT_SCORE_INSET = DEFAULT_VBOX_WIDTH * 0.05;
    public static final double DEFAULT_BUTTON_INSET = DEFAULT_VBOX_WIDTH * 0.2;
    public static final double DEFAULT_SCORE_LABEL_WIDTH = DEFAULT_VBOX_WIDTH - 2 * DEFAULT_BUTTON_INSET;
    public static final double DEFAULT_BUTTON_WIDTH = DEFAULT_VBOX_WIDTH - 2 * DEFAULT_BUTTON_INSET;
}
