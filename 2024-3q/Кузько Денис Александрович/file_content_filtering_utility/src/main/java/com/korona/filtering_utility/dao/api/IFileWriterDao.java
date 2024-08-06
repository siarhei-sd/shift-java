package com.korona.filtering_utility.dao.api;

public interface IFileWriterDao {
    void initializeWriter(String filePath, boolean append);

    void writeLine(String line);

    void closeWriter();

    boolean isWriterInitialized();
}