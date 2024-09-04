package com.qooriq;

import com.qooriq.stats.FloatStats;
import com.qooriq.stats.IntegerStats;
import com.qooriq.stats.StringStats;
import com.qooriq.util.ReaderManager;
import com.qooriq.util.WriterManager;

import java.io.*;
import java.util.*;

public class Main {

    /**
     * Классы для ведения информации про определённые типы данных
     */
    private static IntegerStats intStats;
    private static FloatStats floatStats;
    private static StringStats strStats;

    /**
     * Для записи выходных данных
     */
    private static BufferedWriter integerWriter;
    private static BufferedWriter floatWriter;
    private static BufferedWriter stringWriter;

    /**
     * Для простоты использования
     */
    private static final String INTEGERS = "integers.txt";
    private static final String FLOATS = "floats.txt";
    private static final String STRINGS = "strings.txt";

    /**
     * Префикс названия
     * Путь, куда сохранять выходные файлы
     */
    private static String prefix = "";
    private static String fullPath = "";

    /**
     * Первый флаг для проверки нужна ли статистика по файлам
     * А второй для проверки какого типа флаг, если true - full stats,
     * если false - short stats
     */
    private static boolean isDescriptionFlag = false;
    private static boolean descriptionFlag = false;

    /**
     * Для параметра -а
     */
    private static boolean isNeedAdding = false;
    /**
     * Для проверки, что уже идут входные файлы и после них не будет параметров
     */
    private static boolean isEndOfStr = false;


    /**
     * Для записи строк одной строки, если например есть строка вида:
     * sasdsad 45 asfa
     * Чтобы  sasdsad asfa строки были записаны в выходном файле в одну строку
     */
    private static boolean hasStrings = false;

