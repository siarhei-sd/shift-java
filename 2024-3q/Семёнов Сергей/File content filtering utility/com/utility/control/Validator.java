package com.utility.control;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class Validator {

    private Validator() {}

    public static boolean isLong(String line) {
        try {
            Long.parseLong(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String line) {
        try{
            Double.parseDouble(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDirectory(String directory) {
        Path validDirectory = getValidPath(directory);
        if (validDirectory==null) return false;
        try{
            if (Files.isDirectory(validDirectory)){
                return true;
            }
            System.out.println("Directory \""+directory+"\" does not exist");
            return false;
        } catch (SecurityException e){
            System.out.println("Access to \""+directory+"\" denied");
            return false;
        }
    }

    public static boolean isFile(String file) {
        Path validFile = getValidPath(file);
        if (validFile==null) return false;
        try {
             if (Files.isRegularFile(validFile)){
                 return true;
             }
             System.out.println("File \""+file+"\" does not exist");
             return false;
        } catch (SecurityException e){
            System.out.println("Access to \""+file+"\" denied");
            return false;
        }
     }

    public static boolean isNotOption(String option) {
        return !option.equals("-a") && !option.equals("-f") && !option.equals("-s")
                && !option.equals("-o") && !option.equals("-p");
    }

    private static Path getValidPath(String path) {
        Path resultPath;
        try {
            resultPath=Path.of(path);
        }catch(InvalidPathException e) {
            System.out.println("Invalid path: \""+path+"\"");
            return null;
        }
        return resultPath;
    }

    public static boolean isPREFIX(String prefix) {
        if (prefix.contains(":") || prefix.contains(";") || prefix.contains(">") || prefix.contains("<") || prefix.contains("\\") ||
                prefix.contains("/") || prefix.contains("*") || prefix.contains("?") || prefix.contains("|") || prefix.contains("\"")) {
            System.out.println("Invalid prefix: \"" + prefix+"\"");
            return false;
        }
        return true;
    }
}
