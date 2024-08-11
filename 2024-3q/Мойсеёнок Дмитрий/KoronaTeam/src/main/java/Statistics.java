import java.util.*;

public class Statistics<T> {
    private List<T> data;

    public Statistics(List<T> data) {
        this.data = data;
    }

    public String getShortStatistics() {
        return "short statistic: elements = " + data.size();
    }

    public String getFullStatistics() {
        if (data.isEmpty()) {
            return getShortStatistics();
        }
        if (data.get(0) instanceof Integer) {
            return getFullStatisticsForNumbers();
        } else if (data.get(0) instanceof Double) {
            return getFullStatisticsForNumbers();
        } else {
            return getFullStatisticsForStrings();
        }
    }

    private String getFullStatisticsForNumbers() {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        double sum = 0;

        for (T number : data) {
            double value = ((Number) number).doubleValue();
            if (value < min) min = value;
            if (value > max) max = value;
            sum += value;
        }

        double average = sum / data.size();
        return String.format("full statistic: elements = %d; min = %f; max = %f; sum = %f; average = %f",
                data.size(), min, max, sum, average);
    }

    private String getFullStatisticsForStrings() {
        int minLength = Integer.MAX_VALUE;
        int maxLength = 0;

        for (T string : data) {
            int length = string.toString().length();
            if (length < minLength) minLength = length;
            if (length > maxLength) maxLength = length;
        }

        return String.format("full statistic: elements = %d; min length = %d; max length = %d",
                data.size(), minLength, maxLength);
    }
}
