package ru.nsu.amazyar;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SnakeApplication extends Application {
    private Scene mainMenuScene;
    private Scene gameScene;
    private Scene gameOverScene;

    private Stage mainStage;

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        mainMenuScene = SceneCreator.getMainScene();
        gameScene = SceneCreator.getGameScene();

        mainStage.setTitle("Snake eyes");
        mainStage.setScene(mainMenuScene);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
