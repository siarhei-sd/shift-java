package com.TestTask.filemanager;

import com.TestTask.statistic.Statistic;
import com.TestTask.statistic.statisticImpl.StatisticForFloat;
import com.TestTask.statistic.statisticImpl.StatisticForInteger;
import com.TestTask.statistic.statisticImpl.StatisticForString;
import com.TestTask.parsing.StartOptions;

public class FileManager {
    private final StartOptions startOptions;

    public FileManager(String[] args) {
        this.startOptions = new StartOptions(args);
    }

    public void create() {
        FileDate fileDate = new FileDate(startOptions);
        Statistic integerStatistic = new StatisticForInteger(startOptions.isHaseStat(), startOptions.isFullStat());
        Statistic floatStatistic = new StatisticForFloat(startOptions.isHaseStat(), startOptions.isFullStat());
        Statistic stringStatistic = new StatisticForString(startOptions.isHaseStat(), startOptions.isFullStat());

        for (String file : startOptions.getFiles()){
            fileDate.filterFile(file);
        }
        fileDate.deleteFile();

        integerStatistic.getStatistic(fileDate.getIntFileName());
        floatStatistic.getStatistic(fileDate.getFloatFileName());
        stringStatistic.getStatistic(fileDate.getStrFileName());
    }
}
