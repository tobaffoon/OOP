package ru.nsu.amazyar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
}
