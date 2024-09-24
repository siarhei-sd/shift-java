package com.sheremet.shift.processor;


import com.sheremet.shift.statistics.Statistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ProcessFile {
    private static final Logger logger = LogManager.getLogger(ProcessFile.class);
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^-?\\d+$");
    private static final Pattern FLOAT_PATTERN = Pattern.compile("^-?\\d*\\.\\d+(?:[eE][+-]?\\d+)?$");

    private final String outputPath;
    private final String prefix;
    private final boolean append;
    private final boolean fullStats;
    private final boolean shortStats;

    public ProcessFile(String outputPath,
                       String prefix,
                       boolean append,
                       boolean fullStats,
                       boolean shortStats
                         ) {
        this.outputPath = outputPath;
        this.prefix = prefix;
        this.append = append;
        this.fullStats = fullStats;
        this.shortStats = shortStats;
    }

    public void processFiles(List<String> inputFiles) throws IOException {
        List<String> integers = new ArrayList<>();
        List<String> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        for (String inputFile : inputFiles) {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (INTEGER_PATTERN.matcher(line).matches()) {
                        integers.add(line);
                    } else if (FLOAT_PATTERN.matcher(line).matches()) {
                        floats.add(line);
                    } else {
                        strings.add(line);
                    }
                }

            } catch (IOException e) {
                logger.error("Error reading file " + inputFile, e);
            }

        }

        writeToFile(integers, "integers.txt");
        writeToFile(floats, "floats.txt");
        writeToFile(strings, "strings.txt");

        Statistics.printStatistics(integers, floats, strings, fullStats, shortStats);
    }

    private void writeToFile(List<String> data, String fileName) throws IOException {
        if (data.isEmpty()) return;

        Path filePath = Paths.get(outputPath, prefix + fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, append ? StandardOpenOption.APPEND : StandardOpenOption.CREATE)) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}