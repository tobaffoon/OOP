module ru.nsu.amazyar {
    requires javafx.controls;
    requires javafx.fxml;

    opens ru.nsu.amazyar to javafx.fxml;
    exports ru.nsu.amazyar;
}