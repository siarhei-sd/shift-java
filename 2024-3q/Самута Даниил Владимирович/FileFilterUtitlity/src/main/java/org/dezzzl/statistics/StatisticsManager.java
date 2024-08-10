package org.dezzzl.statistics;

import org.dezzzl.Configuration;
import org.dezzzl.filewriter.DefaultFileWriter;

import java.util.List;

public class StatisticsManager {
    private static final Integer INTEGER_FW_INDEX = 0;
    private static final Integer FLOAT_FW_INDEX = 1;
    private static final Integer STRING_FW_INDEX = 2;


    public static void setStatisticsToWriters(List<DefaultFileWriter> fileWriters, Configuration configuration) {
        if (configuration.isFullStatisticsOption()) {
            fileWriters.get(INTEGER_FW_INDEX).setStatistics(new NumberFullStatistics<Double>());
            fileWriters.get(FLOAT_FW_INDEX).setStatistics(new NumberFullStatistics<Integer>());
            fileWriters.get(STRING_FW_INDEX).setStatistics(new StringFullStatistics());
        } else if (configuration.isShortStatisticsOption()) {
            fileWriters.get(INTEGER_FW_INDEX).setStatistics(new ShortStatistics());
            fileWriters.get(FLOAT_FW_INDEX).setStatistics(new ShortStatistics());
            fileWriters.get(STRING_FW_INDEX).setStatistics(new ShortStatistics());
        }

    }

    public static void printStatistics(List<DefaultFileWriter> fileWriters) {
        for (DefaultFileWriter fileWriter : fileWriters) fileWriter.printStatistics();
    }
}
