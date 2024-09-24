package com.sheremet.shift.utils;

import com.sheremet.shift.processor.ProcessFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileFilter {
    private static final Logger logger = LogManager.getLogger(FileFilter.class);

    public static void main(String[] args) {
        List<String> inputFiles = new ArrayList<>();
        String outputPath = ".";
        String prefix = "";

        boolean append = false;
        boolean fullStats = false;
        boolean shortStats = false;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    outputPath = args[++i];
                    break;
                case "-p":
                    prefix = args[++i];
                    break;
                case "-a":
                    append = true;
                    break;
                case "-s":
                    shortStats = true;
                    break;
                case "-f":
                    fullStats = true;
                    break;
                default:
                    inputFiles.add(args[i]);
                    break;
            }
        }

        try {
            ProcessFile processor = new ProcessFile(outputPath,
                    prefix,
                    append,
                    fullStats,
                    shortStats);
            long startTime = System.nanoTime();

            processor.processFiles(inputFiles);
            long endTime = System.nanoTime();

            long duration = (endTime - startTime); // Время выполнения в наносекундах
            System.out.println("Execution time: " + duration / 1_000_000 + " milliseconds");

        } catch (IOException e) {
            logger.error("Error processing files: ", e);
            System.err.println("Error processing files: " + e.getMessage());
        }
    }
}