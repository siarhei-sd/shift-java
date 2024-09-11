package com.filter.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class FileProcessor {
    private Path outputPath = Paths.get(".");
    private String prefix = "";
    private boolean append = false;
    private boolean fullStats = false;

    private final Map<DataType, Statistics> statisticsMap = new EnumMap<>(DataType.class);

    public FileProcessor() {
        // Инициализируем статистику для каждого типа данных
        statisticsMap.put(DataType.INTEGER, new Statistics());
        statisticsMap.put(DataType.FLOAT, new Statistics());
        statisticsMap.put(DataType.STRING, new Statistics());
    }

    public void parseArgs(String[] args) {
        List<String> files = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o" -> outputPath = Paths.get(args[++i]);
                case "-p" -> prefix = args[++i];
                case "-a" -> append = true;
                case "-s" -> fullStats = false;
                case "-f" -> fullStats = true;
                default -> files.add(args[i]);
            }
        }
        if (files.isEmpty()) {
            throw new IllegalArgumentException("No input files provided");
        }
        for (String file : files) {
            processFile(Paths.get(file));
        }
    }

    public void processFile(Path filePath) {
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            System.err.println("Failed to process file: " + filePath);
        }
    }

    public void processLine(String line) {
        if (line.matches("-?\\d+")) {
            processElement(line, DataType.INTEGER);
        } else if (line.matches("-?\\d*\\.\\d+(E-?\\d+)?")) {
            processElement(line, DataType.FLOAT);
        } else {
            processElement(line, DataType.STRING);
        }
    }

    private void processElement(String element, DataType type) {
        Statistics stats = statisticsMap.get(type);
        if (type == DataType.INTEGER || type == DataType.FLOAT) {
            stats.addNumericValue(Double.parseDouble(element));
        } else if (type == DataType.STRING) {
            stats.addStringValue(element);
        }
        writeToFile(type, element);
    }

    private void writeToFile(DataType type, String element) {
        Path filePath = outputPath.resolve(prefix + type.getFilename());
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, append ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(element);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to write to file: " + filePath);
        }
    }

    public void printStatistics() {
        statisticsMap.forEach((type, stats) -> {
            System.out.println(stats);
        });
    }

    public Statistics getStatistics(DataType type) {
        return statisticsMap.get(type);
    }
}