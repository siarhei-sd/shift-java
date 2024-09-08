package org.egorsemenovv.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class LazyFileWriter implements AutoCloseable {

    private BufferedWriter writer;
    private final Path filePath;
    private final boolean append;
    private boolean initialized = false;

    public LazyFileWriter(Path filePath, boolean append) {
        this.filePath = filePath;
        this.append = append;
    }

    public LazyFileWriter(Path filePath){
        this(filePath, false);
    }

    private void initializeWriter() throws IOException {
        if (writer == null) {
            if(append) {
                writer = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            }
            else {
                writer = Files.newBufferedWriter(filePath);
            }
        }
    }

    public void write(String line) throws IOException {
        if (!initialized) {
            initialized = true;
            initializeWriter();
        }
        writer.write(line);
        writer.newLine();
    }

    @Override
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}
