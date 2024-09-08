package org.example;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CommandOptions {

    public String outputPath;
    public String prefix;
    public boolean addMode;
    public boolean fullStatisticsElements;
    public boolean fullStatisticsFiles;
    public boolean shortStatisticsElements;
    public boolean shortStatisticsFiles;
    public List<String> inputFiles;
    public List<String> outputFiles;

    public CommandOptions(String[] args) {
        outputPath = "";
        prefix = "";
        addMode = false;
        fullStatisticsElements = false;
        fullStatisticsFiles = false;
        shortStatisticsElements = false;
        shortStatisticsFiles = false;

        inputFiles = new ArrayList<>();
        outputFiles = new ArrayList<>();
        System.out.println("----------------Options----------------");
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    outputPath = args[++i];
                    System.out.println("OutputPath: " + outputPath);
                    break;
                case "-p":
                    prefix = args[++i];
                    System.out.println("Prefix: " + prefix);
                    break;
                case "-a":
                    addMode = true;
                    System.out.println("AddMode: " + addMode);
                    break;
                case "-s":
                    shortStatisticsElements = true;
                    System.out.println("ShortStatisticsElements: " + shortStatisticsElements);
                    break;
                case "-sf":
                    shortStatisticsFiles = true;
                    System.out.println("ShortStatisticsFiles: " + shortStatisticsFiles);
                    break;
                case "-f":
                    fullStatisticsElements = true;
                    System.out.println("fullStatisticsElements: " + fullStatisticsElements);
                    break;
                case "-ff":
                    fullStatisticsFiles = true;
                    System.out.println("fullStatisticsFiles: " + fullStatisticsFiles);
                    break;
                default:
                    inputFiles.add(args[i]);
                    break;
            }
        }
        System.out.println("InputFiles: " + inputFiles);
        System.out.println("----------------Output------------------");
    }

    public String getFilePath(String filename) {
        return Paths.get(outputPath, prefix + filename).toString();
    }
}
