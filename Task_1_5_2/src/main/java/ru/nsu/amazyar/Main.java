package ru.nsu.amazyar;

import java.io.IOException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

public class Main {

    public static void main(String[] args) throws ParseException, IOException {
        CommandLine mainCommandLine = ArgumentParser.parseArguments(args);

        Notebook notebook = new Notebook();
        NotebookJSON.readNotebook(notebook);

        boolean notebookChanged = ArgumentParser.executeCommandLine(mainCommandLine, notebook);

        if(notebookChanged) {
            NotebookJSON.writeNotebook(notebook);
        }
    }
}
