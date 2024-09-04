package com.filefilterutility;

import java.util.ArrayList;
import java.util.List;

public class FileFilterUtility {
    public static void main(String[] args) {

        String outputPath = ".";
        String prefix = "";
        boolean append = false;
        boolean fullStatistics = false;

        List<String> inputFiles = new ArrayList<>();

        // Process command line arguments
        try {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-o":
                        if (i + 1 < args.length) {
                            outputPath = args[++i];
                        } else {
                            throw new IllegalArgumentException("Option -o requires a path argument.");
                        }
                        break;
                    case "-p":
                        if (i + 1 < args.length) {
                            prefix = args[++i];
                        } else {
                            throw new IllegalArgumentException("Option -p requires a prefix argument.");
                        }
                        break;
                    case "-a":
                        append = true;
                        break;
                    case "-f":
                        fullStatistics = true;
                        break;
                    case "-s":
                        fullStatistics = false;
                        break;
                    default:
                        if (args[i].startsWith("-")) {
                            throw new IllegalArgumentException("Unknown option: " + args[i]);
                        } else {
                            inputFiles.add(args[i]);
                        }
                        break;
                }
            }

            if (inputFiles.isEmpty()) {
                throw new IllegalArgumentException("No input files provided.");
            }

            FileDataFilter dataFilter = new FileDataFilter(outputPath, prefix, append, fullStatistics);
            dataFilter.processFiles(inputFiles);

        } catch (IllegalArgumentException e) {
            System.err.println("Command line argument error: " + e.getMessage());
            printUsage();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar util.jar [options] [input files]");
        System.out.println("Options:");
        System.out.println("  -o <path>        Specify the output path for result files.");
        System.out.println("  -p <prefix>      Specify the prefix for result file names.");
        System.out.println("  -a               Append to existing result files instead of overwriting.");
        System.out.println("  -f               Show full statistics.");
        System.out.println("  -s               Show short statistics.");
    }

}
