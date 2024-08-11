import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class FileProcessor {
    private List<String> inputFiles;
    private String outputPath;
    private String prefix;
    private boolean append;
    private boolean fullStatistics;

    private List<String> strings = new ArrayList<>();
    private List<Integer> integers = new ArrayList<>();
    private List<Double> floats = new ArrayList<>();

    public FileProcessor(List<String> inputFiles, String outputPath, String prefix, boolean append, boolean fullStatistics) {
        this.inputFiles = inputFiles;
        this.outputPath = outputPath;
        this.prefix = prefix;
        this.append = append;
        this.fullStatistics = fullStatistics;
    }

    public void processFiles() {
        for (String inputFile : inputFiles) {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    processLine(line);
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + inputFile + ". " + e.getMessage());
            }
        }

        writeOutputFiles();
        printStatistics();
    }

    private void processLine(String line) {
        for (String token : line.split("\\s+")) {
            if (isInteger(token)) {
                integers.add(Integer.parseInt(token));
            } else if (isDouble(token)) {
                floats.add(Double.parseDouble(token));
            } else {
                strings.add(token);
            }
        }
    }

    private boolean isInteger(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void writeOutputFiles() {
        writeToFile(strings, "strings.txt");
        writeToFile(integers, "integers.txt");
        writeToFile(floats, "floats.txt");
    }

    private void writeToFile(List<?> data, String fileName) {
        if (data.isEmpty()) {
            return;
        }
        Path filePath = Paths.get(outputPath, prefix + fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, append ? StandardOpenOption.APPEND : StandardOpenOption.CREATE)) {
            for (Object item : data) {
                writer.write(item.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + fileName + ". " + e.getMessage());
        }
    }

    private void printStatistics() {
        printStatisticsForType("integers.txt", integers);
        printStatisticsForType("floats.txt", floats);
        printStatisticsForType("strings.txt", strings);
    }

    private <T> void printStatisticsForType(String fileName, List<T> data) {
        if (data.isEmpty()) {
            return;
        }
        Statistics<T> stats = new Statistics<>(data);
        String statsString = fullStatistics ? stats.getFullStatistics() : stats.getShortStatistics();
        System.out.println(fileName + " " + statsString);
    }
}
