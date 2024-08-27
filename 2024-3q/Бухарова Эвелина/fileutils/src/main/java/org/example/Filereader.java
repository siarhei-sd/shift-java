package org.example;

import java.io.*;

public class Filereader {
    private static FileWriter intWriter;
    private static FileWriter doubleWriter;
    private static FileWriter stringWriter;

    public static void readFile(String fileName, boolean append, Statistic statistic, String outputPath, String filePrefix) {
        BufferedReader reader;
        FileWriter writerInteger;
        FileWriter writerDouble;
        FileWriter writerString;

        try {
            reader = new BufferedReader(new FileReader(fileName));

            String line = reader.readLine();
            while (line != null) {
                //регулярное выражение для поиска целых чисел
                boolean isInteger = line.matches("\\d+");
                //регулярное выражение для поиска чисел с точкой чисел, а также отрицательных и в степени
//                boolean isDouble = line.matches("^\\D+(-?\\d+)*");
                boolean isDouble = line.matches("^[-+]?[0-9]*[.,]?[0-9]+(?:[eE][-+]?[0-9]+)?$");
                if (isInteger) {4
                    writerInteger = createIntWriter(outputPath, filePrefix, append);
                    writerInteger.append(line);
                    writerInteger.append("\n");

                    int intLine = Integer.parseInt(line);
                    statistic.collectIntegers(intLine);
                } else if (isDouble) {
                    writerDouble = createDoubleWriter(outputPath, filePrefix, append);
                    writerDouble.append(line);
                    writerDouble.append("\n");

                    double doubleLine = Double.parseDouble(line);
                    statistic.collectDouble(doubleLine);
                } else {
                    writerString = createStringWriter(outputPath, filePrefix, append);
                    writerString.append(line);
                    writerString.append("\n");

                    int stringLine = line.length();
                    statistic.collectString(stringLine);
                }
                line = reader.readLine();
            }
            flushInt();
            flushDouble();
            flushString();
            reader.close();
        } catch (IOException e) {
            System.out.println("Failed to write file");
        }
    }

    private static FileWriter createWriter(String path, boolean append) throws IOException {
        File file = new File(path);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            //mkdirs-создание папки в которой лежит файл
            System.out.println("Could not create file");
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        return new FileWriter(file, append);
    }

    private static FileWriter createIntWriter(String outputPath, String filePrefix, boolean append) throws IOException {
        if (intWriter == null) {
            intWriter = createWriter(outputPath + filePrefix + "outInteger.txt", append);
        }
        return intWriter;
    }

    public static void flushInt() throws IOException {
        if (intWriter != null) {
            intWriter.flush();
        }
    }

    private static FileWriter createDoubleWriter(String outputPath, String filePrefix, boolean append) throws IOException {
        if (doubleWriter == null) {
            doubleWriter = createWriter(outputPath + filePrefix + "outDouble.txt", append);
        }
        return doubleWriter;
    }

    public static void flushDouble() throws IOException {
        if (doubleWriter != null) {
            doubleWriter.flush();
        }
    }

    private static FileWriter createStringWriter(String outputPath, String filePrefix, boolean append) throws IOException {
        if (stringWriter == null) {
            stringWriter = createWriter(outputPath + filePrefix + "outString.txt", append);
        }
        return stringWriter;
    }

    public static void flushString() throws IOException {
        if (stringWriter != null) {
            stringWriter.flush();
        }
    }

}

