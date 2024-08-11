package com.javaded78.service.stat.impl;

import com.javaded78.domain.stat.ShortStatistics;
import com.javaded78.service.stat.StatisticsStrategy;

public class ShortStatisticsStrategy implements StatisticsStrategy {

    private final ShortStatistics shortStatistics;

    public ShortStatisticsStrategy(final String fileName) {
        shortStatistics = new ShortStatistics(fileName);
    }

    @Override
    public void update(final String value) {
        shortStatistics.setCount();
    }

    @Override
    public ShortStatistics getStatistics() {
        return shortStatistics;
    }
}
