package com.utility.files_operating;

import com.utility.management.StatisticGather;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FilesWriter {

    private static BufferedWriter longWriter;
    private static BufferedWriter stringWriter;
    private static BufferedWriter doubleWriter;

    private static StatisticGather GATHER;

    private static String DIRECTORY;
    private static String INTEGERSFILE="integers.txt";
    private static String STRINGSFILE="strings.txt";
    private static String FLOATSFILE="floats.txt";
    private static StandardOpenOption APPEND=StandardOpenOption.TRUNCATE_EXISTING;

    private FilesWriter (){}

    public static void writeString(String string){
        if(stringWriter==null)openString();
        try {
            stringWriter.write(string);
            stringWriter.newLine();
            if(GATHER!=null) GATHER.stringStatistics(string);
        } catch (IOException e) {
            System.out.println("Error while writing to: \""+STRINGSFILE+"\"");
            throw new RuntimeException();
        }
    }

    private static void openString() {
        try {
            stringWriter = Files.newBufferedWriter(Path.of(DIRECTORY, STRINGSFILE), StandardOpenOption.CREATE, StandardOpenOption.WRITE, APPEND);
        } catch (IOException e) {
            System.out.println("Error in opening or creating file: \""+STRINGSFILE+"\"");
            throw new RuntimeException();
        } catch (SecurityException e) {
            System.out.println("Access denied to write to files: \""+STRINGSFILE+"\"");
            throw new RuntimeException();
        }
    }

    public static void writeDouble(String aDouble){
        if(doubleWriter ==null) openDouble();
        try {
            doubleWriter.write(aDouble);
            doubleWriter.newLine();
            if(GATHER!=null) GATHER.doubleStatistics(aDouble);
        } catch (IOException e) {
            System.out.println("Error while writing to: \""+FLOATSFILE+"\"");
            throw new RuntimeException();
        }
    }

    private static void openDouble(){
        try {
            doubleWriter = Files.newBufferedWriter(Path.of(DIRECTORY, FLOATSFILE), StandardOpenOption.CREATE, StandardOpenOption.WRITE, APPEND);
        } catch (IOException e) {
            System.out.println("Error in opening or creating file: \""+FLOATSFILE+"\"");
            throw new RuntimeException();
        } catch (SecurityException e) {
            System.out.println("Access denied to write to files: \""+FLOATSFILE+"\"");
            throw new RuntimeException();
        }
    }

    public static void writeLong(String aLong){
        if(longWriter ==null) openLong();
        try {
            longWriter.write(aLong);
            longWriter.newLine();
            if(GATHER!=null) GATHER.longStatistics(aLong);
        } catch (IOException e) {
            System.out.println("Error while writing to: \""+INTEGERSFILE+"\"");
            throw new RuntimeException();
        }
    }

    private static void openLong(){
        try {
            longWriter = Files.newBufferedWriter(Path.of(DIRECTORY, INTEGERSFILE), StandardOpenOption.CREATE, StandardOpenOption.WRITE, APPEND);
        } catch (IOException e) {
            System.out.println("Error in opening or creating file: \""+INTEGERSFILE+"\"");
            throw new RuntimeException();
        } catch (SecurityException e) {
            System.out.println("Access denied to write to files: \""+INTEGERSFILE+"\"");
            throw new RuntimeException();
        }
    }

    public static void settingsSetter(String directory, String prefix, boolean append, StatisticGather gather) {
        DIRECTORY = directory;
        INTEGERSFILE = prefix+INTEGERSFILE;
        STRINGSFILE = prefix+STRINGSFILE;
        FLOATSFILE = prefix+FLOATSFILE;
        if(append) APPEND=StandardOpenOption.APPEND;
        GATHER=gather;
    }

    public static String closeWriter(){
        try {
            if(longWriter !=null) longWriter.close();
            if(doubleWriter !=null) doubleWriter.close();
            if (stringWriter!=null) stringWriter.close();
        } catch (IOException e) {
            System.out.println("Error while closing files");
        }
        if(GATHER!=null) return GATHER.getStatistics(INTEGERSFILE, FLOATSFILE, STRINGSFILE);
        return "Statistics mode off";
    }
}
