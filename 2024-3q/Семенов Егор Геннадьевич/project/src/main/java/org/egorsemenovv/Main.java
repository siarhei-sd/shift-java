package org.egorsemenovv;

import org.egorsemenovv.console.ConsoleArgs;
import org.egorsemenovv.console.ConsoleArgsParser;
import org.egorsemenovv.filter.FileContentFilter;
import org.egorsemenovv.statistics.StatisticsCollector;
import org.egorsemenovv.validator.ConsoleArgsValidator;
import org.egorsemenovv.validator.ArgsValidationResult;
import org.egorsemenovv.validator.ValidationError;

import java.io.IOException;


public class Main {

    private static final ConsoleArgsParser consoleArgsParser = new ConsoleArgsParser();
    private static final ConsoleArgsValidator validator = ConsoleArgsValidator.getInstance();

    public static void main(String[] args) {

        consoleArgsParser.parseArgs(args);
        ConsoleArgs consoleArgs = consoleArgsParser.getConsoleArgs();

        ArgsValidationResult validationResult = validator.validate(consoleArgs);

        FileContentFilter filter = new FileContentFilter(validationResult.getPath(),
                validationResult.getPrefix(),
                validationResult.isAppend(),
                validationResult.getStatisticsType());

        if(!validationResult.isValid()){
            for (ValidationError error : validationResult.getValidationErrors()) {
                System.out.println(error.getMessage());
            }
        }

        try {
            filter.performFiltering(validationResult.getFileNames().stream().toList());
        } catch (IOException e){
            System.out.println("problems occurred while writing to files");
        }

        StatisticsCollector statisticsCollector = filter.getStatisticsCollector();
        statisticsCollector.printStatistics();
    }
}