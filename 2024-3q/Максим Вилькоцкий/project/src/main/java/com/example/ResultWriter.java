package com.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ResultWriter {
    private final String outputPath;
    private final String prefix;
    private final boolean appendMode;

    public ResultWriter(String outputPath, String prefix, boolean appendMode) {
        this.outputPath = outputPath;
        this.prefix = prefix;
        this.appendMode = appendMode;
    }

    public void writeResults(List<Integer> integers, List<Double> floats, List<String> strings) throws IOException {
        if (!integers.isEmpty()) {
            writeToFile(getFilePath("integers.txt"), integers);
        }
        if (!floats.isEmpty()) {
            writeToFile(getFilePath("floats.txt"), floats);
        }
        if (!strings.isEmpty()) {
            writeToFile(getFilePath("strings.txt"), strings);
        }
    }

    private String getFilePath(String fileName) {
        return Paths.get(outputPath, prefix + fileName).toString();
    }

    private <T> void writeToFile(String filePath, List<T> data) throws IOException {
        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, appendMode))) {
            for (T item : data) {
                writer.write(item.toString());
                writer.newLine();
            }
        }
    }
}