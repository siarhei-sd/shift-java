package com.korona.filtering_utility.dao.api;

public interface IFileReaderDao {
    void initializeReader(String filePath);
    String readLine();
    void closeReader();
}