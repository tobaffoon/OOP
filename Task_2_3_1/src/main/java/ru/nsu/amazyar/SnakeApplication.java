package ru.nsu.amazyar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.amazyar.constants.MainMenuConstants;
import ru.nsu.amazyar.constants.StageConstants;

public class SnakeApplication extends Application {
    private Scene mainMenuScene;
    private Scene gameScene;
    private Scene gameOverScene;

    private Stage mainStage;

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        mainStage.setMinWidth(StageConstants.DEFAULT_STAGE_SIZE);
        mainStage.setMinHeight(StageConstants.DEFAULT_STAGE_SIZE);
        mainMenuScene = SceneDrawer.getMainScene();
        gameScene = SceneDrawer.getGameScene();

        mainStage.setTitle("Snake eyes");
        mainStage.setScene(mainMenuScene);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
