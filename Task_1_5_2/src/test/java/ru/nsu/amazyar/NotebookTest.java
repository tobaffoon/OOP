package ru.nsu.amazyar;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NotebookTest {
    @Test
    public void funnyTest() {
        Notebook notebook = new Notebook();
        notebook.add("alphabet1", "abc");
        notebook.add("alphabet2", "def");
        notebook.add("alphabet3", "ghi");
        notebook.remove("alphabet2");
        Assertions.assertDoesNotThrow(() -> NotebookJSON.writeNotebook(notebook));
    }
}