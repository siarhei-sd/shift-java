package team.korona.shift;

public class FullStatistics implements Statistics {

    private final IntegerStats integerStats = new IntegerStats();
    private final FloatStats floatStats = new FloatStats();
    private final StringStats stringStats = new StringStats();

    @Override
    public void updateStatistics(String data) {
        if (DataUtils.isInteger(data)) {
            integerStats.update(Integer.parseInt(data));
        } else if (DataUtils.isFloat(data)) {
            floatStats.update(Float.parseFloat(data));
        } else {
            stringStats.update(data);
        }
    }

    @Override
    public String getStatistics() {
        return String.format("%s%s%s", integerStats.getStats(), floatStats.getStats(), stringStats.getStats());
    }

    private static class IntegerStats {
        private int integerCount;
        private int minInteger;
        private int maxInteger;
        private long integerSum;

        public void update(int value) {
            if (integerCount == 0) {
                minInteger = value;
                maxInteger = value;
            } else {
                minInteger = Math.min(minInteger, value);
                maxInteger = Math.max(maxInteger, value);
            }
            integerSum += value;
            integerCount++;
        }

        public String getStats() {
            return "Total integers: " + integerCount + "\n" +
                    "Minimum integer: " + (integerCount > 0 ? minInteger : "No data") + "\n" +
                    "Maximum integer: " + (integerCount > 0 ? maxInteger : "No data") + "\n" +
                    "Sum of integers: " + (integerCount > 0 ? integerSum : "No data") + "\n";
        }
    }

    private static class FloatStats {
        private int floatCount;
        private float minFloat;
        private float maxFloat;
        private float floatSum;

        public void update(float value) {
            if (floatCount == 0) {
                minFloat = value;
                maxFloat = value;
            } else {
                minFloat = Math.min(minFloat, value);
                maxFloat = Math.max(maxFloat, value);
            }
            floatSum += value;
            floatCount++;
        }

        public String getStats() {
            return "Total floats: " + floatCount + "\n" +
                    "Minimum float: " + (floatCount > 0 ? minFloat : "No data") + "\n" +
                    "Maximum float: " + (floatCount > 0 ? maxFloat : "No data") + "\n" +
                    "Sum of floats: " + (floatCount > 0 ? floatSum : "No data") + "\n";
        }
    }

    private static class StringStats {
        private int stringCount;
        private String shortestString = "No data";
        private String longestString = "No data";

        public void update(String value) {
            if (stringCount == 0) {
                shortestString = value;
                longestString = value;
            } else {
                if (value.length() < shortestString.length()) {
                    shortestString = value;
                }

                if (value.length() > longestString.length()) {
                    longestString = value;
                }
            }
            stringCount++;
        }

        public String getStats() {
            return "Totals strings: " + stringCount + "\n" +
                    "Shortest string: " + shortestString + "\n" +
                    "Longest string: " + longestString + "\n";
        }
    }
}
