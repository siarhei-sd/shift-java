package org.shift;

import static org.shift.Constants.*;

public class Statistic {
    private DataType dataType;
    private int count = 0;
    private Double min; // use Double to store both int and float
    private Double max;
    private Double sum = 0.0;
    private Double avg;

    public Statistic(DataType dataType) {
        this.dataType = dataType;
    }

    public void update(String value) {
        count++;

        if (dataType == DataType.INTEGER) {
            int parsedInt = Integer.parseInt(value);

            min = min == null ? parsedInt : Math.min(min, parsedInt);
            max = max == null ? parsedInt : Math.max(max, parsedInt);
            sum += parsedInt;
            avg = sum / count;
        }

        if (dataType == DataType.FLOAT) {
            float parsedFloat = Float.parseFloat(value);

            min = min == null ? parsedFloat : Math.min(min, parsedFloat);
            max = max == null ? parsedFloat : Math.max(max, parsedFloat);
            sum += parsedFloat;
            avg = sum / count;
        }

        if (dataType == DataType.STRING) {
            min = min == null ? value.length() : Math.min(min, value.length());
            max = max == null ? value.length() : Math.max(max, value.length());
        }
    }

    public String getStatistic(Boolean isFullStatistic) {
        String statisticType = isFullStatistic ? FULL_STAT_LABEL : SHORT_STAT_LABEL;
        StringBuilder statistic = new StringBuilder();

        statistic.append(statisticType + ":")
                .append(NUMBERS_COUNT_LABEL + count);

        if (dataType == DataType.INTEGER && isFullStatistic) {
            statistic.append(
                String.format(INT_STAT_TEMPLATE, min.intValue(), max.intValue(), sum.intValue(), avg)
            );
        }

        if (dataType == DataType.FLOAT && isFullStatistic) {
            statistic.append(
                String.format(
                    FLOAT_STAT_TEMPLATE,
                    formatDouble(min),
                    formatDouble(max),
                    formatDouble(sum),
                    formatDouble(avg)
                )
            );
        }

        if (dataType == DataType.STRING && isFullStatistic) {
            statistic.append(
                String.format(SHORT_STAT_TEMPLATE, min.intValue(), max.intValue())
            );
        }

        return statistic.toString();
    }

    private String formatDouble(Double value) {
        return value.toString().length() < 8 ? String.format("%e", value) : String.valueOf(value);
    }
}
