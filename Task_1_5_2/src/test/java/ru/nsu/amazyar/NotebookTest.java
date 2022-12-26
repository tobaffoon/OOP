package ru.nsu.amazyar;

import java.io.IOException;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotebookTest {
    Notebook notebook;

    @BeforeEach
    public void initTests() throws IOException {
        notebook = new Notebook();

        Notebook fillFile = new Notebook();
        fillFile.add("First note", "My first words");
        fillFile.add("Second note", "I am able to construct more complex sentences");
        fillFile.add("Third note", "I feel the urge to sleep");
        NotebookJSON.writeNotebook(fillFile);
    }

    @Test
    public void funnyTest() throws ParseException, IOException {
        Main.main(new String[]{"-show"});
    }
}