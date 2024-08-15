package com.shift_by;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataFilter {

    public static void processFiles(List<String> inputFiles, String outputPath, String prefix, boolean append, boolean shortStats, boolean fullStats) {
        List<Integer> integers = new ArrayList<>();
        List<Double> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        // Обработка входных файлов
        for (String fileName : inputFiles) {
            try (InputStream is = DataFilter.class.getClassLoader().getResourceAsStream(fileName);
                 BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {
                String line;
                while ((line = br.readLine()) != null) {
                    parseLine(line, integers, floats, strings);
                }
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла " + fileName + ": " + e.getMessage());
            } catch (NullPointerException e) {
                System.err.println("Файл " + fileName + " не найден.");
            }
        }

        // Проверяем и создаем необходимые директории для записи результатов
        createDirectoryIfNotExists(outputPath);

        // Обработка флага статистики
        if (!append || !shortStats) {
            // Вывод сообщения об ошибке
            System.err.println("Ошибка: Аргумент статистики не должен быть пустым. " +
                    "Пожалуйста, укажите аргумент статистики: -s (краткая) либо -f (полная).");
            return; // Выход из метода, если аргумент статистики некорректный
        }

        // Запись данных в выходные файлы с учетом префикса
        if (!integers.isEmpty()) {
            FileUtils.writeIntegerData(integers, outputPath + "/" + prefix + "integers.txt", append);
        }

        if (!floats.isEmpty()) {
            FileUtils.writeDoubleData(floats, outputPath + "/" + prefix + "floats.txt", append);
        }

        if (!strings.isEmpty()) {
            FileUtils.writeStringData(strings, outputPath + "/" + prefix + "strings.txt", append);
        }

        // Вывод статистики
        if (shortStats) {
            Statistics.printShortStats(prefix, integers, floats, strings);
        }

        if (fullStats) {
            Statistics.printFullStats(prefix, integers, floats, strings);
        }
    }

    // Метод для парсинга строки
    private static void parseLine(String line, List<Integer> integers, List<Double> floats, List<String> strings) {
        line = line.trim(); // Убираем лишние пробелы
        if (line.isEmpty()) {
            return; // Игнорируем пустые строки
        }

        try {
            // Попытка распарсить строку как целое или дробное число
            if (line.matches("-?\\d+")) {
                integers.add(Integer.parseInt(line));
            } else {
                String floatValue = line.replace(',', '.'); // Заменяем запятую на точку
                floats.add(Double.parseDouble(floatValue));
            }
        } catch (NumberFormatException e) {
            strings.add(line); // Если не удается распарсить, добавляем в строку
        }
    }

    // Метод для проверки существования директории и создания ее при необходимости
    private static void createDirectoryIfNotExists(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Каталог " + directory.getAbsolutePath() + " был создан.");
            } else {
                System.err.println("Не удалось создать каталог " + directory.getAbsolutePath());
            }
        }
    }
}