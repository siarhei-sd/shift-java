package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DataFilterUtility {

    private static final String DEFAULT_INT_FILE = "integers.txt";
    private static final String DEFAULT_FLOAT_FILE = "floats.txt";
    private static final String DEFAULT_STRING_FILE = "strings.txt";

    private static String outputDirectory = "";
    private static String prefix = "";
    private static boolean append = false;
    private static boolean fullStats = false;

    private static List<Integer> integers = new ArrayList<>();
    private static List<Float> floats = new ArrayList<>();
    private static List<String> strings = new ArrayList<>();

    public static void main(String[] args) {
        List<String> inputFiles = parseArguments(args);

        if (inputFiles.isEmpty()) {
            System.err.println("No input files specified.");
            return;
        }

        for (String inputFile : inputFiles) {
            processFile(inputFile);
        }

        writeOutput();
        printStatistics();
    }

    private static List<String> parseArguments(String[] args) {
        List<String> inputFiles = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length) {
                        outputDirectory = args[++i];
                    } else {
                        System.err.println("Output directory not specified.");
                        return Collections.emptyList();
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length) {
                        prefix = args[++i];
                    } else {
                        System.err.println("Prefix not specified.");
                        return Collections.emptyList();
                    }
                    break;
                case "-a":
                    append = true;
                    break;
                case "-s":
                    fullStats = false;
                    break;
                case "-f":
                    fullStats = true;
                    break;
                default:
                    inputFiles.add(args[i]);
                    break;
            }
        }

        return inputFiles;
    }

    private static void processFile(String inputFile) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file " + inputFile + ": " + e.getMessage());
        }
    }

    private static void processLine(String line) {
        // Разделение строки на слова по пробелам
        String[] tokens = line.split("\\s+");

        StringBuilder currentString = new StringBuilder();

        for (String token : tokens) {
            if (isInteger(token)) {
                // Записываем текущую строку, если она непустая
                if (currentString.length() > 0) {
                    strings.add(currentString.toString().trim());
                    currentString.setLength(0);
                }
                integers.add(Integer.parseInt(token));
            } else if (isFloat(token)) {
                // Записываем текущую строку, если она непустая
                if (currentString.length() > 0) {
                    strings.add(currentString.toString().trim());
                    currentString.setLength(0);
                }
                floats.add(Float.parseFloat(token));
            } else {
                // Добавляем токен к текущей строке
                if (currentString.length() > 0) {
                    currentString.append(" ");
                }
                currentString.append(token);
            }
        }

        // Добавляем последнюю собранную строку, если она существует
        if (currentString.length() > 0) {
            strings.add(currentString.toString().trim());
        }
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void writeOutput() {
        writeToFile(getOutputFilePath(DEFAULT_INT_FILE), integers, append);
        writeToFile(getOutputFilePath(DEFAULT_FLOAT_FILE), floats, append);
        writeToFile(getOutputFilePath(DEFAULT_STRING_FILE), strings, append);
    }

    private static <T> void writeToFile(String filePath, List<T> data, boolean append) {
        if (data.isEmpty()) {
            return; // Не создаем пустые файлы
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
            for (T element : data) {
                writer.write(element.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }

    private static String getOutputFilePath(String defaultFileName) {
        return Paths.get(outputDirectory, prefix + defaultFileName).toString();
    }

    private static void printStatistics() {
        printStatistic(getOutputFilePath(DEFAULT_INT_FILE), integers);
        printStatistic(getOutputFilePath(DEFAULT_FLOAT_FILE), floats);
        printStatistic(getOutputFilePath(DEFAULT_STRING_FILE), strings);
    }

    private static <T> void printStatistic(String filePath, List<T> data) {
        if (data.isEmpty()) {
            return;
        }

        if (data.get(0) instanceof Integer) {
            List<Integer> integerData = (List<Integer>) data;
            int min = Collections.min(integerData);
            int max = Collections.max(integerData);
            long sum = integerData.stream().mapToLong(Integer::longValue).sum();
            double avg = integerData.stream().mapToLong(Integer::longValue).average().orElse(0);
            printStat(filePath, integerData.size(), min, max, sum, avg);
        } else if (data.get(0) instanceof Float) {
            List<Float> floatData = (List<Float>) data;
            float min = Collections.min(floatData);
            float max = Collections.max(floatData);
            double sum = floatData.stream().mapToDouble(Float::doubleValue).sum();
            double avg = floatData.stream().mapToDouble(Float::doubleValue).average().orElse(0);
            printStat(filePath, floatData.size(), min, max, sum, avg);
        } else if (data.get(0) instanceof String) {
            List<String> stringData = (List<String>) data;
            int minLen = stringData.stream().mapToInt(String::length).min().orElse(0);
            int maxLen = stringData.stream().mapToInt(String::length).max().orElse(0);
            printStringStat(filePath, stringData.size(), minLen, maxLen);
        }
    }

    private static void printStat(String filePath, int elements, Number min, Number max, double sum, double avg) {
        if (fullStats) {
            System.out.printf("%s full statistic: elements = %d; min = %s; max = %s; sum = %s; average = %s;%n",
                    filePath, elements, min, max, sum, avg);
        } else {
            System.out.printf("%s short statistic: elements = %d%n", filePath, elements);
        }
    }

    private static void printStringStat(String filePath, int elements, int minLen, int maxLen) {
        if (fullStats) {
            System.out.printf("%s full statistic: elements = %d; min length = %d; max length = %d;%n",
                    filePath, elements, minLen, maxLen);
        } else {
            System.out.printf("%s short statistic: elements = %d%n", filePath, elements);
        }
    }
}
