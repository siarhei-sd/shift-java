package com.javaded78.service.writer.impl;

import com.javaded78.exception.WriteFileException;
import com.javaded78.service.writer.Writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileWriter implements Writer {

    private static final FileWriter INSTANCE = new FileWriter();

    private FileWriter() {}

    @Override
    public void inspect(final Path outputFile,
                        final boolean append) {
        try {
            if (!append) {
                Files.deleteIfExists(outputFile);
            }
            Files.createDirectories(outputFile.getParent());
        } catch (IOException e) {
            throw new WriteFileException(e.getMessage());
        }
    }

    @Override
    public BufferedWriter write(BufferedWriter bufferedWriter,
                                final String record,
                                final Path outputFile) throws IOException {
        if (bufferedWriter == null) {
            bufferedWriter = Files.newBufferedWriter(outputFile, CREATE, APPEND);
        }
        bufferedWriter.write(record);
        bufferedWriter.newLine();
        return bufferedWriter;
    }

    public static FileWriter getInstance() {
        return INSTANCE;
        }
}
