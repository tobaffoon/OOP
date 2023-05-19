module ru.nsu.amazyar {
    requires javafx.controls;
    requires javafx.fxml;

    opens ru.nsu.amazyar to javafx.fxml;
    exports ru.nsu.amazyar;
    exports ru.nsu.amazyar.main_menu;
    exports ru.nsu.amazyar.game_screen;
    exports ru.nsu.amazyar.bases;
    exports ru.nsu.amazyar.constants;
    opens ru.nsu.amazyar.main_menu to javafx.fxml;
    opens ru.nsu.amazyar.game_screen to javafx.fxml;
    opens ru.nsu.amazyar.bases to javafx.fxml;
    opens ru.nsu.amazyar.constants to javafx.fxml;
}