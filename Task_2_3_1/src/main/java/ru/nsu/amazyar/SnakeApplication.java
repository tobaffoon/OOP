package ru.nsu.amazyar;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SnakeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =
            new FXMLLoader(SnakeApplication.class.getResource("fxmls/main_menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Snake eyes");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
