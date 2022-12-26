package ru.nsu.amazyar;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotebookTest {
    Notebook notebook;

    @BeforeEach
    public void initTests(){
        notebook = new Notebook();

        Notebook fillFile = new Notebook();
        fillFile.add("First note", "My first words");
        fillFile.add("Second note", "I am able to construct more complex sentences");
        fillFile.add("Third note", "I feel the urge to sleep");
        Assertions.assertDoesNotThrow(() -> NotebookJSON.writeNotebook(fillFile));
    }

    @Test
    public void funnyTest() throws ParseException, IOException {
        Main.main(new String[]{"-show"});
    }

    @Test
    public void notebookTest(){
        Assertions.assertDoesNotThrow(() -> NotebookJSON.readNotebook(notebook));

        List<Note> notes = notebook.getNotes();
        Assertions.assertFalse(notes.isEmpty());

        Assertions.assertNotNull(notebook.find("First note"));

        Note firstNote = notebook.find("First note");
        firstNote.setName("Changed Note");
        firstNote.setContent("I am no longer able to write complex sentences");
        firstNote.setCreationTime(LocalDateTime.now());
        Assertions.assertNull(notebook.find("First note"));

        Assertions.assertFalse(notebook.add("Third note", "Did I make third note already?"));
        Assertions.assertTrue(notebook.add("Forth note", "I have more info"));
        Note someNote = new Note("Second note", "Nothing new", LocalDateTime.now());
        Assertions.assertFalse(notebook.add(someNote));
        someNote.setName("New cool name");
        Assertions.assertTrue(notebook.add(someNote));

        Assertions.assertFalse(notebook.remove("Fifth note"));
        Assertions.assertTrue(notebook.remove("Forth note"));

        Assertions.assertEquals(4, notes.size());
    }
}