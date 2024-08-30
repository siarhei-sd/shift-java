package org.example;


import java.io.*;
import java.nio.file.*;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Util {

    private static String outputPath = "./";
    private static String prefix = "";
    private static boolean append = false;
    private static boolean fullStats = false;

    public static void main(String[] args) {
        List<String> inputFiles = new ArrayList<>();
        parseArguments(args, inputFiles);
        processFiles(inputFiles);
    }

    private static void parseArguments(String[] args, List<String> inputFiles) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length) {
                        outputPath = args[++i];
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length) {
                        prefix = args[++i];
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
    }

    private static void processFiles(List<String> inputFiles) {
        List<Integer> integers = new ArrayList<>();
        List<Float> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        for (String fileName : inputFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    classifyData(line.trim(), integers, floats, strings);
                }
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла " + fileName + ": " + e.getMessage());
            }
        }

        writeToFile(integers, "integers.txt");
        writeToFile(floats, "floats.txt");
        writeToFile(strings, "strings.txt");

        printStatistics(integers, floats, strings);
    }

    private static void classifyData(String line, List<Integer> integers, List<Float> floats, List<String> strings) {
        try {
            if (line.matches("-?\\d+")) {
                integers.add(Integer.parseInt(line));
            } else if (line.matches("-?\\d*\\.\\d+")) {
                floats.add(Float.parseFloat(line));
            } else {
                strings.add(line);
            }
        } catch (NumberFormatException e) {
            System.err.println("Ошибка преобразования данных: " + line);
        }
    }

    private static void writeToFile(List<?> data, String fileName) {
        if (data.isEmpty()) return;

        String fullPath = Paths.get(outputPath, prefix + fileName).toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath, append))) {
            for (Object item : data) {
                writer.write(item.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл " + fullPath + ": " + e.getMessage());
        }
    }

    private static void printStatistics(List<Integer> integers, List<Float> floats, List<String> strings) {
        if (fullStats) {
            printFullStatistics(integers, "Integers");
            printFullStatistics(floats, "Floats");
            printFullStatistics(strings, "Strings");
        } else {
            System.out.println("Краткая статистика:");
            System.out.println("elements integers=" + integers.size());
            System.out.println("elements floats=" + floats.size());
            System.out.println("elements strings=" + strings.size());
        }
    }

    private static void printFullStatistics(List<?> data, String type) {
        System.out.println("Полная статистика для " + type + ":");
        System.out.println("Количество: " + data.size());

        if (type.equals("Integers")) {
            List<Integer> intData = (List<Integer>) data;
            System.out.println("min=" + Collections.min(intData));
            System.out.println("max=" + Collections.max(intData));
            System.out.println("sum=" + intData.stream().mapToInt(Integer::intValue).sum());
            System.out.println("average : " + intData.stream().mapToInt(Integer::intValue).average().orElse(0));
        } else if (type.equals("Floats")) {
            List<Float> floatData = (List<Float>) data;
            System.out.println("min=" + Collections.min(floatData));
            System.out.println("max=" + Collections.max(floatData));
            System.out.println("sum=" + floatData.stream().mapToDouble(Float::doubleValue).sum());
            System.out.println("average=" + floatData.stream().mapToDouble(Float::doubleValue).average().orElse(0));
        } else if (type.equals("Strings")) {
            List<String> strData = (List<String>) data;
            System.out.println("min length=" + strData.stream().mapToInt(String::length).min().orElse(0));
            System.out.println("max length=" + strData.stream().mapToInt(String::length).max().orElse(0));
        }
    }


}