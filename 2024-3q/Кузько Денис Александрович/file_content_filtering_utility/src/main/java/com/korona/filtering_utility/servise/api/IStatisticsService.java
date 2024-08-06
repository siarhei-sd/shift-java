package com.korona.filtering_utility.servise.api;

public interface IStatisticsService<T> {
    T getStatistics();

    void addIntegerData(String data);

    void addFloatData(String data);

    void addStringData(String data);

    void setFileNames(String [] fileNames);

}
