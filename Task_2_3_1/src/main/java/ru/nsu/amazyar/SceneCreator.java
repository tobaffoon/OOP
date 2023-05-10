package ru.nsu.amazyar;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class SceneCreator {
    static public Scene getMainScene(){
        return getSceneFromFxml("fxmls/main_menu.fxml");
    }

    static public Scene getGameScene(){
        return getSceneFromFxml("fxmls/game_screen.fxml");
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
