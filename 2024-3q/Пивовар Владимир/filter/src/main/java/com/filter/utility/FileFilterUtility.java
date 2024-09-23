package com.filter.utility;


public class FileFilterUtility {
    public void process(String[] args) {
        try {
            FileProcessor processor = new FileProcessor();
            processor.parseArgs(args);
            processor.printStatistics();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
