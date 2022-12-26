package ru.nsu.amazyar;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * IO operations for Notebook using JSON.
 * <p>
 * Uses Jackson module
 * </p>
 */
public class NotebookJSON {

    private static final String defaultFile = "notebook.json";
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Write Notebook as JSON to defaultFile.
     *
     * @param notebook written notebook
     */
    public static void writeNotebook(Notebook notebook) throws IOException {
        File outFile = new File(defaultFile);
        objectMapper.writeValue(outFile, notebook.getNotes());
    }

    /**
     * Read Notebook from JSON defaultFile.
     * <p>
     * Method clears existing notes of notebook and replaces them with new ones
     * </p>
     * <p>
     * Method sorts read notes to preserve ascending order of dates.
     * </p>
     * @param notebook notebook in which read occurs
     */
    public static void readNotebook(Notebook notebook) throws IOException {
        File inFile = new File(defaultFile);
        List<Note> notes = objectMapper.readValue(inFile, new TypeReference<>() {
        });
        notes.sort(Comparator.comparing(Note::getCreationTime));
        for (Note note : notes) {
            notebook.add(note);
        }
    }
}
