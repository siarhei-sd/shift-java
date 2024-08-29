package Test;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class FileProcessor {
    private final Path outputPath;
    private final String prefix;
    private final boolean append;
    private final boolean fullStatistic;
    private final Map<DataType, BufferedWriter> writers;
    private final Map<DataType, Statistic> statistics;

    public FileProcessor(Path outputPath, String prefix, boolean append, boolean fullStatistic) {
        this.outputPath = outputPath;
        this.prefix = prefix;
        this.append = append;
        this.fullStatistic = fullStatistic;
        this.writers = new HashMap<>();
        this.statistics = new HashMap<>();
    }

    public void processFiles(List<Path> inputFiles) {
        for (Path inputFile : inputFiles) {
            try (BufferedReader reader = Files.newBufferedReader(inputFile)) {
                String line;
                while ((line = reader.readLine()) != null) {
                	if (isInteger(line)) {
                        writeData(line, DataType.INTEGER);
                        getStatistic(DataType.INTEGER).addNumber(Double.parseDouble(line));
                    } else if (isFloat(line)) {
                        writeData(line, DataType.FLOAT);
                        getStatistic(DataType.FLOAT).addNumber(Double.parseDouble(line));
                    } else {
                        writeData(line, DataType.STRING);
                        getStatistic(DataType.STRING).addString(line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading file " + inputFile + ": " + e.getMessage());
            }
        }
        for (BufferedWriter writer : writers.values()) {
            try {
                writer.close();
            } catch (IOException e) {
                System.err.println("Error closing writer: " + e.getMessage());
            }
        }
        printStatistics();
    }
    private Statistic getStatistic(DataType type) {
        return statistics.computeIfAbsent(type, k -> new Statistic());
    }
    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFloat(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void writeData(String data, DataType type) {
        try {
            BufferedWriter writer = getWriter(type);
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing data: " + e.getMessage());
        }
    }

    private BufferedWriter getWriter(DataType type) throws IOException {
        if (!writers.containsKey(type)) {
            String fileName = prefix + type.name() + ".txt";
            Path filePath = outputPath.resolve(fileName);
            if (!Files.exists(filePath)) Files.createFile(filePath);
            BufferedWriter writer = Files.newBufferedWriter(filePath,
                    StandardOpenOption.CREATE,
                    append ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING);
            writers.put(type, writer);
        }
        return writers.get(type);
    }

    private void printStatistics() {
        for (Map.Entry<DataType, Statistic> entry : statistics.entrySet()) {
            String fileName = prefix + entry.getKey().name().toLowerCase() + ".txt";
            if (fullStatistic) {
                entry.getValue().printFullStatistic(fileName, entry.getKey());
            } else {
                entry.getValue().printShortStatistic(fileName);
            }
        }
    }
}

