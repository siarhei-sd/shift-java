package com.prokopovich;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileWriter {
    public static boolean writeToFile(String fileName, List<String> data, boolean appendMode, boolean writeEmptyFile) {

        if (!writeEmptyFile) {
            if (data.isEmpty()) {
                return false;
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, appendMode), StandardCharsets.UTF_8))) {
            for (String line : data) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + fileName);
            e.printStackTrace();
            return false;
        }

        System.out.println("Создан файл: " + fileName);

        return true;
    }
}
