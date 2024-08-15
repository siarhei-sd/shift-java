package by.pashko.filter.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;

import static by.pashko.filter.utility.Constants.VALUE_TYPE_TO_FILE_NAME;
import static by.pashko.filter.utility.ValueType.DOUBLE;
import static by.pashko.filter.utility.ValueType.INTEGER;
import static by.pashko.filter.utility.ValueType.STRING;

public class Runner implements Runnable {

    private final ApplicationProperties applicationProperties;

    public Runner(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void run() {
        List<String> inputFiles = applicationProperties.getInputFiles();

        if (inputFiles == null || inputFiles.isEmpty()) {
            System.err.println("Input files is empty. Specify the path to the files.");
            return;
        }

        System.out.printf("Starting process with following arguments: %s\n", applicationProperties);

        Map<ValueType, List<String>> map = parseFiles(inputFiles);

        saveElements(map);
    }

    /**
     * Парсит и фильтрует данные из файлов по типу.
     *
     * @param files Список файлов, которые нужно парсить.
     * @return Словарь значений отфильтрованных по типу.
     */
    private Map<ValueType, List<String>> parseFiles(List<String> files) {
        Map<ValueType, List<String>> map = new HashMap<>();

        for (String file : files) {
            System.out.printf("Starting parse file: %s\n", file);

            try {
                for (String line : Files.readAllLines(Path.of(file), StandardCharsets.UTF_8)) {
                    String[] tokens = line.split("\\s+");
                    StringBuilder stringBuilder = new StringBuilder();

                    for (String token : tokens) {
                        if (isDouble(token)) {
                            map.computeIfAbsent(DOUBLE, value -> new ArrayList<>())
                                    .add(token);
                        } else if (isInteger(token)) {
                            map.computeIfAbsent(INTEGER, value -> new ArrayList<>())
                                    .add(token);
                        } else {
                            stringBuilder.append(token)
                                    .append(" ");
                        }
                    }

                    if (stringBuilder.length() != 0) {
                        map.computeIfAbsent(STRING, value -> new ArrayList<>())
                                .add(stringBuilder.toString().trim());
                    }
                }
            } catch (IOException e) {
                System.err.printf("Failed to parse file: %s\n", file);
            }
        }

        return map;
    }

    /**
     * Проверяет является ли входная строка целым числом.
     *
     * @return true - if integer иначе false.
     */
    private boolean isInteger(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            // do nothing
        }

        return false;
    }

    /**
     * Проверяет является ли входная строка числом c плавающей точкой.
     *
     * @return true - if float иначе false.
     */
    private boolean isDouble(String token) {
        try {
            if (token.contains(".")) {
                Double.parseDouble(token);
                return true;
            }
        } catch (NumberFormatException e) {
            // do nothing
        }

        return false;
    }

    private void showShortStatistics(String fileName, List<String> elements) {
        if (applicationProperties.isShowShortStatistics()) {
            System.out.printf("%s short statistics: elements = %s.\n", fileName, elements.size());
        }
    }

    private void showFullStatistics(String fileName, ValueType valueType, List<String> elements) {
        if (applicationProperties.isShowFullStatistics()) {
            if (STRING.equals(valueType)) {
                LongSummaryStatistics summaryStatistics = new LongSummaryStatistics();

                for (String element : elements) {
                    summaryStatistics.accept(element.toCharArray().length);
                }

                System.out.printf(
                        "%s full statistics: elements %s; min = %s, max = %s.\n",
                        fileName,
                        elements.size(),
                        summaryStatistics.getMin(),
                        summaryStatistics.getMax()
                );
            } else {
                DoubleSummaryStatistics summaryStatistics = new DoubleSummaryStatistics();

                for (String element : elements) {
                    summaryStatistics.accept(Double.parseDouble(element));
                }

                System.out.printf(
                        "%s full statistics: elements %s; min = %s, max = %s, sum = %s, average = %s.\n",
                        fileName,
                        elements.size(),
                        summaryStatistics.getMin(),
                        summaryStatistics.getMax(),
                        summaryStatistics.getSum(),
                        summaryStatistics.getAverage()
                );
            }
        }
    }

    /**
     * Сохраняет данные в файл.
     */
    private void saveElements(Map<ValueType, List<String>> map) {
        for (Map.Entry<ValueType, List<String>> entry : map.entrySet()) {
            ValueType valueType = entry.getKey();
            List<String> elements = entry.getValue();

            if (elements.isEmpty()) {
                continue;
            }

            String fileName = buildFileName(valueType);

            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(fileName, applicationProperties.isAppendToEnd()))) {

                for (String value : elements) {
                    writer.append(value);
                    writer.newLine();
                }

                System.out.printf("Elements %s was saved to file %s.\n", elements, fileName);
            } catch (IOException e) {
                System.err.printf("Failed to write to file %s.\n", fileName);
            }

            showShortStatistics(fileName, elements);
            showFullStatistics(fileName, valueType, elements);
        }
    }

    private String buildFileName(ValueType valueType) {
        String fileName = applicationProperties.getFileNamePrefix() + VALUE_TYPE_TO_FILE_NAME.get(valueType);
        String outputDirectory = applicationProperties.getOutputDirectory();

        if (outputDirectory != null && !outputDirectory.isBlank()) {
            File directory = new File(outputDirectory);

            if (directory.exists() || directory.mkdirs()) {
                return directory + File.separator + fileName;
            } else {
                System.err.printf("Failed to create directory %s. Default will be used.\n", directory);
            }
        }

        return fileName;
    }
}
