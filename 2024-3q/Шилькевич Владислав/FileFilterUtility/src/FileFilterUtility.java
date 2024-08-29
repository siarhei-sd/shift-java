import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class FileFilterUtility {

    public static void main(String[] args) {
        List<String> files = new ArrayList<>();
        String outputPath = ".";
        String prefix = "";
        boolean append = false;
        boolean shortStats = false;
        boolean fullStats = false;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    outputPath = args[++i];
                    break;
                case "-p":
                    prefix = args[++i];
                    break;
                case "-a":
                    append = true;
                    break;
                case "-s":
                    shortStats = true;
                    break;
                case "-f":
                    fullStats = true;
                    break;
                default:
                    files.add(args[i]);
                    break;
            }
        }

        List<Integer> integers = new ArrayList<>();
        List<Double> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        for (String file : files) {
            System.out.println("Чтение файла: " + file);
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.matches("-?\\d+")) {
                        integers.add(Integer.parseInt(line));
                    } else if (line.matches("-?\\d*\\.\\d+")) {
                        floats.add(Double.parseDouble(line));
                    } else {
                        strings.add(line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Ошибка чтения файла: " + file);
                e.printStackTrace();
            }
        }

        // Создание директории, если она не существует
        Path outputDir = Paths.get(outputPath);
        if (!Files.exists(outputDir)) {
            try {
                Files.createDirectories(outputDir);
            } catch (IOException e) {
                System.err.println("Ошибка создания директории: " + outputPath);
                e.printStackTrace();
                return;
            }
        }

        if (!integers.isEmpty()) {
            System.out.println("Запись целых чисел в файл: " + prefix + "integers.txt");
            writeToFile(outputPath, prefix + "integers.txt", integers, append);
        }
        if (!floats.isEmpty()) {
            System.out.println("Запись вещественных чисел в файл: " + prefix + "floats.txt");
            writeToFile(outputPath, prefix + "floats.txt", floats, append);
        }
        if (!strings.isEmpty()) {
            System.out.println("Запись строк в файл: " + prefix + "strings.txt");
            writeToFile(outputPath, prefix + "strings.txt", strings, append);
        }

        if (shortStats || fullStats) {
            if (!integers.isEmpty()) {
                printStatistics("Целые числа", integers, fullStats);
            }
            if (!floats.isEmpty()) {
                printStatistics("Вещественные числа", floats, fullStats);
            }
            if (!strings.isEmpty()) {
                printStatistics("Строки", strings, fullStats);
            }
        }
    }

    private static <T> void writeToFile(String outputPath, String filename, List<T> data, boolean append) {
        if (data.isEmpty()) return;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get(outputPath, filename).toString(), append))) {
            for (T item : data) {
                writer.write(item.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + filename);
            e.printStackTrace();
        }
    }

    private static void printStatistics(String dataType, List<?> data, boolean fullStats) {
        System.out.println(dataType + ": " + data.size() + " элементов");
        if (fullStats) {
            if (data.get(0) instanceof Number) {
                List<Number> numbers = data.stream().map(Number.class::cast).collect(Collectors.toList());
                double min = numbers.stream().mapToDouble(Number::doubleValue).min().orElse(0);
                double max = numbers.stream().mapToDouble(Number::doubleValue).max().orElse(0);
                double sum = numbers.stream().mapToDouble(Number::doubleValue).sum();
                double avg = sum / numbers.size();
                System.out.printf("Минимум: %.2f, Максимум: %.2f, Сумма: %.2f, Среднее: %.2f%n", min, max, sum, avg);
            } else if (data.get(0) instanceof String) {
                List<String> strings = data.stream().map(String.class::cast).collect(Collectors.toList());
                int minLen = strings.stream().mapToInt(String::length).min().orElse(0);
                int maxLen = strings.stream().mapToInt(String::length).max().orElse(0);
                System.out.printf("Минимальная длина: %d, Максимальная длина: %d%n", minLen, maxLen);
            }
        }
    }
}
