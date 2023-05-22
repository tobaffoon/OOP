package ru.nsu.amazyar;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SceneDrawer {
    static public Scene getMainScene(){
        return getSceneFromFxml("fxmls/main_menu.fxml");
    }

    static public Scene getGameScene(){
        return getSceneFromFxml("fxmls/game_screen.fxml");
    }

    static public Scene getSettingsScene(){
        return getSceneFromFxml("fxmls/settings.fxml");
    }

    static private Scene getSceneFromFxml(String path){
        FXMLLoader fxmlLoader =
            new FXMLLoader(SnakeApplication.class.getResource(path));

        Scene scene;

        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            // TODO add dialog handler
            throw new RuntimeException(e);
        }

        return scene;
    }
}
