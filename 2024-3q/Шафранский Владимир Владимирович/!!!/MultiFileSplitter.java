import java.io.*;
import java.nio.file.*;
import java.util.regex.Pattern;
import java.util.*;

public class MultiFileSplitter {
    public static void main(String[] args) {
        String outputPath = "output";
        String prefix = "";
        boolean appendMode = false;
        boolean fullStats = false;

        // Парсинг аргументов командной строки
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length) outputPath = args[++i];
                    break;
                case "-p":
                    if (i + 1 < args.length) prefix = args[++i];
                    break;
                case "-a":
                    appendMode = true;
                    break;
                case "-s":
                    fullStats = false;
                    break;
                case "-f":
                    fullStats = true;
                    break;
                default:
                    if (args[i].startsWith("-")) {
                        System.out.println("Неизвестный параметр: " + args[i]);
                        return;
                    }
            }
        }

        if (args.length == 0 || args[args.length - 1].startsWith("-")) {
            System.out.println("Использование: java MultiFileSplitter [-o <output_path>] [-p <prefix>] [-a] [-s|-f] <file1.txt> <file2.txt> ...");
            return;
        }

        // Параметры выходных файлов
        Path stringsFile = Path.of(outputPath, prefix + "strings.txt");
        Path integersFile = Path.of(outputPath, prefix + "integers.txt");
        Path decimalsFile = Path.of(outputPath, prefix + "decimals.txt");

        // Ensure output directory exists
        try {
            Files.createDirectories(stringsFile.getParent());
        } catch (IOException e) {
            System.out.println("Ошибка при создании директории для выходных файлов.");
            e.printStackTrace();
            return;
        }

        // Статистика
        int stringCount = 0, integerCount = 0, decimalCount = 0;
        int minLength = Integer.MAX_VALUE, maxLength = 0;
        int minInt = Integer.MAX_VALUE, maxInt = Integer.MIN_VALUE, sumInt = 0;
        double minDec = Double.MAX_VALUE, maxDec = Double.MIN_VALUE, sumDec = 0.0;

        try (
            BufferedWriter stringsWriter = appendMode ? Files.newBufferedWriter(stringsFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND) : Files.newBufferedWriter(stringsFile);
            BufferedWriter integersWriter = appendMode ? Files.newBufferedWriter(integersFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND) : Files.newBufferedWriter(integersFile);
            BufferedWriter decimalsWriter = appendMode ? Files.newBufferedWriter(decimalsFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND) : Files.newBufferedWriter(decimalsFile)
        ) {
            Pattern intPattern = Pattern.compile("^-?\\d+$");
            Pattern decimalPattern = Pattern.compile("^-?\\d*\\.\\d+$");

            boolean hasStrings = false, hasIntegers = false, hasDecimals = false;

            for (String inputFile : args) {
                if (inputFile.equals("-o") || inputFile.equals("-p") || inputFile.equals("-a") || inputFile.equals("-s") || inputFile.equals("-f")) continue;
                if (inputFile.startsWith("-")) continue;

                Path inputPath = Path.of(inputFile);

                // Check if the file exists
                if (!Files.exists(inputPath)) {
                    System.out.println("Файл не найден: " + inputFile);
                    continue;
                }

                try (BufferedReader br = Files.newBufferedReader(inputPath)) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (intPattern.matcher(line).matches()) {
                            integersWriter.write(line);
                            integersWriter.newLine();
                            integerCount++;
                            hasIntegers = true;
                            int value = Integer.parseInt(line);
                            minInt = Math.min(minInt, value);
                            maxInt = Math.max(maxInt, value);
                            sumInt += value;
                        } else if (decimalPattern.matcher(line).matches()) {
                            decimalsWriter.write(line);
                            decimalsWriter.newLine();
                            decimalCount++;
                            hasDecimals = true;
                            double value = Double.parseDouble(line);
                            minDec = Math.min(minDec, value);
                            maxDec = Math.max(maxDec, value);
                            sumDec += value;
                        } else {
                            stringsWriter.write(line);
                            stringsWriter.newLine();
                            stringCount++;
                            hasStrings = true;
                            minLength = Math.min(minLength, line.length());
                            maxLength = Math.max(maxLength, line.length());
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Ошибка при чтении файла: " + inputFile);
                    e.printStackTrace();
                }
            }

            System.out.println("Файлы успешно созданы:");
            if (hasStrings) {
                System.out.println("Строки: " + stringsFile.toAbsolutePath());
            }
            if (hasIntegers) {
                System.out.println("Целые числа: " + integersFile.toAbsolutePath());
            }
            if (hasDecimals) {
                System.out.println("Дробные числа: " + decimalsFile.toAbsolutePath());
            }

            // Вывод статистики
            System.out.println("Статистика:");
            System.out.println("Строки: " + stringCount);
            if (fullStats && stringCount > 0) {
                System.out.println("Самая короткая строка: " + minLength);
                System.out.println("Самая длинная строка: " + maxLength);
            }

            System.out.println("Целые числа: " + integerCount);
            if (fullStats && integerCount > 0) {
                System.out.println("Минимальное значение: " + minInt);
                System.out.println("Максимальное значение: " + maxInt);
                System.out.println("Сумма: " + sumInt);
                System.out.println("Среднее: " + (double) sumInt / integerCount);
            }

            System.out.println("Дробные числа: " + decimalCount);
            if (fullStats && decimalCount > 0) {
                System.out.println("Минимальное значение: " + minDec);
                System.out.println("Максимальное значение: " + maxDec);
                System.out.println("Сумма: " + sumDec);
                System.out.println("Среднее: " + sumDec / decimalCount);
            }

        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл.");
            e.printStackTrace();
        }
    }
}
