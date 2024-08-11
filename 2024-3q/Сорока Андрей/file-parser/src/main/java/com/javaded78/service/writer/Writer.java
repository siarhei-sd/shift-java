package com.javaded78.service.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;

public interface Writer {

    void inspect(Path outputFile, boolean append);

    BufferedWriter write(BufferedWriter bufferedWriter, String record, Path outputFile) throws IOException;
}
