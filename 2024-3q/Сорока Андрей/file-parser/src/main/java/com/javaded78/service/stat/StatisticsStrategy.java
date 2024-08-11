package com.javaded78.service.stat;

import com.javaded78.domain.stat.ShortStatistics;

public interface StatisticsStrategy {

    void update(String value);

    ShortStatistics getStatistics();
}
