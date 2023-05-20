package ru.nsu.amazyar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.amazyar.constants.StageConstants;

public class SnakeApplication extends Application {
    @Override
    public void start(Stage stage) {
        Stage mainStage = stage;
        mainStage.setMinWidth(StageConstants.DEFAULT_STAGE_SIZE);
        mainStage.setMinHeight(StageConstants.DEFAULT_STAGE_SIZE);
        Scene mainMenuScene = SceneDrawer.getMainScene();

        mainStage.setTitle("Snake eyes");
        mainStage.setScene(mainMenuScene);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
