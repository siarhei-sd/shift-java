package org.example;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class DataProcessing {

    private final CommandOptions options;
    private final List<String> strings = new ArrayList<>();
    private final List<Integer> integers = new ArrayList<>();
    private final List<Double> floats = new ArrayList<>();

    public DataProcessing(CommandOptions options) {
        this.options = options;
    }

    public void processFiles() throws IOException {
        for (String filename : options.inputFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lineFilter(line);
                }
            } catch (IOException e) {
                System.err.println("File processing error: " + filename + ". Check if the file exists or if the file name is correct.");
                System.exit(1);
            }
        }
        writeResults();
    }

    private void lineFilter(String line) {
        if (line == null || line.trim().isEmpty()) {
            return;
        }

        try {
            if (line.contains(".")) {
                floats.add(Double.parseDouble(line));
            } else {
                integers.add(Integer.parseInt(line));
            }
        } catch (NumberFormatException e) {
            strings.add(line);
        }
    }

    private void writeResults() throws IOException {
        writeToFileWithCheck(options.getFilePath("integers.txt"), integers);
        writeToFileWithCheck(options.getFilePath("floats.txt"), floats);
        writeToFileWithCheck(options.getFilePath("strings.txt"), strings);
    }

    private void writeToFileWithCheck(String filePath, List<?> data) throws IOException {
        File directory = new File(options.outputPath);
        if (!options.outputPath.isEmpty() && !directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + options.outputPath);
            } else {
                System.err.println("Failed to create directory: " + options.outputPath + ". Try using existing partitions");
                System.exit(1);
            }
        }

        if (data.isEmpty()) {
            System.out.println("The list is empty, the file was not created: " + filePath);
            return;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
            System.out.println("File created: " + filePath);
        } else {
            System.out.println("File already exists: " + filePath);
        }

        writeToFile(filePath, data);
        options.outputFiles.add(filePath);
    }

    private <T> void writeToFile(String filename, List<T> data) throws IOException {
        if (data.isEmpty()) return;

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, options.addMode))) {
            for (T item : data) {
                writer.println(item);
            }
        }
    }

    public void printStatistics() {

        if (options.shortStatisticsFiles) {
            printShortStatisticsFiles();
        }
        if (options.fullStatisticsElements) {
            printFullStatisticsElements();
        }
        if (options.fullStatisticsFiles) {
            printFullStatisticsFiles();
        }
        if (options.shortStatisticsElements) {
            printShortStatisticsElements();
        }

    }

    private void printShortStatisticsElements() {
        String shortStatisticsElements =
                "------------Short statistics------------ \n" +
                        "unique elements = " + (integers.size() + floats.size() + strings.size());
        System.out.println(shortStatisticsElements);
    }

    private void printShortStatisticsFiles() {
        int totalLineCount = 0;


        for (String filePath : options.outputFiles) {

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                int lineCount = 0;
                while (reader.readLine() != null) {
                    lineCount++;
                }
                totalLineCount += lineCount;
            } catch (IOException e) {
                System.err.println("Error processing file: " + filePath);
            }
        }

        String shortStatisticsFiles =
                "--------Short statistics(custom)--------- \n" +
                        "All elements in files = " + totalLineCount;

        System.out.println(shortStatisticsFiles);
    }

    private void printFullStatisticsElements() {
        System.out.print("-----Full statistics unique elements----- \n");
        if (!integers.isEmpty()) {
            int min = Collections.min(integers);
            int max = Collections.max(integers);
            int sum = 0;
            for (Integer i : integers) {
                sum += i;
            }
            double avg = sum / (double) integers.size();
            DecimalFormat df = new DecimalFormat("#.########");
            System.out.println("Integers - Min: " + min + ", Max: " + max + ", Sum: " + sum + ", Avg: " + df.format(avg));

        }

        if (!floats.isEmpty()) {
            double min = Collections.min(floats);
            double max = Collections.max(floats);
            double sum = 0;
            for (Double i : floats) {
                sum += i;
            }
            double avg = sum / floats.size();
            DecimalFormat df = new DecimalFormat("#.########");
            System.out.println("Floats - Min: " + min + ", Max: " + max + ", Sum: " + sum + ", Avg: " + df.format(avg));

        }

        if (!strings.isEmpty()) {
            System.out.print("Strings - Count: " + strings.size());
            String minLength = strings.get(0);
            String maxLength = strings.get(0);
            for (String s : strings) {
                if (s.compareTo(minLength) < 0) {
                    minLength = s;
                }
            }
            for (String s : strings) {
                if (s.compareTo(maxLength) > 0) {
                    maxLength = s;
                }
            }
            System.out.print(". Min length: " + minLength.length() + ", Max length: " + maxLength.length()+"\n");

        }
    }

    private void printFullStatisticsFiles() {
        System.out.print("------Full statistics all elements------\n");

        // Integer statistics
        if (!integers.isEmpty()) {
            int min = Collections.min(integers);
            int max = Collections.max(integers);
            int sum = 0;
            int countAllInt = 0;

            try (BufferedReader reader = new BufferedReader(new FileReader(options.getFilePath("integers.txt")))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    try {
                        sum += Integer.parseInt(line);
                        countAllInt++;
                    } catch (NumberFormatException e) {

                    }
                }
            } catch (IOException e) {
                System.err.println("Error processing file: " + options.getFilePath("integers.txt"));
            }
            double avg = (double) sum / countAllInt;

            DecimalFormat df = new DecimalFormat("#.########");
            System.out.println("Integers - Min: " + min + ", Max: " + max + ", Sum: " + sum + ", Avg: " + df.format(avg));
        }

        // Float statistics
        if (!floats.isEmpty()) {
            double min = Collections.min(floats);
            double max = Collections.max(floats);
            double sum = 0;
            int countAllFloat = 0;


            try (BufferedReader reader = new BufferedReader(new FileReader(options.getFilePath("floats.txt")))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue; // Пропустить пустые строки
                    try {
                        sum += Double.parseDouble(line);
                        countAllFloat++;
                    } catch (NumberFormatException e) {
                        // Игнорировать строки, которые не являются числами
                    }
                }
            } catch (IOException e) {
                System.err.println("Error processing file: " + options.getFilePath("floats.txt"));
            }


            double avg = (countAllFloat > 0) ? sum / countAllFloat : 0;
            DecimalFormat df = new DecimalFormat("#.########");
            System.out.println("Floats - Min: " + min + ", Max: " + max + ", Sum: " + sum + ", Avg: " + df.format(avg));
        }

        // String statistics
        if (!strings.isEmpty()) {
            File file = new File(options.getFilePath("strings.txt"));

            if (!file.exists()) {
                System.err.println("File not found: " + options.getFilePath("strings.txt"));
                return;
            }

            int totalLineCount = 0;

            for (String filePath : options.outputFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(options.getFilePath("strings.txt")))) {
                    int lineCount = 0;
                    while (reader.readLine() != null) {
                        lineCount++;
                    }
                    totalLineCount += lineCount;
                } catch (IOException e) {
                    System.err.println("Error processing file: " + filePath);
                }
            }

            String minLength = strings.get(0);
            String maxLength = strings.get(0);
            for (String s : strings) {
                if (s.compareTo(minLength) < 0) {
                    minLength = s;
                }
            }
            for (String s : strings) {
                if (s.compareTo(maxLength) > 0) {
                    maxLength = s;
                }
            }
            System.out.print("Strings - count: "+totalLineCount+", Min length: " + minLength.length() + ", Max length: " + maxLength.length()+"\n");
        }


    }

}

