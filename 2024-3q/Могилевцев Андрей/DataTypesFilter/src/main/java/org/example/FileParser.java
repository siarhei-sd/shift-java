package org.example;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class FileParser {
    public void readFile(String fileName) {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(getInputStream(fileName)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                for(String sample : parts ) {
                    determineType(sample);
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }

    private InputStream getInputStream(String filename) throws IOException, URISyntaxException {
        if(filename.contains(":\\")) return getInputStreamFromFileSystem(filename);
        else return getInputStreamFromResource(filename);
    }

    private InputStream getInputStreamFromResource(String fileName)  {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream resource = classLoader.getResourceAsStream(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return resource;
        }

    }

    private InputStream getInputStreamFromFileSystem(String filePath) throws IOException {
        return  Files.newInputStream(Paths.get(filePath));
    }

    public static void determineType(String str) {
        if (isInteger(str)) {
            MemoryRepository.addInteger(Integer.parseInt(str));
        } else if (isFloat(str)) {
            MemoryRepository.addFloat(Float.parseFloat(str));
        } else {
            MemoryRepository.addString(str);
        }
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public static boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
