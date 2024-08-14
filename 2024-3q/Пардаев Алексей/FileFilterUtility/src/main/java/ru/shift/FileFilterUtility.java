package ru.shift;

import java.io.IOException;
import java.util.List;

public class FileFilterUtility {
    public static void main(String[] args) {
        CommandLineOptions options = new CommandLineOptions(args);

        List<String> inputFiles = options.getInputFiles();
        if (inputFiles.isEmpty()) {
            System.out.println("No input files specified.");
            return;
        }

        FileProcessor processor = new FileProcessor(options);
        for (String inputFile : inputFiles) {
            try {
                processor.processFile(inputFile);
            } catch (IOException e) {
                System.err.println("Error processing file: " + inputFile);
                e.printStackTrace();
            }
        }

        processor.writeResults();
        processor.printStatistics();
    }
}
