package ru.nsu.amazyar;

import java.io.IOException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

/**
 * Runs notebook program.
 */
public class Main {

    /**
     * Notebook program entry point.
     */
    public static void main(String[] args) throws ParseException, IOException {
        CommandLine mainCommandLine = NotebookArgumentParser.parseArguments(args);

        Notebook notebook = new Notebook();
        NotebookJson.readNotebook(notebook);

        boolean notebookChanged =
            NotebookArgumentParser.executeCommandLine(mainCommandLine, notebook);

        if (notebookChanged) {
            NotebookJson.writeNotebook(notebook);
        }
    }
}
