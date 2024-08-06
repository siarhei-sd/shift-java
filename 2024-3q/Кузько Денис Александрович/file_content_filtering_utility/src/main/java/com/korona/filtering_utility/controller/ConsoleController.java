package com.korona.filtering_utility.controller;

import com.korona.filtering_utility.dto.FullStatisticsDTO;
import com.korona.filtering_utility.dto.ShortStatisticsDTO;
import com.korona.filtering_utility.exeption.FileDaoException;
import com.korona.filtering_utility.exeption.InvalidCommandLineArgException;
import com.korona.filtering_utility.formatter.StatisticsDTOFormatter;
import com.korona.filtering_utility.servise.FileService;
import com.korona.filtering_utility.servise.FullStatisticsService;
import com.korona.filtering_utility.servise.ShortStatisticsService;
import com.korona.filtering_utility.servise.api.IFileService;
import com.korona.filtering_utility.servise.api.IStatisticsService;
import com.korona.filtering_utility.view.ConsoleView;
import com.korona.filtering_utility.view.api.IView;

import java.util.ArrayList;
import java.util.List;

public class ConsoleController {
    private IFileService fileService;
    private IView consoleView;
    private IStatisticsService statisticsService;

    private String outputDir;
    private String prefix;
    private boolean append;
    private TypeOfStatistics typeOfStatistics;
    private List<String> inputFiles;

    public ConsoleController() {
        this.consoleView = new ConsoleView();
        this.inputFiles = new ArrayList<>();
        prefix="";
    }

    public void execute(String[] args) {
        try {

            processArguments(args);
            initializeStatisticsService();

            fileService = new FileService(statisticsService);
            fileService.setFilePaths(outputDir, prefix);
            fileService.filterData(inputFiles, append);

            displayStatistics();

        } catch (FileDaoException e) {
            handleException(e);
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void processArguments(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    i++;
                    outputDir = args[i] + "/";
                    break;
                case "-p":
                    i++;
                    prefix = args[i];
                    break;
                case "-a":
                    append = true;
                    break;
                case "-s":
                    typeOfStatistics = TypeOfStatistics.SHORT;
                    break;
                case "-f":
                    typeOfStatistics = TypeOfStatistics.FULL;
                    break;
                default:
                    if (args[i].startsWith("-")) {
                        try {
                            throw new InvalidCommandLineArgException("Unknown option: " + args[i] + ". Processing will continue");
                        } catch (InvalidCommandLineArgException e) {
                            handleException(e);
                        }
                    } else {
                        inputFiles.add(args[i]);
                    }
                    break;
            }
        }
    }

    private void handleException(Exception e) {
        consoleView.displayError(e.getMessage(), e);
    }

    private void initializeStatisticsService() {
        if (typeOfStatistics == TypeOfStatistics.SHORT) {
            statisticsService = new ShortStatisticsService();

        } else if (typeOfStatistics == TypeOfStatistics.FULL) {
            statisticsService = new FullStatisticsService();
        }
    }

    private void displayStatistics() {
        if (typeOfStatistics == TypeOfStatistics.SHORT) {
            ShortStatisticsDTO shortStatisticsDTO = (ShortStatisticsDTO) statisticsService.getStatistics();
            String formattedStatistics = StatisticsDTOFormatter.statisticsDTOtoString(shortStatisticsDTO);
            consoleView.displayMessage(formattedStatistics);

        } else if (typeOfStatistics == TypeOfStatistics.FULL) {
            FullStatisticsDTO fullStatisticsDTO = (FullStatisticsDTO) statisticsService.getStatistics();
            String formattedStatistics = StatisticsDTOFormatter.statisticsDTOtoString(fullStatisticsDTO);
            consoleView.displayMessage(formattedStatistics);
        }
    }
}