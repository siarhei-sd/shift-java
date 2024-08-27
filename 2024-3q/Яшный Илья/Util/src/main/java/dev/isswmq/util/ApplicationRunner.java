package dev.isswmq.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ApplicationRunner {

    private static final String DEFAULT_PATH = ".";
    private static final String DEFAULT_PREFIX = "";
    private static final String PATH_TO_INTEGERS = "integers.txt";
    private static final String PATH_TO_FLOATS = "floats.txt";
    private static final String PATH_TO_STRINGS = "strings.txt";

    private static String outputPath = DEFAULT_PATH;
    private static String prefix = DEFAULT_PREFIX;
    private static boolean appendMode = false;
    private static boolean fullStats = false;

    private static final String FLOAT_REGEX = "[+-]?([0-9]*[.])[0-9]+([Ee][+-]?[0-9]+)?";
    private static final String INTEGER_REGEX = "[+-]?\\d+";


    public static void main(String[] args) {
        if (!parseArgs(args)) {
            return;
        }

        List<String> pathToFiles = Arrays.stream(args)
                .filter(arg -> !arg.startsWith("-") && arg.endsWith(".txt"))
                .collect(Collectors.toList());

        if(pathToFiles.isEmpty()) {
            System.out.println("No input files specified");
            return;
        }

        startup(pathToFiles);
    }

    private static boolean parseArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length) {
                        outputPath = args[++i];
                        if (outputPath.isEmpty()) {
                            System.out.println("Output path is empty.");
                            return false;
                        }
                    } else {
                        System.out.println("Output path is not specified.");
                        return false;
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length) {
                        prefix = args[++i];
                    } else {
                        System.out.println("Prefix not specified.");
                        return false;
                    }
                    break;
                case "-a":
                    appendMode = true;
                    break;
                case "-s":
                    fullStats = false;
                    break;
                case "-f":
                    fullStats = true;
                    break;
            }
        }
        return true;
    }


    public static void startup(List<String> pathToFiles) {
        ExecutorService executorService = Executors.newFixedThreadPool(pathToFiles.size());

        List<Future<FileStatistics>> futures = pathToFiles.stream()
                .map(pathToFile -> executorService.submit(() -> processFile(pathToFile)))
                .collect(Collectors.toList());

        executorService.shutdown();
        try {
            FileStatistics overallStats = new FileStatistics();
            for (Future<FileStatistics> future : futures) {
                overallStats.add(future.get());
            }
            printShortStatistics(overallStats);
            if (fullStats) {
                printFullStatistics(overallStats);
            }
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private static FileStatistics processFile(String pathToFile) {
        List<String> integers = new ArrayList<>();
        List<String> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        long integerCount = 0;
        long floatCount = 0;
        long stringCount = 0;

        double minInteger = Double.MAX_VALUE;
        double maxInteger = Double.MIN_VALUE;
        double minFloat = Double.MAX_VALUE;
        double maxFloat = Double.MIN_VALUE;

        double integerSum = 0.0;

        FileStatistics fileStats = new FileStatistics();

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    if (isFloat(line)) {
                        floats.add(line);
                        floatCount++;
                        double value = Double.parseDouble(line);
                        if (value < minFloat) minFloat = value;
                        if (value > maxFloat) maxFloat = value;
                    } else if (isInteger(line)) {
                        integers.add(line);
                        integerCount++;
                        double value = Double.parseDouble(line);
                        if (value < minInteger) minInteger = value;
                        if (value > maxInteger) maxInteger = value;
                        integerSum += value;
                    } else {
                        strings.add(line);
                        stringCount++;
                        fileStats.updateStringLengthStats(line);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error processing file: " + pathToFile);
            e.printStackTrace();
        }

        writeDataToFile(integers, floats, strings);

        return new FileStatistics(integerCount, floatCount, stringCount,
                minInteger, maxInteger, minFloat, maxFloat,
                integerSum, fileStats.getMinStringLength(),
                fileStats.getMaxStringLength());
    }

    private static void writeDataToFile(List<String> integers, List<String> floats, List<String> strings) {
        ExecutorService writeExecutor = Executors.newFixedThreadPool(3);
        List<Callable<Void>> writeTasks = Arrays.asList(
                () -> {
                    writeToFile(integers, getOutputFilePath(PATH_TO_INTEGERS));
                    return null;
                },
                () -> {
                    writeToFile(floats, getOutputFilePath(PATH_TO_FLOATS));
                    return null;
                },
                () -> {
                    writeToFile(strings, getOutputFilePath(PATH_TO_STRINGS));
                    return null;
                }
        );

        try {
            List<Future<Void>> futures = writeExecutor.invokeAll(writeTasks);
            for (Future<Void> future : futures) {
                future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } finally {
            writeExecutor.shutdown();
        }
    }

    private static String getOutputFilePath(String defaultFileName) {
        Path outputDir = Paths.get(System.getProperty("user.dir"), outputPath);
        if (!Files.exists(outputDir)) {
            try {
                Files.createDirectories(outputDir);
            } catch (IOException e) {
                System.err.println("Error creating directories: " + outputDir);
                e.printStackTrace();
            }
        }
        return outputDir.resolve(prefix + defaultFileName).toString();
    }

    private static void writeToFile(List<String> data, String filePath) {
        if (data.isEmpty()) {
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, appendMode))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filePath);
            e.printStackTrace();
        }
    }

    private static void printShortStatistics(FileStatistics stats) {
        System.out.println("Short statistics:");
        System.out.printf("Integers: %d%n", stats.getIntegerCount());
        System.out.printf("Floats: %d%n", stats.getFloatCount());
        System.out.printf("Strings: %d%n", stats.getStringCount());
    }

    private static void printFullStatistics(FileStatistics stats) {
        System.out.println("Full statistics:");
        if (stats.getIntegerCount() > 0) {
            System.out.printf("Integers: %d; Min = %.0f; Max = %.0f; Sum = %.0f; Average = %.0f%n",
                    stats.getIntegerCount(), stats.getMinInteger(), stats.getMaxInteger(), stats.getIntegerSum(),
                    stats.getIntegerSum() / stats.getIntegerCount());
        }
        if (stats.getFloatCount() > 0) {
            System.out.printf("Floats: %d; Min = %.2f; Max = %.2f%n",
                    stats.getFloatCount(), stats.getMinFloat(), stats.getMaxFloat());
        }
        if (stats.getStringCount() > 0) {
            System.out.printf("Strings: %d; Min Length = %d; Max Length = %d%n",
                    stats.getStringCount(), stats.getMinStringLength(), stats.getMaxStringLength());
        }
    }

    private static boolean isInteger(String s) {
        return s.matches(INTEGER_REGEX) && !s.matches(FLOAT_REGEX);
    }

    private static boolean isFloat(String s) {
        return s.matches(FLOAT_REGEX) && !s.matches(INTEGER_REGEX);
    }


