package ru.nsu.amazyar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.amazyar.constants.StageConstants;
import ru.nsu.amazyar.utils.SceneDrawer;

public class SnakeApplication extends Application {
    @Override
    public void start(Stage stage) {
        stage.setMinWidth(StageConstants.DEFAULT_STAGE_SIZE);
        stage.setMinHeight(StageConstants.DEFAULT_STAGE_SIZE);
        Scene mainMenuScene = SceneDrawer.getMainScene();

        stage.setTitle("Snake eyes");
        stage.setScene(mainMenuScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
