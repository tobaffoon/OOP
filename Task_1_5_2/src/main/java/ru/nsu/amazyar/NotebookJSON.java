package ru.nsu.amazyar;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileWriter;
import java.io.IOException;

public class NotebookJSON {
    private static final String defaultFile = "notebook.json";

    public static void writeNotebook(Notebook notebook) throws IOException{
        writeNotebook(notebook, defaultFile);
    }

    public static void writeNotebook(Notebook notebook, String out) throws IOException {
        try(FileWriter writer = new FileWriter(out)){
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(writer, notebook);
        } catch (IOException e){
            throw new IOException();
        }
    }
}
