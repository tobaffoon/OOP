package ru.nsu.amazyar.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import ru.nsu.amazyar.SnakeApplication;

public class SceneDrawer {
    static public Scene getMainScene(){
        return getSceneFromFxml("fxmls/main_menu.fxml");
    }

    static public Scene getSettingsScene(){
        return getSceneFromFxml("fxmls/settings.fxml");
    }

    static private Scene getSceneFromFxml(String path){
        Scene scene = null;
        try {
            FXMLLoader fxmlLoader =
                new FXMLLoader(SnakeApplication.class.getResource(path));

            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            ErrorAlerter.alert(e);
        }

        return scene;
    }
}
