package com.TestTask.filemanager;

import com.TestTask.constants.Constants;
import com.TestTask.parsing.ParserValues;
import com.TestTask.parsing.StartOptions;

import java.io.*;
import java.util.Scanner;

public class FileDate {
    private final StartOptions startOptions;
    int intCounter = 0;
    int floatCounter = 0;
    int strCounter = 0;

    private File intFileName;
    private File strFileName;
    private File floatFileName;

    private PrintWriter intFileWriter;
    private PrintWriter strFileWriter;
    private PrintWriter floatFileWriter;

    public FileDate(StartOptions startOptions) {

        this.startOptions = startOptions;
        String pathOut = startOptions.getOutPath();
        String separator = String.valueOf(File.separatorChar);

        if (!(startOptions.getOutPath().isEmpty())) {
            pathOut += separator;
        }

        try {

            intFileName = new File(pathOut + startOptions.getFilePrefix() + Constants.INTEGER);
            floatFileName = new File(pathOut + startOptions.getFilePrefix() + Constants.FLOAT);
            strFileName = new File(pathOut + startOptions.getFilePrefix() + Constants.STRING);

            intFileWriter = new PrintWriter(new FileWriter(intFileName, startOptions.isAppendMode()));
            floatFileWriter = new PrintWriter(new FileWriter(floatFileName, startOptions.isAppendMode()));
            strFileWriter = new PrintWriter(new FileWriter(strFileName, startOptions.isAppendMode()));

        } catch (IOException e) {
            System.out.println();
            System.out.println(" Error!!! Cannot find the directory path: " + e.getMessage());
            System.out.println("Enter please a valid directory path");
        }
    }

    public PrintWriter getIntFileWriter() {
        return intFileWriter;
    }

    public PrintWriter getStrFileWriter() {
        return strFileWriter;
    }

    public PrintWriter getFloatFileWriter() {
        return floatFileWriter;
    }

    public File getIntFileName() {
        return intFileName;
    }

    public File getStrFileName() {
        return strFileName;
    }

    public File getFloatFileName() {
        return floatFileName;
    }

    public void filterFile(String fileName) {
        File file = new File(fileName);
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                  filterValues(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println();
            System.out.println("Error!!! Input File not found!!!");
            System.out.println("Please enter correct input File.");
        }
    }

    /**
     * запись в файл в зависимости от типа значений
     */
    public void filterValues(String line) {
        if (ParserValues.isInteger(line)) {
            getIntFileWriter().append(line).append('\n').flush();
            intCounter++;
        } else if (ParserValues.isFloat(line)) {
            getFloatFileWriter().append(line).append('\n').flush();
            floatCounter++;
        } else {
            getStrFileWriter().append(line).append('\n').flush();
            strCounter++;
        }
    }

    public void deleteFile() {
        checkFileIsEmpty(intCounter, getIntFileWriter(), intFileName);
        checkFileIsEmpty(strCounter, getStrFileWriter(), strFileName);
        checkFileIsEmpty(floatCounter, getFloatFileWriter(), floatFileName);
    }

    public void checkFileIsEmpty(int counter, PrintWriter writer, File file) {
        if (counter == 0) {
            writer.close();
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
