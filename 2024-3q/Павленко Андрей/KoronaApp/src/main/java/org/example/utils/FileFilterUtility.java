package org.example.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.core.enums.DataType;
import org.example.core.exeptions.KoronaFileHasNotDeletedException;
import org.example.core.exeptions.KoronaFileHasNotReadException;
import org.example.core.exeptions.KoronaFileHasNotWrittenException;
import org.example.services.CommandLineService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;

public class FileFilterUtility {

    protected static final Logger logger = LogManager.getLogger("KoronaParser");

    public FileFilterUtility(final String[] args) {

        final var params = CommandLineService.getParams(args);
        final var isAppend = params.hasOption("a");
        final var prefix = params.hasOption("p")
                ? params.getOptionValue("p")
                : "";

        final var integersFileName = MessageFormat.format("{0}{1}{2}", createDirectoryIfNeeded(params), prefix, "integers.txt");
        final var floatsFileName   = MessageFormat.format("{0}{1}{2}", createDirectoryIfNeeded(params), prefix, "floats.txt");
        final var stringsFileName  = MessageFormat.format("{0}{1}{2}", createDirectoryIfNeeded(params), prefix, "strings.txt");

        try (var intFos     = new FileOutputStream(integersFileName, isAppend);
             var floatFos   = new FileOutputStream(floatsFileName, isAppend);
             var stringsFos = new FileOutputStream(stringsFileName, isAppend)
        ) {
            params.getArgList()
                  .stream()
                  .map(Path::of)
                  .forEach(inputPath -> parseFile(inputPath, intFos, floatFos, stringsFos));
        } catch (final IOException ex) {
            logger.error("Cannot open or create out files", ex);
            throw new KoronaFileHasNotWrittenException("Cannot open or create out files : " + ex.getMessage());
        }

        if (isStatisticRequired(params)) {
            printStats(
                    params,
                    stringsFileName,
                    floatsFileName,
                    integersFileName
            );
        }

        removeEmptyOutFiles(
                Path.of(stringsFileName),
                Path.of(floatsFileName),
                Path.of(integersFileName)
        );
    }

    private void removeEmptyOutFiles(
            Path stringsPath,
            Path floatsPath,
            Path integersPath
    ) {
        try {
            if (Files.size(stringsPath) == 0) {
                Files.delete(stringsPath);
            }

            if (Files.size(floatsPath) == 0) {
                Files.delete(floatsPath);
            }

            if (Files.size(integersPath) == 0) {
                Files.delete(integersPath);
            }
        } catch (IOException ex) {
            logger.error("Cannot remove empty files", ex);
            throw new KoronaFileHasNotDeletedException("Cannot remove empty files : " + ex.getMessage(), ex);
        }
    }

    private String createDirectoryIfNeeded(final CommandLine params) {
        if (!params.hasOption("o")) {
            return "";
        }

        final var directoryName = Path.of(params.getOptionValue("o"));

        try {
            if (!Files.exists(directoryName)) {
                Files.createDirectories(directoryName);
            }

            return MessageFormat.format("./{0}/", directoryName);
        } catch (final IOException ex) {
            throw new KoronaFileHasNotWrittenException("Cannot create directory {}", directoryName);
        }
    }

    private boolean isStatisticRequired(final CommandLine params) {
        return params.hasOption("f") || params.hasOption("s");
    }

    private void printStats(
            final CommandLine params,
            final String stringsFileName,
            final String floatsFileName,
            final String integersFileName
    ) {
        try (var integersStream = Files.lines(Path.of(integersFileName), StandardCharsets.UTF_8);
             var floatsStream = Files.lines(Path.of(floatsFileName), StandardCharsets.UTF_8);
             var stringsStream = Files.lines(Path.of(stringsFileName), StandardCharsets.UTF_8)
        ) {
            final var intStats = integersStream
                    .mapToInt(Integer::parseInt)
                    .summaryStatistics();
            final var floatStats = floatsStream
                    .mapToDouble(Double::parseDouble)
                    .summaryStatistics();
            final var stringsStats = stringsStream
                    .mapToInt(String::length)
                    .summaryStatistics();

            if (params.hasOption("f")) {
                logger.info("Full statistic are :");

                logger.info(
                        "- strings: {}",
                        stringsStats.getCount() > 0
                                ? stringsStats
                                : "There are no rows to handle"
                );

                logger.info(
                        "- floats: {}",
                        floatStats.getCount() > 0
                                ? floatStats
                                : "There are no rows to handle"
                );

                logger.info(
                        "- integers: {}",
                        intStats.getCount() > 0
                                ? intStats
                                : "There are no rows to handle"
                );
            }

            if (params.hasOption("s")) {
                logger.info("Short statistic are :");

                logger.info(
                        "- strings: {}",
                        stringsStats.getCount() > 0
                                ? stringsStats.getCount()
                                : "There are no rows to handle"
                );

                logger.info(
                        "- floats: {}",
                        floatStats.getCount() > 0
                                ? floatStats.getCount()
                                : "There are no rows to handle"
                );

                logger.info(
                        "- integers: {}",
                        intStats.getCount() > 0
                                ? intStats.getCount()
                                : "There are no rows to handle"
                );
            }

        } catch (final IOException ex) {
            logger.error("Cannot calculate stats", ex);
            throw new KoronaFileHasNotReadException("Cannot calculate stats : " + ex.getMessage(), ex);
        }
    }

    private void parseFile(
            final Path path,
            final FileOutputStream intFos,
            final FileOutputStream floatFos,
            final FileOutputStream stringsFos
    ) {
        try (var fileStream = Files.lines(path, StandardCharsets.UTF_8)) {
            fileStream.forEach(row -> handleFileRow(row, intFos, floatFos, stringsFos));
        } catch (final IOException ex) {
            logger.warn("Cannot open file {}", path);
        }
    }

    private void handleFileRow(
            final String row,
            final FileOutputStream intFos,
            final FileOutputStream floatFos,
            final FileOutputStream stringsFos
    ) {
        final var dataType = getDataType(row);

        switch (dataType) {
            case FLOAT -> writeToFile(row, floatFos);
            case INTEGER -> writeToFile(row, intFos);
            default -> writeToFile(row, stringsFos);
        }
    }

    private void writeToFile(
            final String row,
            final FileOutputStream fos
    ) {
        try {
            final var formatted = MessageFormat.format("{0}{1}", row, "\n");
            fos.write(formatted.getBytes(StandardCharsets.UTF_8));
        } catch (final IOException ex) {
            logger.error("Cannot write to file", ex);
            throw new KoronaFileHasNotWrittenException("Cannot write to file : " + ex.getMessage(), ex);
        }
    }

    private DataType getDataType(final String row) {
        try {
            Integer.parseInt(row);
            return DataType.INTEGER;
        } catch (final NumberFormatException ignored) {}

        try {
            Float.parseFloat(row);
            return DataType.FLOAT;
        } catch (final NumberFormatException ignored) {}

        return DataType.STRING;
    }
}