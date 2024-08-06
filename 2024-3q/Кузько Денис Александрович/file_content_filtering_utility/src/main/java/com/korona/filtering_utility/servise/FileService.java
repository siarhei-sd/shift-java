package com.korona.filtering_utility.servise;

import com.korona.filtering_utility.dao.FileDao;
import com.korona.filtering_utility.dao.api.IFileReaderDao;
import com.korona.filtering_utility.dao.api.IFileWriterDao;
import com.korona.filtering_utility.servise.api.IFileService;
import com.korona.filtering_utility.servise.api.IStatisticsService;
import com.korona.filtering_utility.servise.util.DataClassifier;

import java.util.List;

public class FileService implements IFileService {
    private static final String DEFAULT_FILE_PATH_FOR_INTEGERS = "integer.txt";
    private static final String DEFAULT_FILE_PATH_FOR_FLOATS = "floats.txt";
    private static final String DEFAULT_FILE_PATH_FOR_STRINGS = "strings.txt";

    private final IFileWriterDao integerFileDao;
    private final IFileWriterDao floatFileDao;
    private final IFileWriterDao stringFileDao;
    private final IFileReaderDao readFileDao;

    private final IStatisticsService statisticsService;

    private String filePathForIntegers;
    private String filePathForFloats;
    private String filePathForStrings;

    public FileService(IStatisticsService statisticsService) {
        this.integerFileDao = new FileDao();
        this.floatFileDao = new FileDao();
        this.stringFileDao = new FileDao();
        this.readFileDao = new FileDao();
        this.statisticsService = statisticsService;
    }

    @Override
    public void filterData(List<String> inputFiles, boolean append) {

        try {
            for (String inputFile : inputFiles) {
                try {
                    readFileDao.initializeReader(inputFile);
                    String line;

                    while ((line = readFileDao.readLine()) != null) {
                        classifyAndWriteLine(line, append);
                    }
                } finally {
                    readFileDao.closeReader();
                }
            }
        } finally {
            closeWriters();
        }
    }

    @Override
    public void setFilePaths(String outputDir, String prefix) {
        if (statisticsService != null) {
            statisticsService.setFileNames(
                    new String[]{
                            prefix + DEFAULT_FILE_PATH_FOR_INTEGERS,
                            prefix + DEFAULT_FILE_PATH_FOR_FLOATS,
                            prefix + DEFAULT_FILE_PATH_FOR_STRINGS}
            );
        }

        if (outputDir == null) {
            outputDir = "";
        }

        if (prefix == null) {
            prefix = "";
        }

        filePathForIntegers = outputDir + prefix + DEFAULT_FILE_PATH_FOR_INTEGERS;
        filePathForFloats = outputDir + prefix + DEFAULT_FILE_PATH_FOR_FLOATS;
        filePathForStrings = outputDir + prefix + DEFAULT_FILE_PATH_FOR_STRINGS;

    }

    private void classifyAndWriteLine(String line, boolean append) {
        if (DataClassifier.isInteger(line)) {
            if (!integerFileDao.isWriterInitialized()) {
                integerFileDao.initializeWriter(filePathForIntegers, append);
            }

            integerFileDao.writeLine(line);

            if (statisticsService != null) {
                statisticsService.addIntegerData(line);
            }

        } else if (DataClassifier.isFloat(line)) {
            if (!floatFileDao.isWriterInitialized()) {
                floatFileDao.initializeWriter(filePathForFloats, append);
            }

            floatFileDao.writeLine(line);

            if (statisticsService != null) {
                statisticsService.addFloatData(line);
            }

        } else {
            if (!stringFileDao.isWriterInitialized()) {
                stringFileDao.initializeWriter(filePathForStrings, append);
            }

            stringFileDao.writeLine(line);

            if (statisticsService != null) {
                statisticsService.addStringData(line);
            }

        }
    }

    private void closeWriters() {
        if (integerFileDao.isWriterInitialized()) {
            integerFileDao.closeWriter();
        }

        if (floatFileDao.isWriterInitialized()) {
            floatFileDao.closeWriter();
        }

        if (stringFileDao.isWriterInitialized()) {
            stringFileDao.closeWriter();
        }
    }
}
