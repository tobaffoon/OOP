module ru.nsu.amazyar {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens ru.nsu.amazyar to javafx.fxml;
    exports ru.nsu.amazyar;
    exports ru.nsu.amazyar.main_menu;
    opens ru.nsu.amazyar.main_menu to javafx.fxml;
}