    public static void main(String[] args) {

        Queue<String> queueOfFiles = new LinkedList<>();

        int i = 0;
        loop1:
        /**
         * Происходит чтение всех параметров запуска
         * Проверка корректности введенных данных
         * На ввод принимаются только файлы, заканичвающиеся на .txt
         **/
        for (; i < args.length; i++) {
            if ((args[i].charAt(0) == '-' && args[i].length() > 1) && !isEndOfStr) {
                switch (args[i]) {
                    case "-s":
                        isDescriptionFlag = true;
                        continue loop1;
                    case "-f":
                        isDescriptionFlag = true;
                        descriptionFlag = true;
                        continue loop1;
                    case "-o":
                        if (i != args.length - 1) {
                            fullPath = args[i + 1];
                            i++;
                            continue loop1;
                        } else {
                            System.out.println("You should add output directory after -o");
                            return;
                        }
                    case "-p":
                        if (i != args.length - 1) {
                            prefix = args[i + 1];
                            i++;
                            continue loop1;
                        } else {
                            System.out.println("You should add output directory after -p");
                            return;
                        }
                    case "-a":
                        isNeedAdding = true;
                        continue loop1;
                }

                System.out.println("""
                        No such param
                        Param can be:
                        -a (for adding in the end of file)
                        -p (prefix for output files)
                        -s or -f (for stats of output, -s for short and -f for full)
                        -o (for output directory)
                        """);
                return;
            } else {
                if (args[i].endsWith(".txt") && args[i].length() > 4) {
                    queueOfFiles.add(args[i]);
                    isEndOfStr = true;
                } else if (isEndOfStr) {
                    System.out.println("wrong input, you can't add params after input files");
                    return;
                } else if (!args[i].startsWith("-")) {
                    System.out.println("params must be beginning with '-'");
                    return;
                } else if (args[i].length() < 2) {
                    System.out.println("params must be at least 2 characters");
                    return;
                }
            }
        }

        /**
         * Проверяется наличие входных файлоы
         */
        if (queueOfFiles.isEmpty()) {
            System.out.println("You didn't specify any input files");
            return;
        }

        /**
         * Чтение файлов данных на ввод, а также проверка на наличие данного файла
         * Все файлы ввода находятся в src/main/resources
         */
        for (String queueOfFile : queueOfFiles) {
            try {
                var URL = Main.class.getClassLoader().getResource(queueOfFile);
                if (URL != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(URL.openStream()));
                    String line;
                    /**
                     * Проверятся наличие строки, а после какого типа элемент - число или строка
                     */
                    while ((line = reader.readLine()) != null) {
                        StringBuilder sb = new StringBuilder();
                        StringTokenizer stringTokenizer = new StringTokenizer(line);
                        while (stringTokenizer.hasMoreTokens()) {
                            String token = stringTokenizer.nextToken();
                            if (isInteger(token)) {
                                if (integerWriter == null) {
                                    integerWriter = WriterManager.openWriter(fullPath, prefix, INTEGERS, isNeedAdding);
                                }
                                if (!isNeedAdding){
                                    if (intStats == null) {
                                        intStats = new IntegerStats();
                                    }
                                    int num = Integer.parseInt(token);
                                    intStats.chngAvg(num);
                                    intStats.chckAll(num);
                                }
                                integerWriter.append(token).append("\n");
                            } else if (isFloat(line)) {
                                if (floatWriter == null) {
                                    floatWriter = WriterManager.openWriter(fullPath, prefix, FLOATS, isNeedAdding);
                                }
                                if (!isNeedAdding){
                                    if (floatStats == null) {
                                        floatStats = new FloatStats();
                                    }
                                    double num = Double.parseDouble(token);
                                    floatStats.chngAvg(num);
                                    floatStats.chckAll(num);
                                }
                                floatWriter.append(token).append("\n");
                            } else {
                                if (stringWriter == null) {
                                    stringWriter = WriterManager.openWriter(fullPath, prefix, STRINGS, isNeedAdding);
                                }
                                if (!isNeedAdding){
                                    if (strStats == null) {
                                        strStats = new StringStats();
                                    }
                                    strStats.chckAll(token.length());
                                }
                                sb.append(token).append(" ");
                                hasStrings = true;
                            }
                            if (stringWriter != null && hasStrings && !stringTokenizer.hasMoreTokens()) {
                                if (!sb.isEmpty()){
                                    sb.deleteCharAt(sb.length() - 1);
                                    stringWriter.append(sb).append("\n");
                                    if (!isNeedAdding){
                                        strStats.chngCount();
                                    }
                                    hasStrings = false;
                                }
                            }
                        }
                    }

                    reader.close();
                } else {
                    System.out.println("File not found: " + queueOfFile);
                    return;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            if (integerWriter != null) {
                integerWriter.flush();
                integerWriter = null;
            }
            if (floatWriter != null) {
                floatWriter.flush();
                floatWriter = null;
            }
            if (stringWriter != null) {
                stringWriter.flush();
                stringWriter = null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /**
         * Формирование статистики, если добавляем в файл
         */
        try {
            if (isNeedAdding) {
                if (intStats == null) {
                    intStats = new IntegerStats();
                    BufferedReader reader = ReaderManager.openReader(fullPath, prefix, INTEGERS, isNeedAdding);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Integer num = Integer.parseInt(line);
                        intStats.chngAvg(num);
                        intStats.chckAll(num);
                    }
                    reader.close();
                }
                if (floatStats == null) {
                    floatStats = new FloatStats();
                    BufferedReader reader = ReaderManager.openReader(fullPath, prefix, FLOATS, isNeedAdding);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Double num = Double.parseDouble(line);
                        floatStats.chngAvg(num);
                        floatStats.chckAll(num);
                    }
                    reader.close();
                }
                if (strStats == null) {
                    strStats = new StringStats();
                    BufferedReader reader = ReaderManager.openReader(fullPath, prefix, STRINGS, isNeedAdding);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        strStats.chckAll(line.length());
                        strStats.chngCount();
                    }
                    reader.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        /**
         * Вывод статистики, если нужно
         */
        if (isDescriptionFlag) {
            if (!descriptionFlag) {
                if (intStats != null) {
                    System.out.println(prefix + INTEGERS + " " + intStats.shortStats());
                }
                if (floatStats != null) {
                    System.out.println(prefix + FLOATS + " " + floatStats.shortStats());
                }
                if (strStats != null) {
                    System.out.println(prefix + STRINGS + " " + strStats.shortStats());
                }
            } else {
                if (intStats != null) {
                    System.out.println(prefix + INTEGERS + " " + intStats.fullStats());
                }
                if (floatStats != null) {
                    System.out.println(prefix + FLOATS + " " + floatStats.fullStats());
                }
                if (strStats != null) {
                    System.out.println(prefix + STRINGS + " " + strStats.fullStats());
                }
            }
        }
    }


    private static boolean isInteger(String line) {
        try {
            Integer.parseInt(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isFloat(String line) {
        try {
            Double.parseDouble(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