static class FileStatistics {
    private long integerCount;
    private long floatCount;
    private long stringCount;

    private double minInteger = Double.MAX_VALUE;
    private double maxInteger = Double.MIN_VALUE;
    private double minFloat = Double.MAX_VALUE;
    private double maxFloat = Double.MIN_VALUE;

    private int minStringLength = Integer.MAX_VALUE;
    private int maxStringLength = 0;

    private double integerSum = 0.0;

    public FileStatistics() {
    }

    public FileStatistics(long integerCount, long floatCount, long stringCount,
                          double minInteger, double maxInteger, double minFloat, double maxFloat,
                          double integerSum, int minStringLength, int maxStringLength) {
        this.integerCount = integerCount;
        this.floatCount = floatCount;
        this.stringCount = stringCount;
        this.minInteger = minInteger;
        this.maxInteger = maxInteger;
        this.minFloat = minFloat;
        this.maxFloat = maxFloat;
        this.integerSum = integerSum;
        this.minStringLength = minStringLength;
        this.maxStringLength = maxStringLength;
    }

    public void add(FileStatistics other) {
        this.integerCount += other.integerCount;
        this.floatCount += other.floatCount;
        this.stringCount += other.stringCount;
        this.minInteger = Math.min(this.minInteger, other.minInteger);
        this.maxInteger = Math.max(this.maxInteger, other.maxInteger);
        this.minFloat = Math.min(this.minFloat, other.minFloat);
        this.maxFloat = Math.max(this.maxFloat, other.maxFloat);
        this.integerSum += other.integerSum;
        this.minStringLength = Math.min(this.minStringLength, other.minStringLength);
        this.maxStringLength = Math.max(this.maxStringLength, other.maxStringLength);
    }

    public void updateStringLengthStats(String line) {
        int length = line.length();
        this.minStringLength = Math.min(minStringLength, length);
        this.maxStringLength = Math.max(maxStringLength, length);
    }

    public long getIntegerCount() {
        return integerCount;
    }

    public long getFloatCount() {
        return floatCount;
    }

    public long getStringCount() {
        return stringCount;
    }

    public double getMinInteger() {
        return minInteger;
    }

    public double getMaxInteger() {
        return maxInteger;
    }

    public double getMinFloat() {
        return minFloat;
    }

    public double getMaxFloat() {
        return maxFloat;
    }

    public double getIntegerSum() {
        return integerSum;
    }

    public int getMinStringLength() {
        return minStringLength;
    }

    public int getMaxStringLength() {
        return maxStringLength;
    }
}

}
