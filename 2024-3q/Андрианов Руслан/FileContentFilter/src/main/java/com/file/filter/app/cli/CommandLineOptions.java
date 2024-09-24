package com.file.filter.app.cli;

import org.apache.commons.cli.*;

import java.util.List;

public class CommandLineOptions {
    private String prefix = "";
    private String path = "";
    private boolean append = false;
    private boolean shortStatistic = false;
    private boolean fullStatistic = false;
    private List<String> files;

    public CommandLineOptions(String[] inputArgs) {
        parseOptions(inputArgs);
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPath() {
        return path;
    }

    public boolean isAppend() {
        return append;
    }

    public boolean isShortStatistic() {
        return shortStatistic;
    }

    public boolean isFullStatistic() {
        return fullStatistic;
    }

    public List<String> getFiles() {
        return files;
    }

    private void parseOptions(String[] inputArgs) {
        Options options = new Options();

        options.addOption("h", "help", false, "show help");
        options.addOption("a", "append", false, "appends results in the existing file");
        options.addOption("o", "path", true, "set path to the output files");
        options.addOption("p", "prefix", true, "set prefix to the files names");
        options.addOption("s", "small", false, "show small statistic");
        options.addOption("f", "full", false, "show full statistic");

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, inputArgs);

            if (cmd.hasOption("h")) {
                formatter.printHelp("-a -s -f -p prefix -o /path/for/output/ /path/to/file.txt", options);
                System.exit(0); // exit after help is shown
            }

            append = cmd.hasOption("a");
            shortStatistic = cmd.hasOption("s");
            fullStatistic = cmd.hasOption("f");
            path = cmd.getOptionValue("o", "");
            prefix = cmd.getOptionValue("p", "");
            files = cmd.getArgList();

            if (files.isEmpty()) {
                System.out.println("No files provided");
                formatter.printHelp("-a -s -f -p prefix -o /path/for/output/ /path/to/file.txt", options);
                System.exit(0);
            }

        } catch (ParseException e) {
            System.out.println("Failed to parse command line arguments or wrong options were found");
            formatter.printHelp("-a -s -f -p prefix -o /path/for/output/ /path/to/file.txt", options);
            System.exit(0); // exit after help is shown
        }
    }
}
