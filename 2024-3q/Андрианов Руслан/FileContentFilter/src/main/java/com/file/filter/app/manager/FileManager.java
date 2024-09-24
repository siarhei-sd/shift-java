package com.file.filter.app.manager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public List<String> readFromFiles(List<String> inputFiles) {
        List<String> filesContent = new ArrayList<>();
        for (String path : inputFiles) {
            File file = new File(path);
            if (!file.exists() || !file.isFile()) {
                System.out.println("File not found or not a file: " + path);
                continue;
            }
            filesContent.addAll(readFileContent(path));
        }
        return filesContent;
    }

    private List<String> readFileContent(String path) {
        List<String> fileContent = new ArrayList<>();
        try (BufferedReader bReader = new BufferedReader(new FileReader((path)))) {
            String line = bReader.readLine();

            while (line != null) {
                fileContent.add(line);
                line = bReader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("\nFile not found " + path);
        } catch (IOException e) {
            System.out.println("Error has occurred while reading file");
        }
        return fileContent;
    }

    public void writeToFile(List<String> results, String path, boolean isAppend) {
        if (!results.isEmpty()) {
            try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(path, isAppend))) {
                for (String str : results) {
                    bWriter.append(str);
                    bWriter.newLine();
                }
            } catch (IOException e) {
                System.out.println("Permission denied\nNo such file or directory :" + path);
            }
        }
    }
}
