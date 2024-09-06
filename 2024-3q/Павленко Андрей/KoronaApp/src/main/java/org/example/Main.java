package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.core.exeptions.KoronaArgsNotFoundException;
import org.example.utils.FileFilterUtility;

import java.util.Arrays;

public final class Main {

    private Main() {}

    static final Logger logger = LogManager.getLogger("KoronaParser");

    public static void main(String[] args) {
        final var start = System.currentTimeMillis();
        logger.info("KoronaApp is started");

        if (args.length == 0) {
            logger.error("Any args are missed, inputs are {}", Arrays.asList(args));
            throw new KoronaArgsNotFoundException("Any args are missed, inputs are {}");
        }

        new FileFilterUtility(args);

        final var executionTime = System.currentTimeMillis() - start;
        logger.info("KoronaApp is successfully finished in {} ms", executionTime);
    }
}