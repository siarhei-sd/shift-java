package com.example;

import org.apache.commons.cli.ParseException;

public class Main {
    public static void main(String[] args) {
        try {
            ArgumentParser argParser = new ArgumentParser(args);
            
            StatisticsCollector statisticsCollector = new StatisticsCollector();
            DataFilter dataFilter = new DataFilter(statisticsCollector);
            FileProcessor fileProcessor = new FileProcessor(argParser.getInputFiles(), dataFilter);

            fileProcessor.processFiles();

            ResultWriter resultWriter = new ResultWriter(
                argParser.getOutputPath(),
                argParser.getPrefix(),
                argParser.isAppendMode()
            );
            resultWriter.writeResults(
                dataFilter.getIntegers(),
                dataFilter.getFloats(),
                dataFilter.getStrings()
            );

            if (argParser.isShortStats()) {
                System.out.println(statisticsCollector.getShortStatistics());
            } else if (argParser.isFullStats()) {
                System.out.println(statisticsCollector.getFullStatistics());
            }

        } catch (ParseException e) {
            System.err.println("Error parsing arguments: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Error processing files: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}