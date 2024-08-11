package com.javaded78.service.stat.impl;

import com.javaded78.domain.stat.FloatStatistics;
import com.javaded78.domain.stat.ShortStatistics;
import com.javaded78.service.stat.StatisticsStrategy;

public class FloatStatisticsStrategy implements StatisticsStrategy {

    private final FloatStatistics statistics;

    public FloatStatisticsStrategy(final String fileName) {
        statistics = new FloatStatistics(fileName);
    }

    @Override
    public void update(final String value) {
        double doubleValue = Double.parseDouble(value);
        statistics.update(doubleValue);
    }

    @Override
    public ShortStatistics getStatistics() {
        return statistics.getCount() == 0 ? new ShortStatistics(statistics.getFileName()) : this.statistics;
    }
}
