package ru.nsu.amazyar.settings_screen;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.nsu.amazyar.SnakeApplication;
import ru.nsu.amazyar.game_screen.GameScreenController;
import ru.nsu.amazyar.utils.ErrorAlerter;

public class SettingsController implements Initializable {
    @FXML
    Button startButton;
    @FXML
    Label rowsLabel;
    @FXML
    TextField rowsField;
    @FXML
    TextField columnsField;
    @FXML
    Label columnsLabel;
    @FXML
    TextField lengthField;
    @FXML
    Label lengthLabel;
    @FXML
    TextField maxFoodField;
    @FXML
    Label maxFoodLabel;
    @FXML
    TextField speedField;
    @FXML
    Label speedLabel;
    @FXML
    TextField brickNumberField;
    @FXML
    Label brickNumberLabel;
    @FXML
    ColorPicker colorPicker1;
    @FXML
    ColorPicker colorPicker2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addValueRestrainingListener(lengthField, 5, 100);
        addValueRestrainingListener(rowsField, 5, 100);
        addValueRestrainingListener(columnsField, 5, 100);
        addValueRestrainingListener(maxFoodField, 1, 100);
        addValueRestrainingListener(speedField, 1, 10);

        // separate brickNumber because it's max value is variable and unknown before initialisation
        brickNumberField.focusedProperty().addListener((b, o, n) -> {
            if (!n) {
                int inputValue = Integer.parseInt(brickNumberField.getText());
                brickNumberField.setText(Integer.toString(roundToBounds(inputValue, 0,
                    (int) Math.ceil(0.3 * (getRowsValue() * getColumnsValue() - 9)))));
            }
        });

        addNumberFieldToLabelChangedListener(lengthField, lengthLabel, "LENGTH TO WIN");
        addNumberFieldToLabelChangedListener(rowsField, rowsLabel, "ROWS");
        addNumberFieldToLabelChangedListener(columnsField, columnsLabel, "COLUMNS");
        addNumberFieldToLabelChangedListener(maxFoodField, maxFoodLabel, "MAX NUMBER OF FOOD");
        addNumberFieldToLabelChangedListener(speedField, speedLabel, "SPEED");
        addNumberFieldToLabelChangedListener(brickNumberField, brickNumberLabel, "BRICK NUMBER");
    }

    /**
     * Inputs field's value to label's text.
     *
     * @param labelTemplate What to print before the value
     */
    private void addNumberFieldToLabelChangedListener(TextField field, Label label,
        String labelTemplate) {
        field.textProperty().addListener((b, o, n) -> {
            String trimValue = numberOnlyString(n);
            field.setText(trimValue);
            label.setText(labelTemplate + ": [" + trimValue + "]");
        });
    }

    /**
     * Rounds integer value to bounds when focused is changed.
     */
    private void addValueRestrainingListener(TextField field, int min, int max) {
        field.focusedProperty().addListener((b, o, n) -> {
            if (!n) {
                int inputValue = Integer.parseInt(field.getText());
                field.setText(Integer.toString(roundToBounds(inputValue, min, max)));
            }
        });
    }

    private String numberOnlyString(String input) {
        if (!input.matches("\\d*")) {
            input = input.replaceAll("\\D", "");
        }

        return input;
    }

    private int roundToBounds(int input, int min, int max){
        if(input < min){
            input = min;
        }
        else if(input > max){
            input = max;
        }

        return input;
    }

    private int getRowsValue(){
        return Integer.parseInt(rowsField.getText());
    }
    private int getColumnsValue(){
        return Integer.parseInt(columnsField.getText());
    }
    private int getMaxFoodValue(){
        return Integer.parseInt(maxFoodField.getText());
    }
    private int getSpeedValue(){
        return Integer.parseInt(speedField.getText());
    }
    private int getLengthValue(){
        return Integer.parseInt(lengthField.getText());
    }
    private int getBrickNumberValue(){
        return Integer.parseInt(brickNumberField.getText());
    }

    @FXML
    public void onStartButtonPressed(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        try {
            FXMLLoader fxmlLoader =
                new FXMLLoader(SnakeApplication.class.getResource("fxmls/game_screen.fxml"));
            new Scene(fxmlLoader.load());

            GameScreenController controller = fxmlLoader.getController();
            controller.startNewGame(stage, getRowsValue(), getColumnsValue(), getMaxFoodValue(),
                getLengthValue(), getBrickNumberValue(), getSpeedValue(), colorPicker1.getValue(),
                colorPicker2.getValue());
        } catch (Exception e) {
            ErrorAlerter.alert(e);
        }

    }
}
