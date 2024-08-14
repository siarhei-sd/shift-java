package ru.shift;

import java.util.ArrayList;
import java.util.List;

public class CommandLineOptions {
    private String outputDir = ".";
    private String prefix = "";
    private boolean append = false;
    private boolean fullStatistics = false;
    private List<String> inputFiles = new ArrayList<>();

    public CommandLineOptions (String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length) outputDir = args[++i];
                    break;
                case "-p":
                    if (i + 1 < args.length) prefix = args[++i];
                    break;
                case "-a":
                    append = true;
                    break;
                case "-s":
                    fullStatistics = false;
                    break;
                case "-f":
                    fullStatistics = true;
                    break;
                default:
                    inputFiles.add(args[i]);
                    break;
            }
        }
    }

    public String getOutputDir() {
        return outputDir;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isAppend() {
        return append;
    }

    public boolean isFullStatistics() {
        return fullStatistics;
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }
}
