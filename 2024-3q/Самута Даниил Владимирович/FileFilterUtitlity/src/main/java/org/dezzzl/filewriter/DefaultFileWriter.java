package org.dezzzl.filewriter;

import lombok.Getter;
import lombok.Setter;
import org.dezzzl.statistics.Statistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.StandardOpenOption;

@Getter
@Setter
public abstract class DefaultFileWriter {

    private final File file;
    private final StandardOpenOption appendToTheEndOption;
    private BufferedWriter writer;
    protected Statistics<Object> statistics;

    public DefaultFileWriter(File file, StandardOpenOption appendToTheEndOption) {
        this.file = file;
        this.appendToTheEndOption = appendToTheEndOption;
        this.writer = null;
    }


    public abstract boolean writeToFile(String line) throws IOException;

    public void closeConnection() throws IOException {
        if (this.writer != null) writer.close();
    }

    public void setStatistics(Statistics<Object> statistics) {
        this.statistics = statistics;
        statistics.setPathToFile(this.file.getPath());
    }

    public void printStatistics() {
        if (this.statistics != null) this.statistics.printStatistics();
    }
}
