package com.javaded78.consumer;

import com.javaded78.broker.BrokerFactory;
import com.javaded78.config.AppConfig;
import com.javaded78.domain.DataType;
import com.javaded78.service.stat.StatisticsStrategyFactory;
import com.javaded78.service.writer.Writer;
import com.javaded78.service.writer.impl.FileWriter;

import java.nio.file.Path;

public class ConsumerFactory {

    private static final ConsumerFactory INSTANCE = new ConsumerFactory();
    private final BrokerFactory brokerFactory = BrokerFactory.getInstance();
    private final StatisticsStrategyFactory statStrategyFactory = StatisticsStrategyFactory.getInstance();
    private final Writer writer = FileWriter.getInstance();

    private ConsumerFactory() {
    }

    public Consumer create(final AppConfig config,
                           final Path outputFile,
                           final DataType dataType) {
        return Consumer.builder()
                .broker(brokerFactory.createBroker())
                .writer(writer)
                .dataType(dataType)
                .outputFile(outputFile)
                .statisticsStrategy(
                        statStrategyFactory.createStat(
                                outputFile.getFileName().toString(),
                                config.isShortStats(),
                                dataType)
                )
                .append(config.isAppend())
                .build();
    }

    public static ConsumerFactory getInstance() {
        return INSTANCE;
    }
}
