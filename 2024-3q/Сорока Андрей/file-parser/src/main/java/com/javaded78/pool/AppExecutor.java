package com.javaded78.pool;

import com.javaded78.consumer.Consumer;
import com.javaded78.domain.stat.Statistics;
import com.javaded78.exception.ProcessFileException;
import com.javaded78.producer.Producer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class AppExecutor {

    private static final AppExecutor INSTANCE = new AppExecutor();
    public static final int N_THREADS = 4;
    public static final int TIMEOUT = 150;
    private final ExecutorService executor;
    private final Logger logger = Logger.getLogger(AppExecutor.class.getName());

    private AppExecutor() {
        this.executor = Executors.newFixedThreadPool(N_THREADS);
    }

    public Future<Statistics> submit(final Consumer consumer) {
        return executor.submit(consumer);
    }

    public void execute(final Producer producer) {
        executor.execute(producer);
    }

    public void shutdownAndAwaitTermination() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                logger.info("Failed to terminate ExecutorService within 5 seconds. Force stopping.");
                executor.shutdownNow();
                if (!executor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                    logger.info("ExecutorService did not terminate!");
                }
            }
        } catch (InterruptedException e) {
            logger.info("Waiting for ExecutorService to complete was interrupted.");
            executor.shutdownNow();
            Thread.currentThread().interrupt();
            throw new ProcessFileException(e.getMessage());
        }
    }

    public static AppExecutor getInstance() {
        return INSTANCE;
    }
}
