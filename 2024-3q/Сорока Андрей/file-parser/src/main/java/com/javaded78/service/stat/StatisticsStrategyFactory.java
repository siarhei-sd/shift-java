package com.javaded78.service.stat;

import com.javaded78.domain.DataType;
import com.javaded78.service.stat.impl.FloatStatisticsStrategy;
import com.javaded78.service.stat.impl.IntegerStatisticsStrategy;
import com.javaded78.service.stat.impl.ShortStatisticsStrategy;
import com.javaded78.service.stat.impl.StringStatisticsStrategy;

public class StatisticsStrategyFactory {

    private static final StatisticsStrategyFactory INSTANCE = new StatisticsStrategyFactory();


    public StatisticsStrategy createStat(final String fileName,
                                         final boolean isShortStat,
                                         final DataType dataType) {
        if (isShortStat) {
            return new ShortStatisticsStrategy(fileName);
        }
        return switch (dataType) {
            case STRING -> new StringStatisticsStrategy(fileName);
            case FLOAT -> new FloatStatisticsStrategy(fileName);
            default -> new IntegerStatisticsStrategy(fileName);
        };
    }

    private StatisticsStrategyFactory() {}

    public static StatisticsStrategyFactory getInstance() {
        return INSTANCE;
    }
}
