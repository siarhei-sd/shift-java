package by.shift.matveenko.service;

import java.io.FileWriter;
import java.io.IOException;

public class FileMakerService {
    private final boolean addedResults;
    private FileWriter fileWriter;
    private String fileName;
    private boolean isWritingStarted = false;

    public FileMakerService(String name, String path, String prefix, boolean addedResults) {
        this.addedResults = addedResults;
        fileName = name;
        if (prefix != null) {
            fileName = prefix + name;
        }
        if (path != null) {
            fileName = path + "/" + fileName;
        }
    }

    public void addData(String str) throws Exception {
        if (!isWritingStarted) {
            isWritingStarted = true;
            createFile();
        }
        try {
            fileWriter.write(str + System.lineSeparator());
            fileWriter.flush();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Null pointer reference: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    private void createFile() throws Exception {
        try {
            fileWriter = new FileWriter(fileName, addedResults);
        } catch (IOException e) {
            throw new IOException("Error writing to file: " + e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException("Null pointer reference: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Unexpected error: " + e.getMessage());
        }
    }

    public void closeFile() {
        if (!isWritingStarted) {
            return;
        }
        try {
            fileWriter.close();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}