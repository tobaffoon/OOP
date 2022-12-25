package ru.nsu.amazyar;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NotebookTest {
    @Test
    public void writeTest() {
        Notebook notebook = new Notebook();
        notebook.add("alphabet1", "abc");
        notebook.add("alphabet2", "def");
        notebook.add("alphabet3", "ghi");
        notebook.remove("alphabet2");
        Assertions.assertDoesNotThrow(() -> NotebookJSON.writeNotebook(notebook));
    }

    @Test
    public void readTest() throws IOException {
        Notebook notebook = new Notebook();
        NotebookJSON.readNotebook(notebook);
        Assertions.assertTrue(notebook.find("alphabet1") != null && notebook.find("alphabet3") != null);
    }
}