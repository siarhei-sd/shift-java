package com.javaded78.consumer;

import com.javaded78.broker.Broker;
import com.javaded78.domain.DataType;
import com.javaded78.domain.stat.Statistics;
import com.javaded78.exception.WriteFileException;
import com.javaded78.service.stat.StatisticsStrategy;
import com.javaded78.service.writer.Writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Callable;

public class Consumer implements Callable<Statistics> {

    private final Writer writer;
    private final Broker broker;
    private final Path outputFile;
    private final DataType dataType;
    private final StatisticsStrategy statisticsStrategy;
    private final boolean append;

    public Consumer(final Writer writer,
                    final Broker broker,
                    final Path outputFile,
                    final DataType dataType,
                    final StatisticsStrategy statisticsStrategy,
                    final boolean append) {
        this.writer = writer;
        this.broker = broker;
        this.outputFile = outputFile;
        this.dataType = dataType;
        this.statisticsStrategy = statisticsStrategy;
        this.append = append;
    }

    public static ConsumerBuilder builder() {
        return new ConsumerBuilder();
    }

    @Override
    public Statistics call() {
        this.writer.inspect(outputFile, append);
        BufferedWriter bufferedWriter = null;
        try {
            while (true) {
                String record = broker.take(dataType);
                if (Thread.currentThread().isInterrupted() || Broker.EOF.equals(record)) {
                    break;
                }
                bufferedWriter = writer.write(bufferedWriter, record, outputFile);
                statisticsStrategy.update(record);
            }
        } catch (IOException e) {
            throw new WriteFileException(e.getMessage());
        } finally {
            closeWriter(bufferedWriter);
        }
        return statisticsStrategy.getStatistics();
    }

    private void closeWriter(BufferedWriter bufferedWriter) {
        if (bufferedWriter != null) {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                throw new WriteFileException(e.getMessage());
            }
        }
    }

    public static class ConsumerBuilder {
        private Writer writer;
        private Broker broker;
        private Path outputFile;
        private DataType dataType;
        private StatisticsStrategy statisticsStrategy;
        private boolean append;

        ConsumerBuilder() {
        }

        public ConsumerBuilder writer(Writer writer) {
            this.writer = writer;
            return this;
        }

        public ConsumerBuilder broker(Broker broker) {
            this.broker = broker;
            return this;
        }

        public ConsumerBuilder outputFile(Path outputFile) {
            this.outputFile = outputFile;
            return this;
        }

        public ConsumerBuilder dataType(DataType dataType) {
            this.dataType = dataType;
            return this;
        }

        public ConsumerBuilder statisticsStrategy(StatisticsStrategy statisticsStrategy) {
            this.statisticsStrategy = statisticsStrategy;
            return this;
        }

        public ConsumerBuilder append(boolean append) {
            this.append = append;
            return this;
        }

        public Consumer build() {
            return new Consumer(this.writer,
                    this.broker,
                    this.outputFile,
                    this.dataType,
                    this.statisticsStrategy,
                    this.append);
        }
    }
}
