package com.javaded78.service;

import com.javaded78.config.AppConfig;
import com.javaded78.config.PathOutFactory;
import com.javaded78.consumer.Consumer;
import com.javaded78.consumer.ConsumerFactory;
import com.javaded78.domain.DataType;
import com.javaded78.domain.stat.Statistics;
import com.javaded78.exception.ProcessFileException;
import com.javaded78.pool.AppExecutor;
import com.javaded78.producer.Producer;
import com.javaded78.producer.ProducerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.javaded78.util.FileNameConstant.FILE_NAME_FLOAT_OUT;
import static com.javaded78.util.FileNameConstant.FILE_NAME_INT_OUT;
import static com.javaded78.util.FileNameConstant.FILE_NAME_STRING_OUT;

public class FileProcessor implements Processor {

    private static final FileProcessor INSTANCE = new FileProcessor();
    private final ProducerFactory producerFactory = ProducerFactory.getInstance();
    private final ConsumerFactory consumerFactory = ConsumerFactory.getInstance();
    private final PathOutFactory pathOutFactory = PathOutFactory.getInstance();
    private final AppExecutor executor = AppExecutor.getInstance();

    private FileProcessor() {
    }

    @Override
    public List<Statistics> process(final AppConfig config) {

        List<Statistics> statistics = new ArrayList<>();
        try {
            Path fileIntOut = pathOutFactory.create(config, FILE_NAME_INT_OUT);
            Path fileFloatOut = pathOutFactory.create(config, FILE_NAME_FLOAT_OUT);
            Path fileStringOut = pathOutFactory.create(config, FILE_NAME_STRING_OUT);

            Producer producer = producerFactory.create(config);

            Consumer intConsumer = consumerFactory.create(config, fileIntOut, DataType.INTEGER);
            Consumer stringConsumer = consumerFactory.create(config, fileStringOut, DataType.STRING);
            Consumer floatConsumer = consumerFactory.create(config, fileFloatOut, DataType.FLOAT);

            executor.execute(producer);

            Future<Statistics> intStatsFuture = executor.submit(intConsumer);
            Future<Statistics> floatStatsFuture = executor.submit(floatConsumer);
            Future<Statistics> stringStatsFuture = executor.submit(stringConsumer);

            statistics.add(intStatsFuture.get());
            statistics.add(floatStatsFuture.get());
            statistics.add(stringStatsFuture.get());

            executor.shutdownAndAwaitTermination();

        } catch (ExecutionException | InterruptedException | RuntimeException e) {
            executor.shutdownAndAwaitTermination();
            Thread.currentThread().interrupt();
            throw new ProcessFileException(e.getMessage());
        }
        return statistics;
    }

    public static FileProcessor getInstance() {
        return INSTANCE;
    }
}
