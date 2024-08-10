package com.TestTask.parsing;

import java.util.ArrayList;
import java.util.List;

public class StartOptions {
    private final String[] args;

    public StartOptions(String[] args) {
        this.args = args;
        parseStartOption();
    }

    private final StringBuilder outPath = new StringBuilder();
    private final StringBuilder filePrefix = new StringBuilder();

    private boolean appendMode = false;
    private boolean fullStat = false;
    private boolean haseStat = false;

    private final List<String> files = new ArrayList<>();

    public String getOutPath() {
        return outPath.toString();
    }

    public String getFilePrefix() {
        return filePrefix.toString();
    }

    public boolean isAppendMode() {
        return appendMode;
    }

    public boolean isFullStat() {
        return fullStat;
    }

    public boolean isHaseStat() {
        return haseStat;
    }

    public List<String> getFiles() {
        return files;
    }

    public void parseStartOption() {

        /**парсим строку входную строку args*/

        for (int i = 0; i < args.length; i++) {
            if ("-o".equals(args[i])) {
                outPath.append(args[i + 1]);
            } else if ("-p".equals(args[i])) {
                filePrefix.append(args[i + 1]);
            } else if ("-a".equals(args[i])) {
                appendMode = true;
            } else if ("-s".equals(args[i])) {
                haseStat = true;
                fullStat = false;
            } else if ("-f".equals(args[i])) {
                haseStat = true;
                fullStat = true;
            } else if (args[i].endsWith(".txt")) {
                files.add(args[i]);
            } else if (args[i - 1].equals("-p") || args[i - 1].equals("-o")) {
                continue;
            } else {
                System.out.println();
                System.out.println("Error!!! The initial data is wrong: " + args[i]);
                System.out.println("Enter please a valid query");
            }
        }
    }

}
