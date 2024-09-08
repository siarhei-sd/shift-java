package org.example;

import java.io.IOException;

public class FilterApp {
    public static void main(String[] args) throws IOException {
        CommandOptions commandOptions = new CommandOptions(args);
        DataProcessing dataProcessing = new DataProcessing(commandOptions);
        dataProcessing.processFiles();
        dataProcessing.printStatistics();
        System.out.println("----------------------------------------");
    }
}