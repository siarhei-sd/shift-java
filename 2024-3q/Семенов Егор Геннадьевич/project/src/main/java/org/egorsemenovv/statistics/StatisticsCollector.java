package org.egorsemenovv.statistics;


import static org.egorsemenovv.statistics.StatisticsType.LONG;
import static org.egorsemenovv.statistics.StatisticsType.SHORT;

public class StatisticsCollector {

    private final NumberStatistics<Long> longStatistics;
    private final NumberStatistics<Double> doublesStatistics;
    private final StringStatistics stringsStatistics;
    private final StatisticsType statisticsType;

    public StatisticsCollector(String integersFilename, String floatsFileName, String stringsFileName, StatisticsType statisticsType) {
        this.statisticsType = statisticsType;
        this.longStatistics = new NumberStatistics<>(integersFilename);
        longStatistics.setSum(0L);
        this.doublesStatistics = new NumberStatistics<>(floatsFileName);
        doublesStatistics.setSum(0.0);
        this.stringsStatistics = new StringStatistics(stringsFileName);
    }

    public void addLong(long integerNumber) {
        int currentNumberOfElements = longStatistics.getNumberOfElements();
        longStatistics.setNumberOfElements(++currentNumberOfElements);
        if (statisticsType == LONG) {
            long sum = longStatistics.getSum();
            sum += integerNumber;
            longStatistics.setSum(sum);
            double average = ((double) sum) / currentNumberOfElements;
            longStatistics.setAverage(average);
            if (longStatistics.getMaxElement() == null || longStatistics.getMaxElement() < integerNumber) {
                longStatistics.setMaxElement(integerNumber);
            }
            if (longStatistics.getMinElement() == null || longStatistics.getMinElement() > integerNumber) {
                longStatistics.setMinElement(integerNumber);
            }
        }
    }

    public void addDouble(double doubleNumber) {
        int currentNumberOfElements = doublesStatistics.getNumberOfElements();
        doublesStatistics.setNumberOfElements(++currentNumberOfElements);
        if (statisticsType == LONG) {
            double sum = doublesStatistics.getSum();
            sum += doubleNumber;
            doublesStatistics.setSum(sum);
            double average = sum / currentNumberOfElements;
            doublesStatistics.setAverage(average);
            if (doublesStatistics.getMaxElement() == null || doublesStatistics.getMaxElement() < doubleNumber) {
                doublesStatistics.setMaxElement(doubleNumber);
            }
            if (doublesStatistics.getMinElement() == null || doublesStatistics.getMinElement() > doubleNumber) {
                doublesStatistics.setMinElement(doubleNumber);
            }
        }

    }

    public void addString(String line) {
        int currentNumberOfElements = stringsStatistics.getNumberOfElements();
        stringsStatistics.setNumberOfElements(++currentNumberOfElements);
        if (statisticsType == LONG) {
            if (stringsStatistics.getMaxElement() == null || stringsStatistics.getMaxElement().length() < line.length()) {
                stringsStatistics.setMaxElement(line);
            }
            if (stringsStatistics.getMinElement() == null || stringsStatistics.getMinElement().length() > line.length()) {
                stringsStatistics.setMinElement(line);
            }
        }
    }

    public void printStatistics() {
        if (statisticsType == LONG) {
            printNumberFullStatistics(longStatistics);
            printNumberFullStatistics(doublesStatistics);
            printStringsFullStatistic();
        } else if (statisticsType == SHORT) {
            printShortStatistics(longStatistics);
            printShortStatistics(doublesStatistics);
            printShortStatistics(stringsStatistics);
        }
    }

    private <T extends Number> void printNumberFullStatistics(NumberStatistics<T> numberStatistics) {
        String stat = numberStatistics.getFileName() +
                      " full statistics: elements = " +
                      numberStatistics.getNumberOfElements() +
                      "; min = " +
                      numberStatistics.getMinElement() +
                      "; max = " +
                      numberStatistics.getMaxElement() +
                      "; sum = " +
                      numberStatistics.getSum() +
                      "; average = " +
                      numberStatistics.getAverage() +
                      ";";
        System.out.println(stat);
    }

    private void printStringsFullStatistic() {
        String stat = stringsStatistics.getFileName() +
                      " full statistics: elements = " +
                      stringsStatistics.getNumberOfElements() +
                      "; min = " +
                      stringsStatistics.getMinLength() +
                      "; max = " +
                      stringsStatistics.getMaxLength() +
                      ";";
        System.out.println(stat);
    }

    private void printShortStatistics(Statistics<?> statistics) {
        String stat = statistics.getFileName() +
                      ": elements = " +
                      statistics.getNumberOfElements();
        System.out.println(stat);
    }

}
