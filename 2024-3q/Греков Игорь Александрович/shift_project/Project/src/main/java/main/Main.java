package main;

import java.io.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    private static String outputPath = "";
    private static String prefix = "";
    private static final String nameIntegers = "integers.txt";
    private static final String nameFloats = "floats.txt";
    private static final String nameStrings = "strings.txt";
    private static boolean appendMode = false;
    private static boolean fullStats = false;

    private static final List<Integer> integers = new ArrayList<>();
    private static final List<Double> floats = new ArrayList<>();
    private static final List<String> strings = new ArrayList<>();

    public static void main(String[] args) {
        try {
            List<String> inputFiles = parseArguments(args);

            if (inputFiles.isEmpty()) {
                throw new IllegalArgumentException("Не указаны входные файлы.");
            }

            processFiles(inputFiles);
            writeOutputFiles();
            printStatistics();

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    private static List<String> parseArguments(String[] args) {
        List<String> inputFiles = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length) {
                        try{
                            outputPath = String.valueOf(Paths.get(args[++i]));
                            createDirectory(outputPath);
                        } catch (InvalidPathException ex) {
                            System.err.println("Неверно указан путь для сохранения файла. Файлы будут сохранены в текущую директорию");
                        }
                    } else {
                        throw new IllegalArgumentException("Отсутствует аргумент для опции -o.");
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length) {
                        prefix = args[++i];
                    } else {
                        throw new IllegalArgumentException("Отсутствует аргумент для опции -p.");
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
                default:
                    inputFiles.add(args[i]);
                    break;
            }
        }

        return inputFiles;
    }

    private static void processFiles(List<String> inputFiles) {
        for (String fileName : inputFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    classifyAndStore(line.split(" "));
                }
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла " + fileName);
            }
        }
    }

    private static void classifyAndStore(String[] array) {
        StringBuilder buf = new StringBuilder();
        String str;
        for (String value : array) {
            try {
                integers.add(Integer.parseInt(value));
            } catch (NumberFormatException e1) {
                try {
                    floats.add(Double.parseDouble(value));
                } catch (NumberFormatException e2) {
                    buf.append(value).append(" ");
                }
            }
        }
        if (!(str = buf.toString().trim()).isEmpty()) strings.add(str);
    }

    private static void writeOutputFiles() {
        writeToFile(nameIntegers, integers);
        writeToFile(nameFloats, floats);
        writeToFile(nameStrings, strings);
    }

    private static <T> void writeToFile(String fileName, List<T> data) {
        if (data.isEmpty()) {
            return;
        }

        String filePath = outputPath.isEmpty() ? "" : outputPath + File.separator;
        filePath += prefix + fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, appendMode))) {
            for (T line : data) {
                writer.write(line.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл " + filePath + ": " + e.getMessage());
        }
    }

    private static void createDirectory(String path) {
        File directory = new File(String.valueOf(path));

        try {
            directory.mkdirs();
        } catch (SecurityException ex) {
            System.err.println("Нет прав для создания директории");
        }
    }

    private static void printStatistics() {
        if (fullStats) {
            printFullStatistics();
        } else {
            printShortStatistics();
        }
    }

    private static void printShortStatistics() {
        System.out.println("Целые числа: " + integers.size());
        System.out.println("Вещественные числа: " + floats.size());
        System.out.println("Строки: " + strings.size());
    }

    private static void printFullStatistics() {
        printFullStatisticsForNumbers("Целые числа", integers);
        System.out.println();
        printFullStatisticsForNumbers("Вещественные числа", floats);
        System.out.println();
        printFullStatisticsForStrings();
    }

    private static <T extends Number & Comparable<T>> void printFullStatisticsForNumbers(String type, List<T> list) {
        if (list.isEmpty()) {
            System.out.println(type + ": нет данных.");
            return;
        }

        T min = Collections.min(list);
        T max = Collections.max(list);
        double sum = list.stream().mapToDouble(Number::doubleValue).sum();
        double average = sum / list.size();

        System.out.println(type + ":");
        System.out.println("Количество: " + list.size());
        System.out.println("Минимум: " + min);
        System.out.println("Максимум: " + max);
        System.out.println("Сумма: " + sum);
        System.out.println("Среднее: " + average);
    }

    private static void printFullStatisticsForStrings() {
        if (strings.isEmpty()) {
            System.out.println("Строки: нет данных.");
            return;
        }

        String shortest = strings.stream().min(Comparator.comparingInt(String::length)).orElse("");
        String longest = strings.stream().max(Comparator.comparingInt(String::length)).orElse("");

        System.out.println("Строки:");
        System.out.println("Количество: " + strings.size());
        System.out.println("Самая короткая строка (длина): " + shortest.length());
        System.out.println("Самая длинная строка (длина): " + longest.length());
    }
}