package com.korona.filtering_utility.dao;

import com.korona.filtering_utility.dao.api.IFileReaderDao;
import com.korona.filtering_utility.dao.api.IFileWriterDao;
import com.korona.filtering_utility.exeption.FileDaoException;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class FileDao implements IFileReaderDao, IFileWriterDao {
    private BufferedWriter writer;
    private BufferedReader reader;
    private Iterator<String> lineIterator;

    public FileDao() {
    }

    @Override
    public void initializeReader(String filePath) {
        try {
            reader = new BufferedReader(new FileReader(filePath));
            lineIterator = reader.lines().iterator();
        } catch (IOException e) {
            throw new FileDaoException("Failed to initialize reader for file: " + filePath + ". Processing has been interrupted", e);
        }
    }

    @Override
    public void initializeWriter(String filePath, boolean append) {
        try {
            writer = new BufferedWriter(new FileWriter(filePath, append));
        } catch (IOException e) {
            throw  new FileDaoException("Failed to initialize writer for file: " + filePath + ". Processing has been interrupted", e);
        }
    }

    @Override
    public String readLine() {
        if (lineIterator != null && lineIterator.hasNext()) {
            return lineIterator.next();
        }
        return null;
    }

    @Override
    public void writeLine(String line) {
        if (writer != null) {
            try {
                writer.write(line);
                writer.newLine();
            } catch (IOException e) {
                throw new FileDaoException("Failed to write line to file. Processing has been interrupted", e);
            }
        }
    }

    @Override
    public void closeReader() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                throw new FileDaoException("Failed to close reader. Processing has been interrupted", e);
            }
        }
    }

    @Override
    public void closeWriter() {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                throw new FileDaoException("Failed to close writer. Processing has been interrupted" , e);
            }
        }
    }

    public boolean isWriterInitialized() {
        return writer != null;
    }

}
