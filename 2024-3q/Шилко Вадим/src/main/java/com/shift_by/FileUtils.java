package com.shift_by;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtils {
    // Метод для записи целых чисел в файл
    public static void writeIntegerData(List<Integer> integers, String outputPathWithFile, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPathWithFile, append))) {
            for (Integer number : integers) {
                writer.write(number.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + outputPathWithFile + " - " + e.getMessage());
        }
    }

    // Метод для записи дробных чисел в файл
    public static void writeDoubleData(List<Double> floats, String outputPathWithFile, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPathWithFile, append))) {
            for (Double number : floats) {
                writer.write(number.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + outputPathWithFile + " - " + e.getMessage());
        }
    }

    // Метод для записи строк в файл
    public static void writeStringData(List<String> strings, String outputPathWithFile, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPathWithFile, append))) {
            for (String str : strings) {
                writer.write(str);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + outputPathWithFile + " - " + e.getMessage());
        }
    }
}
