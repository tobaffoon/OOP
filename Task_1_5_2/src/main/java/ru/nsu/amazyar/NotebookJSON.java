package ru.nsu.amazyar;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class NotebookJSON {
    private static final String defaultFile = "notebook.json";
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static void writeNotebook(Notebook notebook) throws IOException {
        File outFile = new File(defaultFile);
        objectMapper.writeValue(outFile, notebook.getNotes());
    }

    public static void readNotebook(Notebook notebook) throws IOException{
        File inFile = new File(defaultFile);
        List<Note> notes = objectMapper.readValue(inFile, new TypeReference<>(){});
        for (Note note : notes) {
            notebook.add(note);
        }
    }
}
