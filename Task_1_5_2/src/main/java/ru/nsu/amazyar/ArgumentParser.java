package ru.nsu.amazyar;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class ArgumentParser {
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
        OPTION_SHOW.setArgs(3);
        OPTION_SHOW.setOptionalArg(false);

        OPTIONS = new Options();
        OPTIONS.addOption(OPTION_ADD);
        OPTIONS.addOption(OPTION_REMOVE);
        OPTIONS.addOption(OPTION_SHOW);
    }

    public static CommandLine parseArguments(String[] args) throws ParseException {
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine;

        commandLine = commandLineParser.parse(OPTIONS, args);
        return commandLine;
    }

    public static void executeCommandLine(CommandLine commandLine, Notebook notebook){
        if(commandLine.hasOption(OPTION_ADD)){
            String[] values = commandLine.getOptionValues(OPTION_ADD);


        }
        if(commandLine.hasOption(OPTION_REMOVE)){
            String[] values = commandLine.getOptionValues(OPTION_REMOVE);
        }
        if(commandLine.hasOption(OPTION_SHOW)){
            String[] values = commandLine.getOptionValues(OPTION_SHOW);
        }
    }
}
