package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> argsList = Arrays.asList(args);
        List<String> files = ArgsHelper.getFiles(argsList);

        String statisticType = ArgsHelper.getStatisticType(argsList);
        String outputPath = ArgsHelper.getOutputPath(argsList);
        System.out.println("outputPath " + outputPath);
        String filePrefix = ArgsHelper.getFilePrefix(argsList);
        boolean append = ArgsHelper.getAppend(argsList);

        Statistic statistic = new Statistic(statisticType);
        for (int i = 0; i < files.size(); i++) {
            Filereader.readFile(files.get(i), append || i != 0, statistic, outputPath, filePrefix);
        }
        statistic.printStatistic();
    }
}