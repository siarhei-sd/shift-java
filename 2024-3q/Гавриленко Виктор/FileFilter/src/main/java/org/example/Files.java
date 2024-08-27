package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.example.Utils.isInteger;
import static org.example.Utils.isNumber;

public class Files {
    private final List<String> intList = new ArrayList<>();
    private final List<String> floatList = new ArrayList<>();
    private final List<String> stringList = new ArrayList<>();

    public static final String INTEGER_FILE_NAME = "integers.txt";
    public static final String FLOATS_FILE_NAME = "floats.txt";
    public static final String STRINGS_FILE_NAME = "strings.txt";
    public static final String ERROR_CREATING_FILE = "Error creating the file ";
    public static final String ERROR_CREATING_FOLDER = "Error creating folder";
    public static final String ERROR_WRITING_TO_FILE = "Error writing to the file";
    public static final String FILE_NOT_FOUND = "The file with the name: %s was not found!";
    public static final String SLASH = "/";
    public List<String> getIntList() {
        return intList;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public List<String> getFloatList() {
        return floatList;
    }

    private void readFile(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (isNumber(line)) {
                    if (isInteger(line)) {
                        intList.add(line);
                    } else {
                        floatList.add(line);
                    }
                } else {
                    stringList.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.printf((FILE_NOT_FOUND) + "%n", fileName);
        }
    }

    public void readFiles(List<String> listFiles) {
        for (String fileName : listFiles) {
            readFile(fileName);
        }
    }

    public void createSortFiles(String path, String prefix, boolean argA) {
        if (!path.isEmpty()) {
            createFolder(path);
        }

        String pathPrefix;
        if (path.isEmpty()) {
            pathPrefix = prefix;
        } else {
            pathPrefix = path + SLASH + prefix;
        }

        if (!intList.isEmpty()) {
            createSortFile(pathPrefix + INTEGER_FILE_NAME);
            writeSortFile(pathPrefix + INTEGER_FILE_NAME, intList, argA);
        }
        if (!floatList.isEmpty()) {
            createSortFile(pathPrefix + FLOATS_FILE_NAME);
            writeSortFile(pathPrefix + FLOATS_FILE_NAME, floatList, argA);
        }
        if (!stringList.isEmpty()) {
            createSortFile(pathPrefix + STRINGS_FILE_NAME);
            writeSortFile(pathPrefix + STRINGS_FILE_NAME, stringList, argA);
        }
    }

    private void createFolder(String path) {
        File newFolder = new File(path);
        if (!newFolder.exists()) {
            boolean isCreated = newFolder.mkdirs();
            if (!isCreated) {
                System.out.println(ERROR_CREATING_FOLDER);
            }
        }
    }

    private void createSortFile(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                boolean isCreated = file.createNewFile();
                if (!isCreated) {
                    System.out.println(ERROR_CREATING_FILE + fileName);
                }
            }
        } catch (IOException e) {
            System.out.println(ERROR_CREATING_FILE + fileName);
        }
    }

    private void writeSortFile(String fileName, List<String> list, boolean argA) {
        try (FileWriter writer = new FileWriter(fileName, argA)) {
            for (String line : list)
                writer.write(line + '\n');
        } catch (IOException e) {
            System.out.println(ERROR_WRITING_TO_FILE);
        }
    }
}