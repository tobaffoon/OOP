package ru.nsu.amazyar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jetbrains.annotations.NotNull;

/**
 * Parse command line arguments for notebook.
 * <p>
 * Uses Apache Commons CLI library
 * </p>
 */
public class NotebookArgumentParser {

    private static final Option OPTION_ADD;
    private static final Option OPTION_REMOVE;
    private static final Option OPTION_SHOW;

    private static final Options OPTIONS;

    static {
        OPTION_ADD = new Option("a", "add", true, "Add new note");
        OPTION_ADD.setArgs(2);
        OPTION_ADD.setOptionalArg(false);

        OPTION_REMOVE = new Option("r", "remove", true, "Remove a note by name");
        OPTION_REMOVE.setArgs(1);
        OPTION_REMOVE.setOptionalArg(false);

        OPTION_SHOW = new Option("s", "show", true, "Show contents of the notebook");
        OPTION_SHOW.setArgs(Option.UNLIMITED_VALUES);
        OPTION_SHOW.setOptionalArg(true);

        OPTIONS = new Options();
        OPTIONS.addOption(OPTION_ADD);
        OPTIONS.addOption(OPTION_REMOVE);
        OPTIONS.addOption(OPTION_SHOW);
    }

    /**
     * Parse String[] args to CommandLine.
     *
     * @param args arguments of a program
     * @return CommandLine object relevant to notebook program
     */
    public static CommandLine parseArguments(String[] args) throws ParseException {
        CommandLineParser commandLineParser = new DefaultParser();
        return commandLineParser.parse(OPTIONS, args);
    }

    /**
     * Executes notebook program with given arguments.
     *
     * @param commandLine CommandLine object with relevant arguments
     * @param notebook    notebook on which operations will be executed
     * @return true if notebook changed after operations, false otherwise
     */
    public static boolean executeCommandLine(
        @NotNull CommandLine commandLine,
        @NotNull Notebook notebook) {
        if (commandLine.hasOption(OPTION_ADD)) {
            String[] values = commandLine.getOptionValues(OPTION_ADD);

            return notebook.add(values[0], values[1]);
        }
        if (commandLine.hasOption(OPTION_REMOVE)) {
            String[] values = commandLine.getOptionValues(OPTION_REMOVE);

            return notebook.remove(values[0]);
        }
        if (commandLine.hasOption(OPTION_SHOW)) {
            String[] values = commandLine.getOptionValues(OPTION_SHOW);

            if (values == null) {
                System.out.print(notebook);
            } else {
                DateTimeFormatter notebookFormat = notebook.getDateFormatter();
                LocalDateTime from = LocalDateTime.parse(values[0], notebookFormat);
                LocalDateTime to;
                if(values.length == 1) {
                    to = LocalDateTime.now();
                } else{
                    to = LocalDateTime.parse(values[1], notebookFormat);
                }

                // first two arguments are time limits, next comes arbitrary big array of keywords
                String[] keywords = Arrays.copyOfRange(values, 2, values.length);

                for (Note note : notebook.getNotes()) {
                    if (note.isBetween(from, to) && note.nameContains(keywords)) {
                        System.out.println(note);
                        System.out.println();
                    }
                }
            }

            return false;
        }

        return false;
    }
}
