package ru.nsu.amazyar.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Alert windows with exceptions creator.
 */
public class ErrorAlerter {

    /**
     * Create new {@link Alert} window with exception information inside.
     */
    public static void alert(Exception e) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText(e.getClass().getName());
        errorAlert.setContentText(e.getMessage());
        errorAlert.showAndWait();
    }
}
