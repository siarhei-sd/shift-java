package org.example;
import java.io.*;
import java.util.ArrayList;

public class Util
{
    private static final String RESULT_PATH = "-o";
    private static final String PREFIX = "-p";
    private static final String REWRITE = "-a";
    private static final String SHORT_STAT = "-s";
    private static final String FULL_STAT = "-f";
    private static final String FILE = ".txt";

    private static final String INT_FILE = "integers.txt";
    private static final String FLOAT_FILE = "floats.txt";
    private static final String STRING_FILE = "strings.txt";

    String outIntFile = INT_FILE;
    String outFloatFile = FLOAT_FILE;
    String outStringFile = STRING_FILE;

    ArrayList<Integer> arrayListInt = new ArrayList<>();
    ArrayList<Float> arrayListFloat = new ArrayList<>();
    ArrayList<String> arrayListString = new ArrayList<>();

    boolean rewrite = false;
    boolean fullStat = false;

    public static void main( String[] args )
    {
        Util util = new Util();
        IntData intData = new IntData();
        FloatData floatData = new FloatData();
        StringData stringData = new StringData();

        for (int i = 0; i < args.length; i++)
        {
            String key = args[i];
            String inFile = "";
            if (key.contains(FILE)) {
                inFile = key;
                key = ".txt";
            }
            switch (key) {
                // добавление пути сохранения в начало имён файлов
                case RESULT_PATH:
                    String path = args[i + 1];

                    util.outIntFile = util.AddPath(util.outIntFile, path);
                    util.outFloatFile = util.AddPath(util.outFloatFile, path);
                    util.outStringFile = util.AddPath(util.outStringFile, path);

                    i++;
                    break;
                // добавление префикса к именам файлов
                case PREFIX:
                    String prefix = args[i + 1];

                    util.outIntFile = util.AddPrefix(util.outIntFile, prefix, INT_FILE);
                    util.outFloatFile = util.AddPrefix(util.outFloatFile, prefix, FLOAT_FILE);
                    util.outStringFile = util.AddPrefix(util.outStringFile, prefix, STRING_FILE);

                    i++;
                    break;
                // изменение значения параметра для дозаписи файлов
                case REWRITE:
                    util.rewrite = true;
                    break;
                // изменение значения параметра для определения типа статистики (краткая)
                case SHORT_STAT:
                    util.fullStat = false;
                    break;
                // изменение значения параметра для определения типа статистики (полная)
                case FULL_STAT:
                    util.fullStat = true;
                    break;
                // считывание строк из файла и добавление их в массив по типу
                case FILE:
                    util.ReadFromFile(inFile);
                    break;
                default:
                    System.out.println("Введён неизвестный аргумент: " + "'" + key + "'");
                    break;
            }
        }
        // проверка наличия в массиве данных, сбор статистики и запись в файл
        intData.WriteIntoFile(util.arrayListInt, util.outIntFile, util.rewrite);
        floatData.WriteIntoFile(util.arrayListFloat, util.outFloatFile, util.rewrite);
        stringData.WriteIntoFile(util.arrayListString, util.outStringFile, util.rewrite);

        // вывод статистики
        intData.PrintStatistic(util.fullStat);
        floatData.PrintStatistic(util.fullStat);
        stringData.PrintStatistic(util.fullStat);
    }

    // метод добавления пути сохранения к имени файла
    String AddPath(String outFile, String path)
    {
        outFile = path + outFile;
        return outFile;
    }
    // метод добавления префикса к имени файла
    String AddPrefix(String outFile, String prefix, String firstFileName)
    {
        int index;
        index = outFile.indexOf(firstFileName);
        outFile = outFile.substring(0, index) + prefix + outFile.substring(index);
        return outFile;
    }
    // метод чтения из файла
    void ReadFromFile(String inFile)
    {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inFile))) {
            System.out.print("Считывание файла: " + inFile + "\n");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (CheckIsItInt(line)) {
                    arrayListInt.add(Integer.parseInt(line));
                } else if (CheckIsItFloat(line)) {
                    arrayListFloat.add(Float.parseFloat(line));
                } else {
                    arrayListString.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    // метод проверки, можно ли привести содержимое строки к INT
    static boolean CheckIsItInt(String x){
        try
        {
            Integer.parseInt(x);
        }
        catch (Throwable e)
        {
            return false;
        }
        return true;
    }
    // метод проверки, можно ли привести содержимое строки к FLOAT
    static boolean CheckIsItFloat(String x){
        try
        {
            Float.parseFloat(x);
        }
        catch (Throwable e)
        {
            return false;
        }
        return true;
    }
}
