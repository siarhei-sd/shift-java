package org.example;

import org.apache.commons.cli.*;

import java.util.*;

public class UtilCLI {
    private Options options;
    private CommandLineParser parser;
    private CommandLine cmd;

    private List<String> inputFiles;
    private String outputPath;
    private String outputPrefix;
    private boolean appendMode;
    private String statisticsType;

    public UtilCLI() {
        this.options = new Options();
        this.parser = new DefaultParser();

        setupOptions();
    }

    private void setupOptions() {
        options.addOption("o", "output", true, "Output path");
        options.addOption("p", "prefix", true, "Output file prefix");
        options.addOption("a", "append", false, "Append to existing files");
        options.addOption("s", "short", false, "Short statistics");
        options.addOption("f", "full", false, "Full statistics");
    }

    public void parseArguments(String[] args) {
        try {
            cmd = parser.parse(options, args);

            inputFiles = cmd.getArgList();

            if (cmd.hasOption("o")) {
                outputPath = cmd.getOptionValue("o");
            } else {
                outputPath = ".";
            }

            if (cmd.hasOption("p")) {
                outputPrefix = cmd.getOptionValue("p");
            } else {
                outputPrefix = "";
            }

            appendMode = cmd.hasOption("a");

            if (cmd.hasOption("f")) {
                statisticsType = "-f";
            } else {
                statisticsType = "-s";
            }

        } catch (ParseException e) {
            System.err.println("Error parsing command line arguments: " + e.getMessage());
            System.exit(1);
        }
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getOutputPrefix() {
        return outputPrefix;
    }

    public boolean isAppendMode() {
        return appendMode;
    }

    public String getStatisticsType() {
        return statisticsType;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
