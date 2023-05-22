package ru.nsu.amazyar.settings_screen;


import java.io.IOException;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.nsu.amazyar.SceneDrawer;
import ru.nsu.amazyar.SnakeApplication;
import ru.nsu.amazyar.game_screen.GameScreenController;

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
    ColorPicker colorPicker1;
    @FXML
    ColorPicker colorPicker2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addNumberFieldToLabelListener(lengthField, lengthLabel, "LENGTH TO WIN", 5, 100);
        addNumberFieldToLabelListener(rowsField, rowsLabel, "ROWS", 5, 100);
        addNumberFieldToLabelListener(columnsField, columnsLabel, "COLUMNS", 5, 100);
        addNumberFieldToLabelListener(maxFoodField, maxFoodLabel, "MAX NUMBER OF FOOD", 1, 5);
        addNumberFieldToLabelListener(speedField, speedLabel, "SPEED", 1, 5);
    }

    private void addNumberFieldToLabelListener(TextField field, Label label, String labelTemplate, int min, int max){
        field.focusedProperty().addListener((b, o, n) -> {
            if(!n){
                int inputValue = Integer.parseInt(field.getText());
                field.setText(Integer.toString(roundToBounds(inputValue, min, max)));

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

    @FXML
    public void onStartButtonPressed(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader =
            new FXMLLoader(SnakeApplication.class.getResource("fxmls/game_screen.fxml"));
        new Scene(fxmlLoader.load());

        GameScreenController controller = fxmlLoader.getController();
        controller.startNewGame(stage, Integer.parseInt(rowsField.getText()), Integer.parseInt(columnsField.getText()), Integer.parseInt(maxFoodField.getText()), Integer.parseInt(lengthField.getText()), Integer.parseInt(speedField.getText()), colorPicker1.getValue(), colorPicker2.getValue());
    }
}
