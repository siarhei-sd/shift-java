package org.dezzzl.filewriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class IntegerFileWriter extends DefaultFileWriter {

    public IntegerFileWriter(File file, StandardOpenOption appendToTheEndOption) {
        super(file, appendToTheEndOption);
    }

    @Override
    public boolean writeToFile(String line) throws IOException {
        if (!isInteger(line)) return false;
        if (this.getWriter() == null) this.setWriter(
                Files.newBufferedWriter(getFile().toPath(),
                        getAppendToTheEndOption(),
                        StandardOpenOption.CREATE)
        );
        this.getWriter().write(line);
        this.getWriter().newLine();
        return true;
    }

    private boolean isInteger(String line) {
        try {
            int value = Integer.parseInt(line);
            if (this.statistics != null) this.statistics.updateStatistics(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
