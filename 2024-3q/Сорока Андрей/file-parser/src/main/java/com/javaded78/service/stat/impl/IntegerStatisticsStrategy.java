package com.javaded78.service.stat.impl;

import com.javaded78.domain.stat.IntStatistics;
import com.javaded78.domain.stat.ShortStatistics;
import com.javaded78.service.stat.StatisticsStrategy;

public class IntegerStatisticsStrategy implements StatisticsStrategy {
    
    private final IntStatistics statistics;

    public IntegerStatisticsStrategy(final String fileName) {
        statistics = new IntStatistics(fileName);
    }

    @Override
    public void update(final String value) {
        long intValue = Long.parseLong(value);
        statistics.update(intValue);
    }

    @Override
    public ShortStatistics getStatistics() {
        return statistics.getCount() == 0 ? new ShortStatistics(statistics.getFileName()) : this.statistics;
    }
}
