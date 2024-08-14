package com.filterutil;

import java.io.*;
import java.util.*;

public class FileProcessor {
    private OptionsParser optionsParser;
    private DataFilter dataFilter;
    private StatisticsCollector statsCollector;

    public FileProcessor(OptionsParser optionsParser) {
        this.optionsParser = optionsParser;
        this.dataFilter = new DataFilter();
        this.statsCollector = new StatisticsCollector();
    }

    public void processFiles() {
        for (String file : optionsParser.getInputFiles()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    dataFilter.filterData(line); // сортировка
                }
            } catch (IOException e) {
                System.err.println("Ошибка чтения файла: " + file);
            }
        }
        writeResults();
        printStatistics();
    }

    private void writeResults() {
        writeToFile(dataFilter.getIntegers(), "integers.txt");
        writeToFile(dataFilter.getFloats(), "floats.txt");
        writeToFile(dataFilter.getStrings(), "strings.txt");
    }

    private void writeToFile(List<?> data, String fileName) {
        if (data.isEmpty()) return; // если список пуст, он завершается

        String fullPath = optionsParser.getOutputPath() + "/" + optionsParser.getPrefix() + fileName; // ./some-integers.txt (Пример)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath, optionsParser.isAppend()))) {
            // optionsParser.isAppend() - добавляет (вместо перезаписи) данные в выходной файл.
            for (Object item : data) {
                writer.write(item.toString());
                writer.newLine(); // перевод на новую строку
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + fullPath);
        }
    }

    private void printStatistics() {
        if (optionsParser.isShortStat()) {
            System.out.println("Short Statistics:");
            System.out.println("Integers: " + dataFilter.getIntegers().size());
            System.out.println("Floats: " + dataFilter.getFloats().size());
            System.out.println("Strings: " + dataFilter.getStrings().size());
        }

        if (optionsParser.isFullStat()) {
            System.out.println("Full Statistics:");
            statsCollector.collect(dataFilter.getIntegers(), dataFilter.getFloats(), dataFilter.getStrings());
            System.out.println(statsCollector.getFullStatistics());
        }
    }
}