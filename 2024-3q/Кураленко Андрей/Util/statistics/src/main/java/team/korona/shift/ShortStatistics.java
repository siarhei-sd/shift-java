package team.korona.shift;

public class ShortStatistics implements Statistics {
    private int intCount;
    private int floatCount;
    private int stringCount;

    @Override
    public void updateStatistics(String data) {
        if (DataUtils.isInteger(data)) {
            intCount++;
        } else if (DataUtils.isFloat(data)) {
            floatCount++;
        } else {
            stringCount++;
        }
    }

    @Override
    public String getStatistics() {
        return "Total integers: " + intCount + "\n" +
                "Total floats: " + floatCount + "\n" +
                "Totals strings: " + stringCount + "\n";
    }
}