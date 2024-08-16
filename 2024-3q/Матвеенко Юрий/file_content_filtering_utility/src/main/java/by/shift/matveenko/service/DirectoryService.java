package by.shift.matveenko.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryService {

    public static void createDirectory(String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            System.err.println("Failed to create directory");
        }
    }

    public static void deleteDirectory(String path) {
        try {
            Path directoryPath = Paths.get(path);
            if (Files.isDirectory(directoryPath) &&
                    !Files.newDirectoryStream(directoryPath).iterator().hasNext()) {
                Files.delete(directoryPath);
            }
        } catch (IOException e) {
            System.err.println("Failed to delete empty directory");
        }
    }
}