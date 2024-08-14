package ru.shift;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileProcessor {
    private CommandLineOptions options;
    private Map<String, List<String>> data;
    private StatisticsCollector statsCollector;

    public FileProcessor(CommandLineOptions options) {
        this.options = options;
        this.data = new HashMap<>();
        this.data.put("integers", new ArrayList<>());
        this.data.put("floats", new ArrayList<>());
        this.data.put("strings", new ArrayList<>());
        this.statsCollector = new StatisticsCollector();
    }

    public void processFile(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filePath));
        for (String line : lines) {
            categorizeAndStore(line);
        }
    }

    private void categorizeAndStore(String line) {
        if (line.matches("-?\\d+")) {
            data.get("integers").add(line);
            statsCollector.addInteger(Integer.parseInt(line));
        } else if (line.matches("-?\\d*\\.\\d+([eE]-?\\d+)?")) {
            data.get("floats").add(line);
            statsCollector.addFloat(Double.parseDouble(line));
        } else {
            data.get("strings").add(line);
            statsCollector.addString(line);
        }
    }

    public void writeResults() {
        writeToFile("integers", data.get("integers"));
        writeToFile("floats", data.get("floats"));
        writeToFile("strings", data.get("strings"));
    }

    private void writeToFile(String type, List<String> content) {
        if (content.isEmpty())
            return;

        String fileName = options.getOutputDir() + "/" + options.getPrefix() + type + ".txt";
        Path filePath = Paths.get(fileName);

        try {
            Files.createDirectories(filePath.getParent());
        } catch (IOException e) {
            System.err.println("Error creating directories for file: " + fileName);
            e.printStackTrace();
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, options.isAppend()))) {
            for (String line : content) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + fileName);
            e.printStackTrace();
        }
    }

    public void printStatistics() {
        if (options.isFullStatistics()) {
            statsCollector.printFullStatistics(options.getPrefix());
        } else {
            statsCollector.printShortStatistics(options.getPrefix());
        }
    }
}
