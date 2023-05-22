package ru.nsu.amazyar.settings_screen;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class SettingsController implements Initializable {
    @FXML
    Button startButton;
    @FXML
    Label rowsLabel;
    @FXML
    Slider rowsSlider;
    @FXML
    Slider columnsSlider;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rowsSlider.valueProperty().addListener((b, o, n) -> rowsLabel.setText("ROWS: [" + n.intValue() + "]"));
        columnsSlider.valueProperty().addListener((b, o, n) -> columnsLabel.setText("COLUMNS: [" + n.intValue() + "]"));

        addNumberFieldToLabelListener(lengthField, lengthLabel, "LENGTH TO WIN");
        addNumberFieldToLabelListener(maxFoodField, maxFoodLabel, "MAX NUMBER OF FOOD");
        addNumberFieldToLabelListener(speedField, speedLabel, "SPEED");
    }

    private void addNumberFieldToLabelListener(TextField field, Label label, String labelTemplate){
        field.focusedProperty().addListener((b, o, n) -> {
            if(!n){
                int inputValue = Integer.parseInt(field.getText());
                field.setText(Integer.toString(roundToBounds(inputValue, 1, 5)));

                String value = numberOnlyString(field.getText());
                label.setText(labelTemplate + ": [" + value + "]");
            }
        });
    }

    private String numberOnlyString(String input){
        if (!input.matches("\\d*")) {
            input = input.replaceAll("[^\\d]", "0");
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

    public void onStartButtonPressed(){

    }
}
