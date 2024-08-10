package org.dezzzl.filewriter;

import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

@Getter
public class StringFileWriter extends DefaultFileWriter {
    public StringFileWriter(File file, StandardOpenOption appendToTheEndOption) {
        super(file, appendToTheEndOption);
    }

    @Override
    public boolean writeToFile(String line) throws IOException {
        if (this.getWriter() == null) this.setWriter(
                Files.newBufferedWriter(getFile().toPath(),
                        getAppendToTheEndOption(),
                        StandardOpenOption.CREATE)
        );
        if (this.statistics != null) this.statistics.updateStatistics(line);
        this.getWriter().write(line);
        this.getWriter().newLine();
        return true;
    }
}
