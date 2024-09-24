
package by.konashuk.analyzer.file;

import by.konashuk.analyzer.parameter.AnalysisParameters;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;

import static by.konashuk.analyzer.file.constants.FileConstants.Files.FLOAT_FILE_NAME_PATTERN;
import static by.konashuk.analyzer.file.constants.FileConstants.Files.INTEGER_FILE_NAME_PATTERN;
import static by.konashuk.analyzer.file.constants.FileConstants.Files.STRING_FILE_NAME_PATTERN;
import static by.konashuk.analyzer.file.constants.FileConstants.Patterns.FLOAT_PATTERN;
import static by.konashuk.analyzer.file.constants.FileConstants.Patterns.INTEGER_PATTERN;
import static by.konashuk.analyzer.file.constants.FileConstants.Patterns.STRINGS_PATTERN;


public final class FileAnalyzer {
    private FileAnalyzer() {
    }

    public static void analyzeFiles(AnalysisParameters analysisParameters) {
        processFiles(analysisParameters);
    }

    private static void processFiles(AnalysisParameters analysisParameters) {
        String integerFilePath = INTEGER_FILE_NAME_PATTERN.formatted(analysisParameters.getPath(), analysisParameters.getPrefix());
        String floatFilePath = FLOAT_FILE_NAME_PATTERN.formatted(analysisParameters.getPath(), analysisParameters.getPrefix());
        String stringFilePath = STRING_FILE_NAME_PATTERN.formatted(analysisParameters.getPath(), analysisParameters.getPrefix());

        try (FileOutputStream intFileOutputStream = createFileOutputStream(integerFilePath, analysisParameters.getIsAppend());
             FileOutputStream floatFileOutputStream = createFileOutputStream(floatFilePath, analysisParameters.getIsAppend());
             FileOutputStream stringFileOutputStream = createFileOutputStream(stringFilePath, analysisParameters.getIsAppend())) {

            parseFiles(analysisParameters.getFileNames(), intFileOutputStream, floatFileOutputStream, stringFileOutputStream);

            if (analysisParameters.getShortStatistic()) {
                calculateFilesStatistic(floatFilePath, integerFilePath, stringFilePath, false);
            }
            if (analysisParameters.getFullStatistic()) {
                calculateFilesStatistic(floatFilePath, integerFilePath, stringFilePath, true);
            }
        } catch (IOException exp) {
            throw new RuntimeException("Failed to read file(s) ", exp);
        }

        deleteEmptyFile(integerFilePath);
        deleteEmptyFile(floatFilePath);
        deleteEmptyFile(stringFilePath);
    }

    private static void parseFiles(Set<String> fileNames,
                                   FileOutputStream intFileStream,
                                   FileOutputStream floatFileStream,
                                   FileOutputStream stringFileStream
    ) throws IOException {
        for (String fileName : fileNames) {
            try (BufferedReader br = createBufferedReader(fileName)) {
                String fileLine;

                while ((fileLine = br.readLine()) != null) {

                    Matcher integerMatcher = INTEGER_PATTERN.matcher(fileLine);
                    if (integerMatcher.find()) {
                        writeToFile(intFileStream, integerMatcher.group() + "\n", fileName);
                        continue;
                    }

                    Matcher floatMatcher = FLOAT_PATTERN.matcher(fileLine);
                    if (floatMatcher.find()) {
                        writeToFile(floatFileStream, floatMatcher.group() + "\n", fileName);
                        continue;
                    }

                    Matcher wordsMatcher = STRINGS_PATTERN.matcher(fileLine);
                    if (wordsMatcher.find()) {
                        writeToFile(stringFileStream, fileLine + "\n", fileName);
                        continue;
                    }

                    throw new IllegalArgumentException("File " + fileName + " contains not supported data type.");
                }
            } catch (IOException exp) {
                throw new IOException("Error reading file : " + fileName);
            }
        }
    }

    private static void writeToFile(
            FileOutputStream outputStream,
            String stringToWrite,
            String fileName) {
        try {
            outputStream.write(stringToWrite.getBytes(StandardCharsets.UTF_8));
        } catch (IOException ex) {
            throw new RuntimeException("Can not write data to " + fileName, ex);
        }
    }

    private static void deleteEmptyFile(String filePath) {
        File fileToDelete = new File(filePath);

        try {
            if (fileToDelete.length() == 0) {
                fileToDelete.delete();
            }
        } catch (Exception exp) {
            throw new RuntimeException("Can not delete the file " + filePath);
        }
    }

