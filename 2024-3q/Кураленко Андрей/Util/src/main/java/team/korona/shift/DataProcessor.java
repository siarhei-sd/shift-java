package team.korona.shift;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataProcessor implements AutoCloseable {

    private static final String INTEGER_FILE_NAME = "integers.txt";
    private static final String FLOAT_FILE_NAME = "floats.txt";
    private static final String STRING_FILE_NAME = "strings.txt";
    private static final String NEW_LINE = "\n";

    private Writer intWriter = null;
    private Writer floatWriter = null;
    private Writer stringWriter = null;

    private final String outputPath;
    private final String prefix;
    private final boolean append;

    private final Statistics statistics;

    public DataProcessor(String outputPath, String prefix, boolean append, Statistics statistics) {
        this.outputPath = outputPath;
        this.prefix = prefix;
        this.append = append;
        this.statistics = statistics;
    }

    public void processLine(String line) throws IOException {
        if (DataUtils.isInteger(line)) {
            writeInteger(line);
        } else if (DataUtils.isFloat(line)) {
            writeFloat(line);
        } else {
            writeString(line);
        }
        statistics.updateStatistics(line);
    }

    private void writeInteger(String line) throws IOException {
        if (intWriter == null) {
            intWriter = getWriter(getFileName(INTEGER_FILE_NAME));
        }
        intWriter.write(line + NEW_LINE);
    }

    private void writeFloat(String line) throws IOException {
        if (floatWriter == null) {
            floatWriter = getWriter(getFileName(FLOAT_FILE_NAME));
        }
        floatWriter.write(line + NEW_LINE);
    }

    private void writeString(String line) throws IOException {
        if (stringWriter == null) {
            stringWriter = getWriter(getFileName(STRING_FILE_NAME));
        }
        stringWriter.write(line + NEW_LINE);
    }

    private Writer getWriter(String fileName) throws IOException {
        Path filePath = Path.of(fileName);
        if (Files.notExists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }
        return new BufferedWriter(new FileWriter(fileName, append));
    }

    private String getFileName(String defaultFileName) {
        return Path.of(outputPath, prefix + defaultFileName).toString();
    }

    @Override
    public void close() throws IOException {
        if (intWriter != null) intWriter.close();
        if (floatWriter != null) floatWriter.close();
        if (stringWriter != null) stringWriter.close();
    }

    public void printStatistics() {
        System.out.println(statistics.getStatistics());
    }
}