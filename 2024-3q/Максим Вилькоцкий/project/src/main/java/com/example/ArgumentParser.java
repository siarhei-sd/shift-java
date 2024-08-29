package com.example;

import org.apache.commons.cli.*;

public class ArgumentParser {
    private CommandLine cmd;
    private String[] inputFiles;

    public ArgumentParser(String[] args) throws ParseException {
        Options options = new Options();

        options.addOption("o", "output", true, "Output directory path");
        options.addOption("p", "prefix", true, "Output files prefix");
        options.addOption("a", "append", false, "Append to existing files");
        options.addOption("s", "short-stats", false, "Show short statistics");
        options.addOption("f", "full-stats", false, "Show full statistics");

        CommandLineParser parser = new DefaultParser();
        cmd = parser.parse(options, args);

        inputFiles = cmd.getArgs();
        if (inputFiles.length == 0) {
            throw new ParseException("No input files specified");
        }
    }

    public String getOutputPath() {
        return cmd.getOptionValue("o", ".");
    }

    public String getPrefix() {
        return cmd.getOptionValue("p", "");
    }

    public boolean isAppendMode() {
        return cmd.hasOption("a");
    }

    public boolean isShortStats() {
        return cmd.hasOption("s");
    }

    public boolean isFullStats() {
        return cmd.hasOption("f");
    }

    public String[] getInputFiles() {
        return inputFiles;
    }
}