package org.example;

import java.io.*;
import java.util.*;

public class FileProcessor {
    private UtilCLI cli;
    private StatsCollector statsCollector;

    private Map<DataType, List<String>> collectedData;

    public FileProcessor(UtilCLI cli) {
        this.cli = cli;
        this.statsCollector = new StatsCollector();
        this.collectedData = new HashMap<>();
        for (DataType type : DataType.values()) {
            collectedData.put(type, new ArrayList<>());
        }
    }

    public void processFiles() {
        try {
            for (String filename : cli.getInputFiles()) {
                File file = new File(filename);
                processFile(file);
            }
        } catch (Exception e) {
            System.err.println("Error processing files: " + e.getMessage());
        }

        statsCollector.printStatistics(cli.getStatisticsType());

        writeAllDataToFiles();
    }

    private void processFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        }
    }

    private void processLine(String line) {
        String[] tokens = line.split("\\s+");

        for (String token : tokens) {
            if (UtilCLI.isInteger(token)) {
                collectedData.get(DataType.INTEGER).add(token);
                statsCollector.collect(DataType.INTEGER, token);
            } else if (UtilCLI.isFloat(token)) {
                collectedData.get(DataType.FLOAT).add(token);
                statsCollector.collect(DataType.FLOAT, token);
            } else {
                collectedData.get(DataType.STRING).add(token);
                statsCollector.collect(DataType.STRING, token);
            }
        }
    }

    private void writeAllDataToFiles() {
        boolean append = cli.isAppendMode();

        for (Map.Entry<DataType, List<String>> entry : collectedData.entrySet()) {
            DataType type = entry.getKey();
            List<String> dataList = entry.getValue();

            if (!dataList.isEmpty()) {
                String filename = getOutputFilename(type);

                try (PrintWriter writer = new PrintWriter(new FileWriter(filename, append))) {
                    for (String data : dataList) {
                        writer.println(data);
                    }
                } catch (IOException e) {
                    System.err.println("Error writing to file " + filename + ": " + e.getMessage());
                }
            }
        }
    }

    private String getOutputFilename(DataType type) {
        String prefix = cli.getOutputPrefix();
        String basePath = cli.getOutputPath();

        switch (type) {
            case INTEGER:
                return basePath + File.separator + prefix + "integers.txt";
            case FLOAT:
                return basePath + File.separator + prefix + "floats.txt";
            case STRING:
                return basePath + File.separator + prefix + "strings.txt";
            default:
                throw new IllegalArgumentException("Unknown data type: " + type);
        }
    }
}


