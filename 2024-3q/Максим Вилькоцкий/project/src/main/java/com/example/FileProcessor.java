package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileProcessor {
    private final String[] inputFiles;
    private final DataFilter dataFilter;

    public FileProcessor(String[] inputFiles, DataFilter dataFilter) {
        this.inputFiles = inputFiles;
        this.dataFilter = dataFilter;
    }

    public void processFiles() throws IOException {
        for (String filePath : inputFiles) {
            processFile(filePath);
        }
    }

    private void processFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dataFilter.processLine(line);
            }
        }
    }
}