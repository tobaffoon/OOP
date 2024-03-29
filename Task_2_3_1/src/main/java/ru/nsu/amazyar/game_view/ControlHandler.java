package ru.nsu.amazyar.game_view;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.nsu.amazyar.bases.Direction;

/**
 * Handler for keyboard presses.
 */
public class ControlHandler implements EventHandler<KeyEvent> {

    private final GameScreenController controller;

    /**
     * Construct new control handler.
     *
     * @param controller controller, whose methods handler calls
     */
    public ControlHandler(GameScreenController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(KeyEvent event) {
        KeyCode code = event.getCode();

        switch (code) {
            case LEFT:
                controller.playerChangeDirection(Direction.LEFT);
                break;
            case RIGHT:
                controller.playerChangeDirection(Direction.RIGHT);
                break;
            case UP:
                controller.playerChangeDirection(Direction.UP);
                break;
            case DOWN:
                controller.playerChangeDirection(Direction.DOWN);
                break;
            case P:
                controller.pauseAndUnpause();
                break;
            default:
                break;
        }
    }
}
