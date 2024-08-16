package by.shift.matveenko.service;

import by.shift.matveenko.data.DataStatistics;
import by.shift.matveenko.data.DataTypes;
import by.shift.matveenko.data.StatisticsTypes;
import by.shift.matveenko.service.statistics.DoubleStatisticsService;
import by.shift.matveenko.service.statistics.IntegerStatisticsService;
import by.shift.matveenko.service.statistics.StringStatisticsService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FileConsumerService {
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^[-+]?\\d+$");
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("^[-+]?[0-9]*[.,][0-9]+(?:[eE][-+]?[0-9]+)?$");
    private final Map<DataTypes, FileMakerService> fileMakers = new HashMap<>();
    private final Map<DataTypes, DataStatistics> dataStatistics = new HashMap<>();

    public FileConsumerService(StatisticsTypes statisticsTypes, String path, String prefix, boolean addedResults) {
        dataStatistics.put(DataTypes.INTEGER, new IntegerStatisticsService(statisticsTypes));
        dataStatistics.put(DataTypes.DOUBLE, new DoubleStatisticsService(statisticsTypes));
        dataStatistics.put(DataTypes.STRING, new StringStatisticsService(statisticsTypes));
        for (DataTypes dataTypes : dataStatistics.keySet()) {
            fileMakers.put(dataTypes, new FileMakerService(dataTypes.toString() + ".txt", path, prefix, addedResults));
        }
    }

    public void readFile(String path) throws Exception {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            String str;
            while ((str = reader.readLine()) != null) {
                DataTypes dataTypes = dataTypeIdentifier(str);
                dataStatistics.get(dataTypes).addData(str);
                fileMakers.get(dataTypes).addData(str);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + path);
        } catch (IOException e) {
            System.err.println("Problem with reading file: " + path);
        }
    }

    public void printStatistics() {
        for (DataStatistics dataStatistics : this.dataStatistics.values()) {
            System.out.println(dataStatistics.toString());
        }
    }

    public void closeFiles() {
        for (FileMakerService fileMakerService : fileMakers.values()) {
            fileMakerService.closeFile();
        }
    }

    public DataTypes dataTypeIdentifier(String str) {
        if (INTEGER_PATTERN.matcher(str).matches()) {
            return DataTypes.INTEGER;
        }
        if (DOUBLE_PATTERN.matcher(str).matches()) {
            return DataTypes.DOUBLE;
        } else {
            return DataTypes.STRING;
        }
    }
}