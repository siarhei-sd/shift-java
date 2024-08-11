package com.javaded78.service.reader.impl;

import com.javaded78.service.reader.Reader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FileReader implements Reader {

    private static final FileReader INSTANCE = new FileReader();
    private final Logger logger = Logger.getLogger(FileReader.class.getName());

    private FileReader() {}

    @Override
    public Stream<String> readAll(final List<String> files) {
        return files.stream()
                .map(Paths::get)
                .flatMap(this::safeReadLines);
    }

    public Stream<String> safeReadLines(final Path filePath) {
        try {
            return Files.lines(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.severe(String.format("Error reading file: %s, %s", filePath.getFileName(), e.getMessage()));
            return Stream.empty();
        }
    }

    public static FileReader getInstance() {
        return INSTANCE;
    }
}
