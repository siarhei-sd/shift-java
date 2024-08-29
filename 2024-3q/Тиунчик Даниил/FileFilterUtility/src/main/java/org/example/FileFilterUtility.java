package org.example;

import java.io.*;
import java.util.*;
import java.util.function.Function;

public class FileFilterUtility {

    public static void main(String[] args) {
        try {
            Options options = parseOptions(args);
            processFiles(options);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static Options parseOptions(String[] args) {
        Options options = new Options();
        List<String> files = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o" -> options.setOutputPath(args[++i]);
                case "-p" -> options.setPrefix(args[++i]);
                case "-a" -> options.setAppend(true);
                case "-s" -> options.setShortStatistics(true);
                case "-f" -> options.setFullStatistics(true);
                default -> files.add(args[i]);
            }
        }

        options.setFiles(files);
        return options;
    }

    private static void processFiles(Options options) throws IOException {
        Map<DataType, BufferedWriter> writers = new HashMap<>();
        Map<DataType, List<String>> statistics = new HashMap<>();

        try {
            for (String file : options.getFiles()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        DataType type = DataType.determineType(line);
                        write(writers, options, type, line);
                        statistics.computeIfAbsent(type, k -> new ArrayList<>()).add(line);
                    }
                }
            }
            printStatistics(statistics, options);
        } finally {
            for (BufferedWriter writer : writers.values()) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Error closing writer: " + e.getMessage());
                }
            }
        }
    }

    private static void write(Map<DataType, BufferedWriter> writers, Options options, DataType type, String value) throws IOException {
        BufferedWriter writer = writers.get(type);
        if (writer == null) {
            String fileName = options.getFileName(type);
            writer = new BufferedWriter(new FileWriter(fileName, options.isAppend()));
            writers.put(type, writer);
        }

        try {
            writer.write(value);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing value: " + e.getMessage());
        }
    }

    private static void printStatistics(Map<DataType, List<String>> statistics, Options options) {
        for (Map.Entry<DataType, List<String>> entry : statistics.entrySet()) {
            DataType type = entry.getKey();
            List<String> values = entry.getValue();

            System.out.println(options.getPrefix() + type.toString().toLowerCase() + "s.txt statistics: elements = " + values.size());
            if (options.isFullStatistics()) {
                switch (type) {
                    case INTEGER -> printStatistics(values, Integer::parseInt);
                    case FLOAT -> printStatistics(values, Double::parseDouble);
                    case STRING -> printStringStatistics(values);
                }
            }
        }
    }


    private static <T extends Number & Comparable<T>> void printStatistics(List<String> values, Function<String, T> parser) {
        List<T> numbers = values.stream().map(parser).toList();
        T min = Collections.min(numbers);
        T max = Collections.max(numbers);
        double sum = numbers.stream().mapToDouble(Number::doubleValue).sum();
        double avg = numbers.stream().mapToDouble(Number::doubleValue).average().orElse(0.0);

        System.out.println("min = " + min + ", max = " + max + ", sum = " + sum + ", average = " + avg);
    }

    private static void printStringStatistics(List<String> values) {
        int minLength = values.stream().mapToInt(String::length).min().orElse(0);
        int maxLength = values.stream().mapToInt(String::length).max().orElse(0);

        System.out.println("shortest length = " + minLength + ", longest length = " + maxLength);
    }
}