    private static void calculateFilesStatistic(
            String floatFilePath,
            String integerFilePath,
            String stringsFilePath,
            Boolean isFullStatistic
    ) {
        calculateIntStatistic(integerFilePath, isFullStatistic);
        calculateDoubleStatistic(floatFilePath, isFullStatistic);
        calculateStringsStatistic(stringsFilePath, isFullStatistic);
    }

    private static void calculateIntStatistic(String integerFileName, Boolean isFullStatistic) {
        try (BufferedReader intBR = createBufferedReader(integerFileName)) {
            String line;
            String intStatisticInfo;
            List<Integer> intList = new ArrayList<>();

            while ((line = intBR.readLine()) != null) {
                intList.add(Integer.parseInt(line));
            }

            if (intList.isEmpty()) {
                intStatisticInfo = integerFileName + " does not have data to handle";
            } else {
                IntSummaryStatistics intSummaryStatistics = intList.stream()
                        .mapToInt(e -> e)
                        .summaryStatistics();

                intStatisticInfo = isFullStatistic ? integerFileName + " full statistic: " +
                        "elements = " + intSummaryStatistics.getCount() + "; " +
                        "min = " + intSummaryStatistics.getMin() + "; " +
                        "max = " + intSummaryStatistics.getMax() + "; " +
                        "sum = " + intSummaryStatistics.getSum() + "; " +
                        "average = " + intSummaryStatistics.getAverage() :
                        integerFileName + " short statistic: elements = " + intSummaryStatistics.getCount();
            }

            System.out.println(intStatisticInfo);

        } catch (IOException exp) {
            throw new RuntimeException("Statistics can not be calculated.", exp);
        }
    }

    private static void calculateDoubleStatistic(String doubleFileName, Boolean isFullStatistic) {
        try (BufferedReader floatBR = createBufferedReader(doubleFileName)) {
            String line;
            String doubleStatisticInfo;
            List<Double> doubleList = new ArrayList<>();

            while ((line = floatBR.readLine()) != null) {
                doubleList.add(Double.parseDouble(line));
            }

            if (doubleList.isEmpty()) {
                doubleStatisticInfo = doubleFileName + " does not have data to handle";
            } else {
                DoubleSummaryStatistics floatSummaryStatistics = doubleList.stream()
                        .mapToDouble(e -> e)
                        .summaryStatistics();

                doubleStatisticInfo = isFullStatistic ? doubleFileName + " full statistic: " +
                        "elements = " + floatSummaryStatistics.getCount() + "; " +
                        "min = " + floatSummaryStatistics.getMin() + "; " +
                        "max = " + floatSummaryStatistics.getMax() + "; " +
                        "sum = " + floatSummaryStatistics.getSum() + "; " +
                        "average = " + floatSummaryStatistics.getAverage() :
                        doubleFileName + " short statistic: elements = " + floatSummaryStatistics.getCount();
            }

            System.out.println(doubleStatisticInfo);

        } catch (IOException exp) {
            throw new RuntimeException("Statistics can not be calculated.", exp);
        }
    }

    private static void calculateStringsStatistic(String stringsFileName, Boolean isFullStatistic) {
        try (BufferedReader stringBR = createBufferedReader(stringsFileName)) {
            String line;
            String stringsStatisticInfo;
            List<String> strList = new ArrayList<>();

            while ((line = stringBR.readLine()) != null) {
                strList.add(line);
            }

            if (strList.isEmpty()) {
                stringsStatisticInfo = stringsFileName + " does not have data to handle";
            } else {
                IntSummaryStatistics stringSummaryStatistics = strList.stream()
                        .mapToInt(String::length)
                        .summaryStatistics();

                stringsStatisticInfo = isFullStatistic ? stringsFileName + " full statistic: " +
                        "elements = " + stringSummaryStatistics.getCount() + "; " +
                        "min string length = " + stringSummaryStatistics.getMin() + "; " +
                        "max string length = " + stringSummaryStatistics.getMax() :
                        stringsFileName + " short statistic: elements = " + stringSummaryStatistics.getCount();
            }

            System.out.println(stringsStatisticInfo);

        } catch (IOException exp) {
            throw new RuntimeException("Statistics can not be calculated.", exp);
        }
    }

    private static BufferedReader createBufferedReader(String fileName) throws IOException {
        return new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8));
    }

    private static FileOutputStream createFileOutputStream(String fileName, Boolean isAppend) throws IOException {
        return new FileOutputStream(fileName, isAppend);
    }
}