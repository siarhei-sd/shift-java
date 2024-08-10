package org.dezzzl.filewriter;

import lombok.Getter;
import org.dezzzl.Configuration;
import org.dezzzl.util.FilePathUtil;
import org.dezzzl.exception.PathIsNotValidException;
import java.io.File;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Getter
public class FileWriterManager {
    public static final String DEFAULT_FILE_PATH_TO_SAVE = "";
    public static final String DEFAULT_FILENAME_INTEGERS = "integers.txt";
    public static final String DEFAULT_FILENAME_STRINGS = "strings.txt";
    public static final String DEFAULT_FILENAME_FLOATS = "floats.txt";
    private final Configuration configuration;
    private final List<DefaultFileWriter> fileWriters = new ArrayList<>();
    private final StandardOpenOption appendToTheEndOption;

    public FileWriterManager(Configuration configuration) {
        this.configuration = configuration;
        this.appendToTheEndOption = this.configuration.isAppendToTheEndOption() ?
                StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING;
        setUp();
    }

    private void setUp() {
        try {
            FilePathUtil.parse(this.configuration.getPathToSave());
        } catch (PathIsNotValidException e) {
            this.configuration.setPathToSave(DEFAULT_FILE_PATH_TO_SAVE);
        }
        String pathForIntegers = FilePathUtil.getRealPathAccordingToConfig(this.configuration.getFilePrefix(),
                this.configuration.getPathToSave(), DEFAULT_FILENAME_INTEGERS);
        String pathForFloats = FilePathUtil.getRealPathAccordingToConfig(this.configuration.getFilePrefix(),
                this.configuration.getPathToSave(), DEFAULT_FILENAME_FLOATS);
        String pathForStrings = FilePathUtil.getRealPathAccordingToConfig(this.configuration.getFilePrefix(),
                this.configuration.getPathToSave(), DEFAULT_FILENAME_STRINGS);
        File fileForIntegers = FilePathUtil.createFile(pathForIntegers);
        File fileForFloats = FilePathUtil.createFile(pathForFloats);
        File fileForStrings = FilePathUtil.createFile(pathForStrings);
        fileWriters.addAll(List.of(
                new IntegerFileWriter(fileForIntegers, this.appendToTheEndOption),
                new FloatFileWriter(fileForFloats, this.appendToTheEndOption),
                new StringFileWriter(fileForStrings, this.appendToTheEndOption)
        ));
    }

    public void writeLine(String line) throws IOException {
        for (DefaultFileWriter fileWriter : this.fileWriters) {
            boolean isWritten = fileWriter.writeToFile(line);
            if (isWritten) break;
        }
    }

    public void closeConnections() {
        for (DefaultFileWriter fileWriter : this.fileWriters) {
            try {
                fileWriter.closeConnection();
            } catch (IOException e) {
                System.out.println("File closing error");;
            }
        }
    }


}
