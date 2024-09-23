package com.filtr.utility.ArgsExecutive;


import java.util.ArrayList;
import java.util.List;

/**
 * Обработка аргументов, аналитика
 */
public class ArgsExecutive {

    private final StringBuilder outputPath = new StringBuilder("");
    private final StringBuilder filePrefix = new StringBuilder("");

    private boolean appendMode = false;
    private boolean fullStatistic = false;
    private boolean haveStatistic = false;

    private final List<String> files = new ArrayList<>();

    /**
     * Путь файлов.
     */
    public List<String> getFiles() {
        return files;
    }

    /**
     * Выход
     */
    public String getOutputPath() {
        return outputPath.toString();
    }

    /**
     * Префикс
     */
    public String getFilePrefix() {
        return filePrefix.toString();
    }

    /**
     * Запись.
     */
    public boolean isAppendMode() {
        return appendMode;
    }

    /**
     * Статистика
     */
    public boolean isFullStatistic() {
        return fullStatistic;
    }

    /**
     * Отображение статистики
     */
    public boolean isHaveStatistic() {
        return haveStatistic;
    }

    /**
     * Запрос аргументов
     */
    public void parseProgramArguments(String[] options){
        for (int i = 0; i < options.length; i++){
            if ("-o".equals(options[i])) {
                outputPath.append(options[i + 1]);
            } else if ("-p".equals(options[i])) {
                filePrefix.append(options[i + 1]);
            } else if ("-a".equals(options[i])) {
                appendMode = true;
            } else if ("-s".equals(options[i])) {
                haveStatistic = true;
                fullStatistic = false;
            } else if ("-f".equals(options[i])) {
                haveStatistic = true;
                fullStatistic = true;
            } else if (options[i].endsWith(".txt")) {
                files.add(options[i]);
            } else if (options[i-1].equals("-p")  || options[i-1].equals("-o")) {
                continue;
            } else {
                System.out.println();
                System.out.println("  Error! The program don't have this option: " + options[i]);
                System.out.println(" Please enter the correct options ");
                System.exit(1);
            }
        }
    }
}
