package com.shift_by;

import java.util.ArrayList;
import java.util.List;

public class Main {

    //    Пример запуска утилиты
//    java -jar target\shift_by-1.0-SNAPSHOT.jar -s -a -p sample- in1.txt in2.txt
//    java -jar target\shift_by-1.0-SNAPSHOT.jar -s -a -p result- in1.txt in2.txt
//    java -jar target\shift_by-1.0-SNAPSHOT.jar -f -a -p result- in1.txt in2.txt
//    java -jar target\shift_by-1.0-SNAPSHOT.jar -s -a -o F:\JAVA\shift_by\some -p result- in1.txt in2.txt
//    java -jar target\shift_by-1.0-SNAPSHOT.jar -s -a -p sample- in1.txt in2.txt in3.txt
//    java -jar target\shift_by-1.0-SNAPSHOT.jar -s -a -p sample- in3.txt
//    java -jar target\shift_by-1.0-SNAPSHOT.jar -s -p sample- in3.txt
//    java -jar target\shift_by-1.0-SNAPSHOT.jar -p sample- in4.txt
    public static void main(String[] args) {
        // Проверка наличия входных файлов
        if (args.length == 0) {
            System.out.println("Вы должны указать хотя бы один файл для обработки.");
            return;
        }

        // Инициализация параметров по умолчанию
        String outputPath = "."; // Корень проекта
        String prefix = ""; // Префикс для имен файлов
        boolean append = false; // Перезапись выходных файлов по умолчанию
        boolean shortStats = false; // Вывод краткой статистики по умолчанию
        boolean fullStats = false; // Вывод полной статистики по умолчанию

        List<String> inputFiles = new ArrayList<>();

        // Обработка аргументов командной строки
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    outputPath = args[++i]; // Путь для выходных файлов -o F:\JAVA\shift_by\some
                    break;
                case "-p":
                    prefix = args[++i]; // Префикс для имен выходных файлов
                    break;
                case "-a":
                    append = true; // Установка флага добавления
                    break;
                case "-s":
                    shortStats = true; // Установка флага краткой статистики
                    break;
                case "-f":
                    fullStats = true; // Установка флага полной статистики
                    break;
                default:
                    inputFiles.add(args[i]); // Добавление входного файла
                    break;
            }
        }

        // Обработка файлов
        DataFilter.processFiles(inputFiles, outputPath, prefix, append, shortStats, fullStats);
    }
}
