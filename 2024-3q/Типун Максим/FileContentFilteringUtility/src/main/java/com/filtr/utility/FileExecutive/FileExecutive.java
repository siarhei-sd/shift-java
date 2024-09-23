package com.filtr.utility.FileExecutive;

import java.io.*;
import java.util.Scanner;


public class FileExecutive {
    // проверим файл на пустоту
    int intCount = 0;
    int floatCount = 0;
    int stringCount = 0;


    public FileExecutive(boolean appendLine, String path, String prefix) {
        this.outputPath = path;
        this.filePrefix = prefix;

        String separator = String.valueOf(File.separatorChar);

        if (!path.isEmpty()){
            path += separator;
        }

        try {
            String intFileName = path  + prefix + "integers.txt";
            String strFileName = path  + prefix + "strings.txt";
            String floatFileName = path + prefix + "floats.txt";

            // Файлы для каждого типа строки из файла.
            integersFile = new File(intFileName);
            stringsFile = new File(strFileName);
            floatsFile = new File(floatFileName);

            // Инициализация PrintWriter с опцией записи / перезаписи файлов.
            intWriter = new PrintWriter(new FileWriter(integersFile, appendLine));
            stringWriter = new PrintWriter(new FileWriter(stringsFile, appendLine));
            floatWriter = new PrintWriter(new FileWriter(floatsFile, appendLine));
        } catch (IOException e) {
            System.out.println();
            System.out.println(" Error! Cannot find the specified path: " + e.getMessage());
            System.out.println(" Please enter the correct path ");
            System.exit(1);
        }
    }
    private String outputPath;
    private String filePrefix;

    // PrintWriter для каждого типа строки
    private PrintWriter intWriter;
    private PrintWriter stringWriter;
    private PrintWriter floatWriter;

    String intFileName = outputPath + filePrefix + "integers.txt";
    String strFileName = outputPath + filePrefix + "strings.txt";
    String floatFileName = outputPath + filePrefix + "floats.txt";

    // Файлы для каждого типа строки
    private File integersFile = new File(intFileName);
    private File stringsFile = new File(strFileName);
    private File floatsFile = new File(floatFileName);

    // Регулярные выражения


    public String getIntRegex() {
        return "^[+-]?\\d+$";
    }


    public String getStringRegex() {
        return "^(?![+-]?\\d)(?!\\n)[^\\d ].*$";
    }


    public String getFloatRegex() {
        return "[+-]?\\d+\\.\\d+([eE][+-]?\\d+)?";
    }

    public PrintWriter getIntWriter() {
        return intWriter;
    }

    public PrintWriter getStringWriter() {
        return stringWriter;
    }

    public PrintWriter getFloatWriter() {
        return floatWriter;
    }

    public File getIntegersFile() {
        return integersFile;
    }

    public File getStringsFile() {
        return stringsFile;
    }

    public File getFloatsFile() {
        return floatsFile;
    }

    public void filterFile(String fileName){
        File file = new File(fileName);
        try(Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()) {
                filterLine(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println();
            System.out.println(" Error! File not found: " + e.getMessage());
            System.out.println(" Please enter the correct file ");
            System.exit(1);
        }
    }

    //запись в файлы и сбор статистика
    public void filterLine(String line){
        if (line.matches(getIntRegex())) {
            intCount++;
            getIntWriter().append(line).append('\n').flush();
        }
        if (line.matches(getStringRegex())) {
            stringCount++;
            getStringWriter().append(line).append('\n').flush();
        }
        if (line.matches(getFloatRegex())) {
            floatCount++;
            getFloatWriter().append(line).append('\n').flush();
        }
    }

    public void checkAndDelete(int counter,
                               PrintWriter writer,
                               File file)
    {
        if (counter == 0) {
            writer.close();
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public void deleteFileIfEmpty() {
        checkAndDelete(intCount, getIntWriter(), integersFile);
        checkAndDelete(stringCount, getStringWriter(), stringsFile);
        checkAndDelete(floatCount, getFloatWriter(), floatsFile);
    }

}
