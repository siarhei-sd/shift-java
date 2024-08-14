package com.filterutil;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OptionsParser {
    private String outputPath = "."; // текущая дериктория
    private String prefix = "";
    private boolean append = false; // булевый флаг, показывающий, нужно ли добавлять (вместо перезаписи) данные в выходной файл.
    private boolean shortStat = false;
    private boolean fullStat = false;
    private List<String> inputFiles = new ArrayList<>();

    public OptionsParser(String[] args) {
        parseOptions(args);
        // Конструктор который принимает массив аргументов из метода Main.
    }

    private void parseOptions(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o": // Если аргумент равен `-o`, то следующий элемент в массиве (это ожидаемый путь для `outputPath`) сохраняется.
                    outputPath = args[++i];
                    break;
                case "-p": // Если аргумент равен `-p`, то следующий элемент сохраняется как `prefix`.
                    prefix = args[++i];
                    break;
                case "-a": // Если аргумент равен `-a`, устанавливается флаг `append` в `true`.
                    append = true;
                    break;
                case "-s":
                    shortStat = true;
                    break;
                case "-f":
                    fullStat = true;
                    break;
                default:
                    inputFiles.add(args[i]);
                    break;
            }
        }
    }
}
