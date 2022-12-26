package ru.nsu.amazyar;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class.
 */
class NotebookTest {

    static Notebook notebook = new Notebook();
    static Notebook buffer = new Notebook();

    /**
     * Initialise notebook.json file.
     */
    @BeforeAll
    public static void initTests() {
        Notebook fillFile = new Notebook();
        fillFile.add("First note", "My first words");
        fillFile.add("Second note", "I am able to construct more complex sentences");
        fillFile.add("Third note", "I feel the urge to sleep");
        Assertions.assertDoesNotThrow(() -> NotebookJSON.writeNotebook(fillFile));
    }

    /**
     * Save json file before tests.
     */
    @BeforeEach
    public void saveJSON() {
        Assertions.assertDoesNotThrow(() -> NotebookJSON.readNotebook(buffer));
    }

    /**
     * Restore json file after tests.
     */
    @AfterEach
    public void restoreJSON() {
        Assertions.assertDoesNotThrow(() -> NotebookJSON.writeNotebook(buffer));
    }

    /**
     * Test for general notebook class functionality,
     */
    @Test
    public void notebookTest() {
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

    /**
     * Test for notebook IO functionality.
     */
    @Test
    public void readWriteTest() {
        Assertions.assertDoesNotThrow(() -> NotebookJSON.readNotebook(notebook));

        notebook.add("New Note", "New info");
        Assertions.assertDoesNotThrow(() -> NotebookJSON.writeNotebook(notebook));

        Notebook nextNotebook = new Notebook();
        Assertions.assertDoesNotThrow(() -> NotebookJSON.readNotebook(nextNotebook));

        Assertions.assertEquals(notebook.toString(), nextNotebook.toString());
    }

    /**
     * Test for parse arguments functionality.
     */
    @Test
    public void commandLineTest() {
        Assertions.assertDoesNotThrow(
            () -> Main.main(new String[] {"-add", "\"New edit\"", "\"New content\""}));
        Assertions.assertDoesNotThrow(
            () -> Main.main(new String[] {"-add", "\"New edit\"", "\"Old content\""}));
        Assertions.assertDoesNotThrow(() -> Main.main(new String[] {"-remove", "\"New edit\""}));

        ByteArrayOutputStream showOutputByteArray = new ByteArrayOutputStream();
        PrintStream showOutputStream = new PrintStream(showOutputByteArray);
        System.setOut(showOutputStream);
        Assertions.assertDoesNotThrow(() -> Main.main(new String[] {"-show"}));
        String showOutput = showOutputByteArray.toString();

        Notebook reference = new Notebook();
        Assertions.assertDoesNotThrow(() -> NotebookJSON.readNotebook(reference));

        Assertions.assertEquals(reference.toString(), showOutput);

        reference.remove("\"New edit\"");
        showOutputByteArray.reset();
        Assertions.assertDoesNotThrow(
            () -> Main.main(
                new String[] {"-show", "\"14.12.2019 7:00\"", "\"17.12.2023 13:00\"", "\"Note\""}));
        showOutput = showOutputByteArray.toString();
        Assertions.assertEquals(reference.toString().replace("\r", ""),
            showOutput.replace("\r", ""));

        reference.remove("First note");
        reference.remove("Second note");
        showOutputByteArray.reset();
        Assertions.assertDoesNotThrow(
            () -> Main.main(
                new String[] {"-show", "\"14.12.2019 7:00\"", "\"17.12.2023 13:00\"", "\"Note\"",
                    "\"third\""}));
        showOutput = showOutputByteArray.toString();
        Assertions.assertEquals(reference.toString().replace("\r", ""),
            showOutput.replace("\r", ""));
    }

    /**
     * Wrong arguments exceptions test.
     */
    @Test
    public void wrongArgumentsTest() {
        Assertions.assertThrows(MissingArgumentException.class,
            () -> Main.main(new String[] {"-add"}));
        Assertions.assertThrows(
            MissingArgumentException.class, () -> Main.main(new String[] {"-add", "\"Only name\""}));
        Assertions.assertThrows(MissingArgumentException.class,
            () -> Main.main(new String[] {"-r"}));
        Assertions.assertDoesNotThrow(() -> Main.main(new String[] {"-s", "\"14.12.2023 7:00\""}));
        Assertions.assertThrows(
            ParseException.class, () -> Main.main(new String[] {"-x", "\"14.12.2019 7:00\""}));
    }
}