package com.javaded78.service.stat.impl;

import com.javaded78.domain.stat.ShortStatistics;
import com.javaded78.domain.stat.StringStatistics;
import com.javaded78.service.stat.StatisticsStrategy;

public class StringStatisticsStrategy implements StatisticsStrategy {

    private final StringStatistics statistics;

    public StringStatisticsStrategy(final String fileName) {
        statistics = new StringStatistics(fileName);
    }

    @Override
    public void update(final String value) {
        statistics.update(value);
    }

    @Override
    public ShortStatistics getStatistics() {
        return statistics.getCount() == 0 ? new ShortStatistics(statistics.getFileName()) : this.statistics;
    }
}
