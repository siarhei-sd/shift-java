package org.example;

import java.util.ArrayList;
import java.util.List;

public class Parameters {
    private static String destinationfolder = "src/main/resources/";
    private static String prefix = "";
    private static boolean addToFile = false;
    private static List<String> filesToRead = new ArrayList<>();

    private static boolean shortStats;

    public static String getDestinationfolder() {
        return destinationfolder;
    }

    public static void setDestinationfolder(String destinationfolder) {
        Parameters.destinationfolder = destinationfolder;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        Parameters.prefix = prefix;
    }

    public static boolean isAddToFile() {
        return addToFile;
    }

    public static void setAddToFile(boolean addToFile) {
        Parameters.addToFile = addToFile;
    }

    public static List<String> getFilesToRead() {
        return filesToRead;
    }

    public static void addFileToRead(String fileToRead) {
        Parameters.filesToRead.add(fileToRead);
    }

    public static boolean isShortStats() {
        return shortStats;
    }

    public static void setShortStats(boolean shortStats) {
        Parameters.shortStats = shortStats;
    }
}
