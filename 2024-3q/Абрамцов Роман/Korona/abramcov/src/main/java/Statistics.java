import java.util.HashMap;
import java.util.Map;

public class Statistics {
    private final boolean fullStats;
    private final Map<DataType, StatDetails> stats = new HashMap<>();

    public Statistics(boolean fullStats) {
        this.fullStats = fullStats;
    }
    //сбор статистики
    public <T> void collectStats(DataType dataType, T value) {
        StatDetails statDetails = stats.computeIfAbsent(dataType, key -> new StatDetails(fullStats));
        if (value instanceof Integer) {
            statDetails.collect((Integer) value);
        } else if (value instanceof Float) {
            statDetails.collect((Float) value);
        } else if (value instanceof String) {
            statDetails.collect((String) value);
        }
    }

    public void printStats() {
        if (fullStats) {
            printFullStats();
        } else {
            printShortStats();
        }
    }

    public void printShortStats() {
        for (Map.Entry<DataType, StatDetails> entry : stats.entrySet()) {
            System.out.println(entry.getKey() + " short statistic: elements = " + entry.getValue().getCount());
        }
    }

    public void printFullStats() {
        for (Map.Entry<DataType, StatDetails> entry : stats.entrySet()) {
            StatDetails details = entry.getValue();
            StringBuilder sb = new StringBuilder();
            sb.append(entry.getKey()).append(" full statistic: ");
            sb.append("elements = ").append(details.getCount());

            if (details.getCount() > 0) {
                if (entry.getKey() == DataType.INTEGER) {
                    sb.append("; min = ").append(details.getMinInt())
                            .append("; max = ").append(details.getMaxInt())
                            .append("; sum = ").append(details.getSumInt())
                            .append("; average = ").append(details.getAverageInt());
                } else if (entry.getKey() == DataType.FLOAT) {
                    sb.append("; min = ").append(details.getMinFloat())
                            .append("; max = ").append(details.getMaxFloat())
                            .append("; sum = ").append(details.getSumFloat())
                            .append("; average = ").append(details.getAverageFloat());
                } else if (entry.getKey() == DataType.STRING) {
                    sb.append("; min length = ").append(details.getMinLength())
                            .append("; max length = ").append(details.getMaxLength());
                }
            }

            System.out.println(sb);
        }
    }

    // влож. класс собирает статистику по каждому типу данных
    private static class StatDetails {
        private int count;
        private int minInt, maxInt, sumInt;
        private float minFloat, maxFloat, sumFloat;
        private int minLength, maxLength;

        public StatDetails(boolean fullStats) {
            this.count = 0;
            this.minInt = Integer.MAX_VALUE;
            this.maxInt = Integer.MIN_VALUE;
            this.minFloat = Float.MAX_VALUE;
            this.maxFloat = Float.MIN_VALUE;
            this.sumInt = 0;
            this.sumFloat = 0.0f;
            this.minLength = Integer.MAX_VALUE;
            this.maxLength = Integer.MIN_VALUE;
        }

        public void collect(Integer value) {
            if (count == 0 || value < minInt) minInt = value;
            if (count == 0 || value > maxInt) maxInt = value;
            sumInt += value;
            count++;
        }

        public void collect(Float value) {
            if (count == 0 || value < minFloat) minFloat = value;
            if (count == 0 || value > maxFloat) maxFloat = value;
            sumFloat += value;
            count++;
        }

        public void collect(String value) {
            int length = value.length();
            if (count == 0 || length < minLength) minLength = length;
            if (count == 0 || length > maxLength) maxLength = length;
            count++;
        }

        public int getCount() {
            return count;
        }

        public int getMinInt() {
            return minInt;
        }

        public int getMaxInt() {
            return maxInt;
        }

        public int getSumInt() {
            return sumInt;
        }

        public float getAverageInt() {
            return count > 0 ? (float) sumInt / count : 0;
        }

        public float getMinFloat() {
            return minFloat;
        }

        public float getMaxFloat() {
            return maxFloat;
        }

        public float getSumFloat() {
            return sumFloat;
        }

        public float getAverageFloat() {
            return count > 0 ? sumFloat / count : 0;
        }

        public int getMinLength() {
            return minLength;
        }

        public int getMaxLength() {
            return maxLength;
        }
    }
}
