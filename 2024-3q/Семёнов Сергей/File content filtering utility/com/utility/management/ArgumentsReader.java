package com.utility.management;

import com.utility.control.Validator;

import java.nio.file.Path;
import java.util.*;

public class ArgumentsReader {

    private ArgumentsReader() {}

    private static Path[] SOURCEFILES;
    private static String PREFIX="";
    private static Path PATH=Path.of("");
    private static boolean APPEND;
    private static boolean SHORT;
    private static boolean FULL;

    public static void read(String[] arguments){
        SOURCEFILES = new Path[arguments.length];
        int index=0;
        for (int i = 0; i < arguments.length; i++) {
            switch (arguments[i]) {
                case "-o" -> {if (i!=arguments.length-1 && Validator.isNotOption(arguments[i+1])) {
                    if (Validator.isDirectory(arguments[i+1])) PATH=Path.of(arguments[i+1]);
                    i++;
                }}
                case "-p" -> {if (i!=arguments.length-1 && Validator.isNotOption(arguments[i+1])) {
                    if (Validator.isPREFIX(arguments[i+1])) PREFIX=arguments[i+1];
                    i++;
                }}
                case "-a" -> APPEND = true;
                case "-s" -> SHORT = true;
                case "-f" -> FULL = true;
                default -> {if (Validator.isFile(arguments[i])) SOURCEFILES[index++] = Path.of(arguments[i]);}
            }
        }
        SOURCEFILES=Arrays.stream(SOURCEFILES).filter(Objects::nonNull).distinct().toArray(Path[]::new);
    }

    public static String getPREFIX() {
        return PREFIX;
    }

    public static Path getPATH() {
        return PATH;
    }

    public static boolean isAPPEND() {
        return APPEND;
    }

    public static boolean isSHORT() {
        return SHORT;
    }

    public static boolean isFULL() {
        return FULL;
    }

    public static Path[] getSourceFiles() {
        return SOURCEFILES;
    }
}
