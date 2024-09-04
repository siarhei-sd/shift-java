package com.filefilterutility;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileDataFilter {
    private final String outputPath;
    private final String prefix;
    private final boolean append;
    private final boolean fullStatistics;
    private final Map<DataType, List<String>> dataMap = new HashMap<>();
    private final Map<DataType, Statistics> statisticsMap = new HashMap<>();

    public FileDataFilter(String outputPath, String prefix, boolean append, boolean fullStatistics) {
        this.outputPath = outputPath;
        this.prefix = prefix;
        this.append = append;
        this.fullStatistics = fullStatistics;
        initializeAllMaps();
    }

    private void initializeAllMaps() {
        for (DataType type : DataType.values()) {
            dataMap.put(type, new ArrayList<>());
            statisticsMap.put(type, new Statistics(type, fullStatistics));
        }
    }

    public void processFiles(List<String> inputFiles) {
        for (String fileName : inputFiles) {
            File file = new File(fileName);
            if (!file.exists() || !file.isFile()) {
                System.out.println("File not found or not a file: " + fileName);
                continue;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    classifyAndStoreData(line.trim());
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + fileName);
            }
        }

        writeResults();
        printStatistics();
    }


    private void classifyAndStoreData(String data) {
        if (data.isEmpty()){
            return;
        }

        StringBuilder stringPart = new StringBuilder();
        String[] tokens = data.split("\\s+");

        for (String token : tokens) {
            if (token.matches("-?\\d+")) {
                dataMap.get(DataType.INTEGER).add(token);
                statisticsMap.get(DataType.INTEGER).updateStatistics(Integer.parseInt(token));
            } else if (token.matches("-?\\d*\\.\\d+([eE][+-]?\\d+)?")) {
                dataMap.get(DataType.FLOAT).add(token);
                statisticsMap.get(DataType.FLOAT).updateStatistics(Double.parseDouble(token));
            } else {
                if (stringPart.length() > 0) {
                    stringPart.append(" ");
                }
                stringPart.append(token);
            }
        }

        if (stringPart.length() > 0) {
            dataMap.get(DataType.STRING).add(stringPart.toString());
            statisticsMap.get(DataType.STRING).updateStatistics(stringPart.toString());
        }
    }



    private void writeResults() {
        for (DataType type : DataType.values()) {
            List<String> dataList = dataMap.get(type);
            if (!dataList.isEmpty()) {
                String fileName = outputPath + File.separator + prefix + type.getFileName();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, append))) {
                    for (String data : dataList) {
                        writer.write(data);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    System.out.println("Error writing to file: " + fileName);
                }
            }
        }
    }

    private void printStatistics() {
        for (DataType type : DataType.values()) {
            if (!dataMap.get(type).isEmpty()) {
                statisticsMap.get(type).printStatistics(prefix + type.getFileName());
            }
        }
    }
}
